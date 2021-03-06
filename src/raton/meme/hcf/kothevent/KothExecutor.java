package raton.meme.hcf.kothevent;

import com.doctordark.utils.other.command.ArgumentExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import raton.meme.hcf.HCF;
import raton.meme.hcf.kothevent.args.KothHelpArgument;
import raton.meme.hcf.kothevent.args.KothNextArgument;
import raton.meme.hcf.kothevent.args.KothScheduleArgument;
import raton.meme.hcf.kothevent.args.KothSetCapDelayArgument;

/**
 * Command used to handle KingOfTheHills.
 */
public class KothExecutor extends ArgumentExecutor {

    private final KothScheduleArgument kothScheduleArgument;

    public KothExecutor(HCF plugin) {
        super("koth");

        addArgument(new KothHelpArgument(this));
        addArgument(new KothNextArgument(plugin));
        addArgument(this.kothScheduleArgument = new KothScheduleArgument(plugin));
        addArgument(new KothSetCapDelayArgument(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            this.kothScheduleArgument.onCommand(sender, command, label, args);
            return true;
        }

        return super.onCommand(sender, command, label, args);
    }
}
