package raton.meme.hcf.factionutils.args.staff;

import com.doctordark.utils.other.command.CommandArgument;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import raton.meme.hcf.HCF;
import raton.meme.hcf.factionutils.claim.Claim;
import raton.meme.hcf.factionutils.type.ClaimableFaction;
import raton.meme.hcf.factionutils.type.Faction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactionClaimForArgument extends CommandArgument {

    private static final int MIN_EVENT_CLAIM_AREA = 2;
    private final HCF plugin;

    public FactionClaimForArgument(HCF plugin) {
        super("claimfor", "Claims land for another faction.");
        this.plugin = plugin;
        this.permission = "hcf.command.faction.argument." + getName();
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName() + " <factionName>";
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
            return true;
        }
        Faction targetFaction = this.plugin.getFactionManager().getFaction(args[1]);
        if (!(targetFaction instanceof ClaimableFaction)) {
            sender.sendMessage(ChatColor.RED + "Claimable faction named " + args[1] + " not found.");
            return true;
        }
        Player player = (Player) sender;
        WorldEditPlugin worldEditPlugin = this.plugin.getWorldEdit();
        if (worldEditPlugin == null) {
            sender.sendMessage(ChatColor.RED + "WorldEdit must be installed to set claim areas.");
            return true;
        }
        Selection selection = worldEditPlugin.getSelection(player);
        if (selection == null) {
            sender.sendMessage(ChatColor.RED + "You must make a WorldEdit selection to do this.");
            return true;
        }
        ClaimableFaction claimableFaction = (ClaimableFaction) targetFaction;
        if (claimableFaction.addClaim(new Claim(claimableFaction, selection.getMinimumPoint(), selection.getMaximumPoint()), sender)) {
            sender.sendMessage(ChatColor.YELLOW + "Successfully claimed this land for " + ChatColor.RED + targetFaction.getName() + ChatColor.YELLOW + '.');
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if ((args.length != 2) || (!(sender instanceof Player))) {
            return Collections.emptyList();
        }
        if (args[1].isEmpty()) {
            return null;
        }
        Player player = (Player) sender;
        List<String> results = new ArrayList(this.plugin.getFactionManager().getFactionNameMap().keySet());
        for (Player target : Bukkit.getServer().getOnlinePlayers()) {
            if ((player.canSee(target)) && (!results.contains(target.getName()))) {
                results.add(target.getName());
            }
        }
        return results;
    }
}
