package raton.meme.hcf;

import com.doctordark.utils.internal.com.doctordark.base.BasePlugin;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import raton.meme.hcf.args.*;
import raton.meme.hcf.armors.PvpClassManager;
import raton.meme.hcf.armors.bard.EffectRestorer;
import raton.meme.hcf.conquestevent.ConquestExecutor;
import raton.meme.hcf.deathban.*;
import raton.meme.hcf.deathban.lives.LivesExecutor;
import raton.meme.hcf.deathban.lives.PvpTimerCommand;
import raton.meme.hcf.economy.*;
import raton.meme.hcf.endoftheworld.EotwCommand;
import raton.meme.hcf.endoftheworld.EotwHandler;
import raton.meme.hcf.endoftheworld.EotwListener;
import raton.meme.hcf.eventutils.CaptureZone;
import raton.meme.hcf.eventutils.EventExecutor;
import raton.meme.hcf.eventutils.EventScheduler;
import raton.meme.hcf.factionutils.*;
import raton.meme.hcf.factionutils.args.FactionClaimChunkArgument;
import raton.meme.hcf.factionutils.args.FactionManagerArgument;
import raton.meme.hcf.factionutils.claim.*;
import raton.meme.hcf.factionutils.type.*;
import raton.meme.hcf.kothevent.KothExecutor;
import raton.meme.hcf.listener.*;
import raton.meme.hcf.listener.combatloggers.CombatLogListener;
import raton.meme.hcf.listener.combatloggers.CustomEntityRegistration;
import raton.meme.hcf.listener.fixes.*;
import raton.meme.hcf.listener.render.ProtocolLibHook;
import raton.meme.hcf.listener.render.VisualiseHandler;
import raton.meme.hcf.listener.render.WallBorderListener;
import raton.meme.hcf.scoreboard.ScoreboardHandler;
import raton.meme.hcf.startoftheworld.SotwCommand;
import raton.meme.hcf.startoftheworld.SotwListener;
import raton.meme.hcf.startoftheworld.SotwTimer;
import raton.meme.hcf.systems.crates.KeyListener;
import raton.meme.hcf.systems.crates.KeyManager;
import raton.meme.hcf.systems.signs.DeathSignListener;
import raton.meme.hcf.systems.signs.EventSignListener;
import raton.meme.hcf.timer.TimerExecutor;
import raton.meme.hcf.timer.TimerManager;
import raton.meme.hcf.ymls.SettingsYML;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HCF extends JavaPlugin {

    @Getter
    public static HCF plugin;
    @Getter
    private static HCF instance;
    public Permission permission;
    @Getter
    private PlayTimeManager playTimeManager;
    @Getter
    private Random random = new Random();
    @Getter
    private ClaimHandler claimHandler;
    @Getter
    private DeathbanManager deathbanManager;
    @Getter
    private EconomyManager economyManager;
    @Getter
    private EffectRestorer effectRestorer;
    @Getter
    private EotwHandler eotwHandler;
    @Getter
    private EventScheduler eventScheduler;
    @Getter
    private FactionManager factionManager;
    @Getter
    private FoundDiamondsListener foundDiamondsListener;
    @Getter
    private PvpClassManager pvpClassManager;
    @Getter
    private ScoreboardHandler scoreboardHandler;
    @Getter
    private SotwTimer sotwTimer;
    @Getter
    private TimerManager timerManager;
    @Getter
    private KeyManager keyManager;
    @Getter
    private FactionUser factionUser;
    @Getter
    private UserManager userManager;
    @Getter
    private VisualiseHandler visualiseHandler;
    @Getter
    private WorldEditPlugin worldEdit;
    private ArrayList<String> sataffModePlayers = new ArrayList<String>();
    private ArrayList<String> staffChat = new ArrayList<String>();
    private ArrayList<String> staffboard = new ArrayList<String>();
    private ArrayList<String> vanish = new ArrayList<String>();
    private String craftBukkitVersion;
    private CombatLogListener combatLogListener;

    public static HCF getPlugin() {
        return HCF.plugin;
    }

    public static HCF getInstance() {
        return HCF.instance;
    }

    public static SotwTimer getSotwRunnable() {
        return HCF.getSotwRunnable();
    }

    public CombatLogListener getCombatLogListener() {
        return this.combatLogListener;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public Permission getPermissions() {
        return permission;
    }

    @Override
    public void onEnable() {
        searchForDependencies();

        File text = new File(getDataFolder(), "event-schedules.txt");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(text); // Added by YoloSanta so it doesn't spam your console.
        file.options().copyDefaults(true);

        Cooldowns.createCooldown("medic_cooldown");

        HCF.plugin = this;
        BasePlugin.getPlugin().init(this);

        Plugin wep = getServer().getPluginManager().getPlugin("WorldEdit");
        this.worldEdit = wep instanceof WorldEditPlugin && wep.isEnabled() ? (WorldEditPlugin) wep : null;
        CustomEntityRegistration.registerCustomEntities();

        this.craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        ProtocolLibHook.hook(this);

        SettingsYML.init(this);
        this.effectRestorer = new EffectRestorer(this);
        this.registerConfiguration();
        this.registerCommands();
        this.registerManagers();
        this.registerListeners();

        this.setupPermissions();


        new BukkitRunnable() {
            @Override
            public void run() {
                getServer().broadcast(ChatColor.GREEN.toString() + ChatColor.BOLD + "Saving!" + "\n" + ChatColor.GREEN + "Saved all faction and player data to the database." + "\n" + ChatColor.GRAY + "Current TPS: " + ChatColor.GRAY, "hcf.seesaves");
                saveData();
            }
        }.runTaskTimerAsynchronously(plugin, TimeUnit.MINUTES.toMillis(10L), TimeUnit.MINUTES.toMillis(10L));
    }

    private void saveData() {
        this.combatLogListener.removeCombatLoggers();
        this.deathbanManager.saveDeathbanData();
        this.economyManager.saveEconomyData();
        this.factionManager.saveFactionData();
        this.keyManager.saveKeyData();
        this.timerManager.saveTimerData();
        this.userManager.saveUserData();
    }

    @Override
    public void onDisable() {
        this.pvpClassManager.onDisable();
        this.scoreboardHandler.clearBoards();
        this.factionManager.saveFactionData();
        this.deathbanManager.saveDeathbanData();
        this.economyManager.saveEconomyData();
        this.factionManager.saveFactionData();
        this.keyManager.saveKeyData();
        this.timerManager.saveTimerData();
        this.userManager.saveUserData();
        this.playTimeManager.savePlaytimeData();
        this.saveData();

        HCF.plugin = null; // always initialise last
    }

    private void registerConfiguration() {
        ConfigurationSerialization.registerClass(CaptureZone.class);
        ConfigurationSerialization.registerClass(Deathban.class);
        ConfigurationSerialization.registerClass(Claim.class);
        ConfigurationSerialization.registerClass(Subclaim.class);
        ConfigurationSerialization.registerClass(Deathban.class);
        ConfigurationSerialization.registerClass(FactionUser.class);
        ConfigurationSerialization.registerClass(ClaimableFaction.class);
        ConfigurationSerialization.registerClass(ConquestFaction.class);
        ConfigurationSerialization.registerClass(CapturableFaction.class);
        ConfigurationSerialization.registerClass(KothFaction.class);
        ConfigurationSerialization.registerClass(EndPortalFaction.class);
        ConfigurationSerialization.registerClass(Faction.class);
        ConfigurationSerialization.registerClass(FactionMember.class);
        ConfigurationSerialization.registerClass(PlayerFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.class);
        ConfigurationSerialization.registerClass(SpawnFaction.class);
        ConfigurationSerialization.registerClass(GlowstoneMountainFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.NorthRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.EastRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.SouthRoadFaction.class);
        ConfigurationSerialization.registerClass(RoadFaction.WestRoadFaction.class);
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        this.playTimeManager = new PlayTimeManager(this);
        manager.registerEvents(this.playTimeManager, this);
        manager.registerEvents(this.combatLogListener = new CombatLogListener(this), this);
        manager.registerEvents(new HitDetectionListener(), this);
        manager.registerEvents(new FactionManagerArgument(this), this);
        manager.registerEvents(new BlockHitFixListener(), this);
        manager.registerEvents(new BlockJumpGlitchFixListener(), this);
        manager.registerEvents(new BoatGlitchFixListener(), this);
        manager.registerEvents(new BookDeenchantListener(), this);
        manager.registerEvents(new FactionClaimChunkArgument(this), this);
        manager.registerEvents(new BorderListener(), this);
        manager.registerEvents(new CreatureSpawn(), this);
        manager.registerEvents(new CobbleCommand(), this);
        manager.registerEvents(new BottledExpListener(), this);
        manager.registerEvents(new PortalTrapFixListener(this), this);
        manager.registerEvents(new ChatListener(this), this);
        manager.registerEvents(new ClaimWandListener(this), this);
        manager.registerEvents(new CoreListener(this), this);
        manager.registerEvents(new ElevatorListener(this), this);
        manager.registerEvents(new CrowbarListener(this), this);
        manager.registerEvents(new DeathListener(this), this);
        manager.registerEvents(new DeathMessageListener(this), this);
        manager.registerEvents(new DeathSignListener(this), this);
        manager.registerEvents(new KitSigns(this), this);
        if (!SettingsYML.KIT_MAP) {
            manager.registerEvents(new DeathbanListener(this), this);
        }
        manager.registerEvents(new EnchantLimitListener(), this);
        manager.registerEvents(new EnderChestRemovalListener(), this);
        manager.registerEvents(new EntityLimitListener(), this);
        manager.registerEvents(new EndListener(), this);
        manager.registerEvents(new EotwListener(this), this);
        manager.registerEvents(new EventSignListener(), this);
        manager.registerEvents(new ExpMultiplierListener(), this);
        manager.registerEvents(new FactionListener(this), this);
        manager.registerEvents(this.foundDiamondsListener = new FoundDiamondsListener(this), this);
        manager.registerEvents(new FastBrewingListener(this), this);
        manager.registerEvents(new InfinityArrowFixListener(), this);
        manager.registerEvents(new KeyListener(this), this);
        manager.registerEvents(new PearlGlitchListener(this), this);
        manager.registerEvents(new PortalListener(this), this);
        manager.registerEvents(new PotionLimitListener(), this);
        manager.registerEvents(new ProtectionListener(this), this);
        manager.registerEvents(new SubclaimWandListener(this), this);
        manager.registerEvents(new SignSubclaimListener(this), this);
        manager.registerEvents(new ShopSignListener(this), this);
        manager.registerEvents(new SkullListener(), this);
        manager.registerEvents(new SotwListener(this), this);
        manager.registerEvents(new BeaconStrengthFixListener(), this);
        manager.registerEvents(new UnRepairableListener(), this);
        manager.registerEvents(new WallBorderListener(this), this);
        manager.registerEvents(new AutoSmeltOreListener(), this);
        manager.registerEvents(new WorldListener(this), this);
        manager.registerEvents(new StrengthPatch(), this);
        manager.registerEvents(new HungerFixListener(), this);
        manager.registerEvents(new KnockBackListener(this), this);
        manager.registerEvents(new PotListener(), this);
    }

    private void registerCommands() {
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("cobble").setExecutor(new CobbleCommand());
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("titanium").setExecutor(new MedicCommand(this));
        getCommand("crowgive").setExecutor(new GiveCrowbarCommand());
        getCommand("staffinfo").setExecutor(new StaffInfoCommand());
        getCommand("hcfhelp").setExecutor(new HCFHelpCommand());
        getCommand("coords").setExecutor(new CoordsCommand());
        getCommand("conquest").setExecutor(new ConquestExecutor(this));
        getCommand("economy").setExecutor(new EconomyCommand(this));
        getCommand("refund").setExecutor(new RefundCommand());
        getCommand("eotw").setExecutor(new EotwCommand(this));
        getCommand("event").setExecutor(new EventExecutor(this));
        getCommand("hcfhelp").setExecutor(new HCFHelpCommand());
        getCommand("faction").setExecutor(new FactionExecutor(this));
        getCommand("gopple").setExecutor(new GoppleCommand(this));
        getCommand("koth").setExecutor(new KothExecutor(this));
        getCommand("lives").setExecutor(new LivesExecutor(this));
        getCommand("location").setExecutor(new LocationCommand(this));
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rawcast").setExecutor(new RawcastCommand());
        getCommand("freeze").setExecutor(new FreezeCommand(this));
        getCommand("logout").setExecutor(new LogoutCommand(this));
        getCommand("mapkit").setExecutor(new MapKitCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("regen").setExecutor(new RegenCommand(this));
        getCommand("servertime").setExecutor(new ServerTimeCommand());
        getCommand("sotw").setExecutor(new SotwCommand(this));
        getCommand("spawncannon").setExecutor(new SpawnCannonCommand(this));
        getCommand("staffrevive").setExecutor(new StaffReviveCommand(this));
        getCommand("timer").setExecutor(new TimerExecutor(this));
        getCommand("togglebroadcasts").setExecutor(new ToggleBroadcastsCommand());
        getCommand("togglecapzoneentry").setExecutor(new ToggleCapzoneEntryCommand(this));
        getCommand("togglelightning").setExecutor(new ToggleLightningCommand(this));
        getCommand("togglesidebar").setExecutor(new ToggleSidebarCommand(this));
        getCommand("playtime").setExecutor(new PlayTimeCommand());
        getCommand("rename").setExecutor(new RenameCommand(this));
        if (!SettingsYML.KIT_MAP) ;
        getCommand("pvptimer").setExecutor(new PvpTimerCommand(this));

        Map<String, Map<String, Object>> map = getDescription().getCommands();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            PluginCommand command = getCommand(entry.getKey());
            command.setPermission("command." + entry.getKey());
            command.setPermissionMessage(ChatColor.RED + "No permission.");
        }
    }

    private void registerCooldowns() {
        Cooldowns.createCooldown("medic_cooldown");
    }

    private void registerManagers() {
        this.claimHandler = new ClaimHandler(this);
        this.deathbanManager = new FlatFileDeathbanManager(this);
        this.economyManager = new FlatFileEconomyManager(this);
        this.eotwHandler = new EotwHandler(this);
        this.eventScheduler = new EventScheduler(this);
        this.factionManager = new FlatFileFactionManager(this);
        this.keyManager = new KeyManager(this);
        this.pvpClassManager = new PvpClassManager(this);
        this.sotwTimer = new SotwTimer();
        this.timerManager = new TimerManager(this); // needs to be registered before ScoreboardHandler
        this.scoreboardHandler = new ScoreboardHandler(this);
        this.userManager = new UserManager(this);
        this.visualiseHandler = new VisualiseHandler();
    }

    public PlayTimeManager getPlayTimeManager() {
        return this.playTimeManager;
    }

    public ArrayList getStaffModePlayers() {
        return this.sataffModePlayers;
    }

    public ArrayList getStaffChatPlayers() {
        return this.staffChat;
    }

    public ArrayList getToggledStaffBoardPlayers() {
        return this.staffboard;
    }

    public ArrayList getVanishedPlayers() {
        return this.vanish;
    }

    public Random getRandom() {
        return this.random;
    }

    public ClaimHandler getClaimHandler() {
        return this.claimHandler;
    }

    public DeathbanManager getDeathbanManager() {
        return this.deathbanManager;
    }

    public EconomyManager getEconomyManager() {
        return this.economyManager;
    }

    public EffectRestorer getEffectRestorer() {
        return this.effectRestorer;
    }

    public EotwHandler getEotwHandler() {
        return this.eotwHandler;
    }

    public FactionUser getFactionUser() {
        return this.factionUser;
    }

    public EventScheduler getEventScheduler() {
        return this.eventScheduler;
    }

    public FactionManager getFactionManager() {
        return this.factionManager;
    }

    public FoundDiamondsListener getFoundDiamondsListener() {
        return this.foundDiamondsListener;
    }

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    public PvpClassManager getPvpClassManager() {
        return this.pvpClassManager;
    }

    public String getCraftBukkitVersion() {
        return craftBukkitVersion;
    }

    public ScoreboardHandler getScoreboardHandler() {
        return this.scoreboardHandler;
    }

    public SotwTimer getSotwTimer() {
        return this.sotwTimer;
    }

    public TimerManager getTimerManager() {
        return this.timerManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public VisualiseHandler getVisualiseHandler() {
        return this.visualiseHandler;
    }

    public WorldEditPlugin getWorldEdit() {
        return this.worldEdit;
    }

    private void searchForDependencies() { // Added by YoloSanta as better pre-caution.
        if (!getServer().getPluginManager().isPluginEnabled("Vault")) {
            System.out.println("Server is locking down because Vault wasn't found in the plugins folder.");
            getServer().shutdown();
        }
    }
}
