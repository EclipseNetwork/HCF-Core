package raton.meme.hcf.timer.type;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import raton.meme.hcf.HCF;
import raton.meme.hcf.armors.archer.ArcherClass;
import raton.meme.hcf.timer.PlayerTimer;
import raton.meme.hcf.timer.event.TimerExpireEvent;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ArcherTimer
        extends PlayerTimer
        implements Listener {
    private final HCF plugin;
    private HashMap<Player, ChatColor> lastColor = new HashMap<Player, ChatColor>();

    public ArcherTimer(HCF plugin) {
        super("Archer Mark", TimeUnit.SECONDS.toMillis(7L));
        this.plugin = plugin;
    }

    public String getScoreboardPrefix() {
        return ChatColor.GOLD.toString() + ChatColor.BOLD;
    }

    public void run() {
    }

    @EventHandler
    public void onExpire(TimerExpireEvent e) {
        if ((e.getUserUUID().isPresent()) && (e.getTimer().equals(this))) {
            UUID userUUID = e.getUserUUID().get();
            Player player = Bukkit.getPlayer(userUUID);
            if (player == null) {
                return;
            }
            Bukkit.getPlayer(ArcherClass.TAGGED.get(userUUID)).sendMessage(ChatColor.YELLOW + "Your archer mark on " + ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " has expired.");
            player.sendMessage(ChatColor.YELLOW + "You're no longer archer marked.");
            ArcherClass.TAGGED.remove(player.getUniqueId());
            Player[] arrayOfPlayer;
            int j = (arrayOfPlayer = Bukkit.getServer().getOnlinePlayers()).length;
            for (int i = 0; i < j; i++) {
                Player players = arrayOfPlayer[i];
                this.plugin.getScoreboardHandler().getPlayerBoard(players.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player))) {
            Player entity = (Player) e.getEntity();
            Entity damager = e.getDamager();
            if (getRemaining(entity) > 0L) {
                Double damage = e.getDamage() * 0.3D;
                e.setDamage(e.getDamage() + damage);
            }
        }
        if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Arrow))) {
            Player entity = (Player) e.getEntity();
            Entity damager = ((Arrow) e.getDamager()).getShooter();
            if (((damager instanceof Player)) && (getRemaining(entity) > 0L)) {
                if (ArcherClass.TAGGED.get(entity.getUniqueId()).equals(damager.getUniqueId())) {
                    setCooldown(entity, entity.getUniqueId());
                }
                Double damage = Double.valueOf(e.getDamage() * 0.3D);
                e.setDamage(e.getDamage() + damage.doubleValue());
            }
        }
    }
}
