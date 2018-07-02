package raton.meme.hcf.args;

import com.doctordark.utils.BukkitUtils;
import com.doctordark.utils.internal.com.doctordark.base.BaseConstants;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PingCommand implements CommandExecutor {

    public static boolean canSee(CommandSender sender, Player target) {
        return (target != null) && ((!(sender instanceof Player)) || (((Player) sender).canSee(target)));
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target;
        if ((args.length > 0) && (sender.hasPermission(command.getPermission() + ".others"))) {
            target = BukkitUtils.playerWithNameOrUUID(args[0]);
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Usage: " + "/ping <player>");
                return true;
            }
            target = (Player) sender;
        }
        if ((target == null) || (!PingCommand.canSee(sender, target))) {
            sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
            return true;
        }
        sender.sendMessage((target.equals(sender) ? (ChatColor.YELLOW + "Your ping is ") : (ChatColor.YELLOW + "Ping of §a" + (target.getName() + " §eis "))) + target.getPing());
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return (args.length == 1) && (sender.hasPermission(command.getPermission() + ".others")) ? null : Collections.emptyList();
    }
}
