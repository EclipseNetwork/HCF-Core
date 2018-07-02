package raton.meme.hcf.scoreboard;

import com.google.common.base.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.event.FactionRelationCreateEvent;
import raton.meme.hcf.factionutils.event.FactionRelationRemoveEvent;
import raton.meme.hcf.factionutils.event.PlayerJoinedFactionEvent;
import raton.meme.hcf.factionutils.event.PlayerLeftFactionEvent;
import raton.meme.hcf.scoreboard.helper.ScoreboardHelper;

import java.util.*;

public class ScoreboardHandler
        implements Listener {
    private final Map<UUID, PlayerBoard> playerBoards;
    private final ScoreboardHelper timerSidebarProvider;
    private final HCF plugin;

    public ScoreboardHandler(HCF plugin) {
        this.playerBoards = new HashMap();
        this.plugin = plugin;
        this.timerSidebarProvider = new ScoreboardHelper(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = Bukkit.getServer().getOnlinePlayers()).length;
        for (int i = 0; i < j; i++) {
            Player players = arrayOfPlayer[i];
            PlayerBoard playerBoard;
            setPlayerBoard(players.getUniqueId(), playerBoard = new PlayerBoard(plugin, players));
            Collection<? extends Player> c = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
            playerBoard.addUpdates(c);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        for (PlayerBoard board : this.playerBoards.values()) {
            board.addUpdate(player);
        }
        PlayerBoard board2 = new PlayerBoard(this.plugin, player);
        Object c = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
        board2.addUpdates((Collection) c);
        setPlayerBoard(uuid, board2);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.playerBoards.remove(event.getPlayer().getUniqueId()).remove();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerJoinedFaction(PlayerJoinedFactionEvent event) {
        Optional<Player> optional = event.getPlayer();
        if (optional.isPresent()) {
            Player player = optional.get();
            Set<Player> players = event.getFaction().getOnlinePlayers();
            this.getPlayerBoard(event.getPlayerUUID()).addUpdates(players);
            for (Player target : players) {
                this.getPlayerBoard(target.getUniqueId()).addUpdate(player);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerLeftFaction(PlayerLeftFactionEvent event) {
        Optional<Player> optional = event.getPlayer();
        if (optional.isPresent()) {
            Player player = optional.get();
            Collection<Player> players = event.getFaction().getOnlinePlayers();
            getPlayerBoard(event.getUniqueID()).addUpdates(players);
            for (Player target : players) {
                getPlayerBoard(target.getUniqueId()).addUpdate(player);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onFactionAllyCreate(FactionRelationCreateEvent event) {
        Set<Player> updates = new HashSet(event.getSenderFaction().getOnlinePlayers());
        updates.addAll(event.getTargetFaction().getOnlinePlayers());
        for (PlayerBoard board : this.playerBoards.values()) {
            board.addUpdates(updates);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onFactionAllyRemove(FactionRelationRemoveEvent event) {
        Set<Player> updates = new HashSet(event.getSenderFaction().getOnlinePlayers());
        updates.addAll(event.getTargetFaction().getOnlinePlayers());
        for (PlayerBoard board : this.playerBoards.values()) {
            board.addUpdates(updates);
        }
    }

    public PlayerBoard getPlayerBoard(UUID uuid) {
        return this.playerBoards.get(uuid);
    }

    public void setPlayerBoard(UUID uuid, PlayerBoard board) {
        this.playerBoards.put(uuid, board);
        board.setSidebarVisible(true);
        board.setDefaultSidebar(this.timerSidebarProvider, 2L);
    }

    public void clearBoards() {
        Iterator<PlayerBoard> iterator = this.playerBoards.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().remove();
            iterator.remove();
        }
    }
}
