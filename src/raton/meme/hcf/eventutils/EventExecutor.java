package raton.meme.hcf.eventutils;

import com.doctordark.utils.other.command.ArgumentExecutor;
import raton.meme.hcf.HCF;
import raton.meme.hcf.eventutils.argument.*;

/**
 * Handles the execution and tab completion of the event command.
 */
public class EventExecutor extends ArgumentExecutor {

    public EventExecutor(HCF plugin) {
        super("event");

        addArgument(new EventCancelArgument(plugin));
        addArgument(new EventCreateArgument(plugin));
        addArgument(new EventDeleteArgument(plugin));
        addArgument(new EventRenameArgument(plugin));
        addArgument(new EventSetAreaArgument(plugin));
        addArgument(new EventSetCapzoneArgument(plugin));
        addArgument(new EventAddLootTableArgument(plugin));
        addArgument(new EventDelLootTableArgument(plugin));
        addArgument(new EventSetLootArgument(plugin));
        addArgument(new EventStartArgument(plugin));
        addArgument(new EventUptimeArgument(plugin));
    }
}