package raton.meme.hcf.deathban.lives.args;

import com.doctordark.utils.other.command.CommandArgument;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import raton.meme.hcf.HCF;
import raton.meme.hcf.deathban.Deathban;
import raton.meme.hcf.factionutils.FactionUser;

/**
 * An {@link CommandArgument} used to clear all {@link Deathban}s.
 */
public class LivesClearDeathbansArgument extends CommandArgument {

    private final HCF plugin;

    public LivesClearDeathbansArgument(HCF plugin) {
        super("cleardeathbans", "Clears the global deathbans");
        this.plugin = plugin;
        this.aliases = new String[]{"resetdeathbans"};
        this.permission = "hcf.command.lives.argument." + getName();
    }

    @Override
    public String getUsage(String label) {
        return '/' + label + ' ' + getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (FactionUser user : plugin.getUserManager().getUsers().values()) {
            user.removeDeathban();
        }

        Command.broadcastCommandMessage(sender, ChatColor.YELLOW + "All death-bans have been cleared.");
        return true;
    }
}
