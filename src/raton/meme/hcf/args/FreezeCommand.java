package raton.meme.hcf.args;

import com.doctordark.utils.BukkitUtils;
import com.doctordark.utils.other.chat.ClickAction;
import com.doctordark.utils.other.chat.Text;
import net.minecraft.util.gnu.trove.map.TObjectLongMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectLongHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.event.PlayerFreezeEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class FreezeCommand implements CommandExecutor, Listener {
    private static final String FREEZE_BYPASS = "command.freeze.bypass";
    public static TObjectLongMap<UUID> frozenPlayers;
    public static HashMap<Player, String> frozenReasons;

    static {
        FreezeCommand.frozenPlayers = (TObjectLongMap<UUID>) new TObjectLongHashMap();
        FreezeCommand.frozenReasons = new HashMap<Player, String>();
    }

    private long defaultFreezeDuration;
    private long serverFrozenMillis;
    private HashSet<String> frozen;

    public FreezeCommand(final HCF plugin) {
        this.frozen = new HashSet<String>();
        this.defaultFreezeDuration = TimeUnit.MINUTES.toMillis(60L);
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + "/freeze <player>");
            return true;
        }
        final String reason = null;
        Long freezeTicks = this.defaultFreezeDuration;
        final long millis = System.currentTimeMillis();
        if (args[0].equalsIgnoreCase("all") && sender.hasPermission(String.valueOf(command.getPermission()) + ".all")) {
            final long oldTicks = this.getRemainingServerFrozenMillis();
            if (oldTicks > 0L) {
                freezeTicks = 0L;
            }
            this.serverFrozenMillis = millis + this.defaultFreezeDuration;
            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "The server is " + ((freezeTicks > 0L) ? ("now frozen for " + DurationFormatUtils.formatDurationWords(freezeTicks, true, true)) : "no longer frozen") + ((reason == null) ? "" : (" with reason " + reason)) + '.');
            return true;
        }
        final Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null || !PingCommand.canSee(sender, target)) {
            sender.sendMessage(ChatColor.GOLD + "Player '" + ChatColor.WHITE + args[0] + ChatColor.GOLD + "' not found.");
            return true;
        }
        final UUID targetUUID = target.getUniqueId();
        final boolean shouldFreeze = this.getRemainingPlayerFrozenMillis(targetUUID) > 0L;
        final PlayerFreezeEvent playerFreezeEvent = new PlayerFreezeEvent(target, shouldFreeze);
        Bukkit.getServer().getPluginManager().callEvent(playerFreezeEvent);
        if (playerFreezeEvent.isCancelled()) {
            sender.sendMessage(ChatColor.RED + "Unable to freeze " + target.getName() + '.');
            return false;
        }
        if (shouldFreeze) {
            this.frozen.remove(target.getName());
            FreezeCommand.frozenPlayers.remove(targetUUID);
            sender.sendMessage("§eYou have succesfully un froze §b" + target.getName());
            target.sendMessage(ChatColor.RED + "You have been un-frozen.");
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            FreezeCommand.frozenReasons.remove(target);
        } else if (args.length == 1) {
            this.frozen.add(target.getName());
            FreezeCommand.frozenPlayers.put(targetUUID, millis + freezeTicks);
            final String timeString = DurationFormatUtils.formatDurationWords(freezeTicks, true, true);
            this.Message(target.getName());
            sender.sendMessage("§eYou have succesfully froze §b" + target.getName());
        } else {
            this.frozen.add(target.getName());
            FreezeCommand.frozenPlayers.put(targetUUID, millis + freezeTicks);
            final String timeString = DurationFormatUtils.formatDurationWords(freezeTicks, true, true);
            this.Message(target.getName());
            sender.sendMessage("§eYou have succesfully froze §b" + target.getName());
            final String reason2 = StringUtils.join(args, ' ', 1, args.length);
            FreezeCommand.frozenReasons.put(target, reason2);
        }
        return true;
    }

    private void Message(final String name) {
        final HashMap timer = new HashMap();
        final Player p = Bukkit.getPlayer(name);
        final BukkitTask task = new BukkitRunnable() {
            public void run() {
                if (FreezeCommand.this.frozen.contains(name)) {
                    p.sendMessage(" ");
                    p.sendMessage("§f\u2588\u2588\u2588\u2588§c\u2588§f\u2588\u2588\u2588\u2588");
                    p.sendMessage("§f\u2588\u2588\u2588§c\u2588§6\u2588§c\u2588§f\u2588\u2588\u2588");
                    p.sendMessage("§f\u2588\u2588§c\u2588§6\u2588§0\u2588§6\u2588§c\u2588§f\u2588\u2588 §4§lDo NOT log out!");
                    p.sendMessage("§f\u2588\u2588§c\u2588§6\u2588§0\u2588§6\u2588§c\u2588§f\u2588\u2588 §eIf you log out, you will be banned!");
                    p.sendMessage("§f\u2588§c\u2588§6\u2588\u2588§0\u2588§6\u2588\u2588§c\u2588§f\u2588 §fPlease download Teamspeak and connect to:");
                    p.sendMessage("§f\u2588§c\u2588§6\u2588\u2588\u2588\u2588\u2588§c\u2588§f\u2588 " + "ts.server.net");
                    p.sendMessage("§c\u2588§6\u2588\u2588\u2588§0\u2588§6\u2588\u2588\u2588§c\u2588");
                    p.sendMessage("§c\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588");
                    p.sendMessage(" ");
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(HCF.getPlugin(), 0L, 200L);
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return (args.length == 1) ? null : Collections.emptyList();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            final Player attacker = BukkitUtils.getFinalAttacker(event, false);
            if (attacker == null) {
                return;
            }
            final Player player = (Player) entity;
            if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) {
                attacker.sendMessage(ChatColor.RED + player.getName() + " is currently frozen, you may not attack.");
                event.setCancelled(true);
                return;
            }
            if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(attacker.getUniqueId()) > 0L) {
                event.setCancelled(true);
                attacker.sendMessage(ChatColor.RED + "You may not attack players whilst frozen.");
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }
        final Player player = event.getPlayer();
        if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        final Player player = e.getPlayer();
        if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) {
            if (e.getCause() == TeleportCause.ENDER_PEARL) {
                player.sendMessage(ChatColor.RED + "You may not pearl while frozen!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) {
            Block block = e.getClickedBlock();
            if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                player.sendMessage(ChatColor.RED + "You may not open chests while frozen!");
                e.setCancelled(true);
                return;
            }
            if (player.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                player.sendMessage(ChatColor.RED + "You may not pearl while frozen!");
                e.setCancelled(true);
                return;
            }
            player.sendMessage(ChatColor.RED + "You may not interact with things while frozen!");
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {
        final Player player = e.getPlayer();
        if (this.getRemainingServerFrozenMillis() > 0L || this.getRemainingPlayerFrozenMillis(player.getUniqueId()) > 0L) {
            player.sendMessage(ChatColor.RED + "You may not drop items while frozen!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        if (this.frozen.contains(e.getPlayer().getName())) {
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (staff.hasPermission("base.command.freeze") || staff.hasPermission("command.freeze")) {
                    new Text(String.valueOf(ChatColor.YELLOW) + ChatColor.BOLD + e.getPlayer().getName() + " has " + ChatColor.DARK_RED + "QUIT" + ChatColor.YELLOW + ChatColor.BOLD + " while frozen." + ChatColor.GRAY + ChatColor.ITALIC + "(Click here to ban)").setHoverText(ChatColor.YELLOW + "Click here to permanently ban" + ChatColor.GRAY + e.getPlayer().getName()).setClick(ClickAction.RUN_COMMAND, "/ban " + e.getPlayer().getName() + " Refusal to SS");
                    return;
                }
            }
        }
    }

    private long getRemainingServerFrozenMillis() {
        return this.serverFrozenMillis - System.currentTimeMillis();
    }

    private long getRemainingPlayerFrozenMillis(final UUID uuid) {
        final long remaining = FreezeCommand.frozenPlayers.get(uuid);
        if (remaining == FreezeCommand.frozenPlayers.getNoEntryValue()) {
            return 0L;
        }
        return remaining - System.currentTimeMillis();
    }
}
