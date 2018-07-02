package raton.meme.hcf.args;

import com.doctordark.utils.InventoryUtils;
import com.doctordark.utils.ItemBuilder;
import com.doctordark.utils.other.chat.Lang;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import raton.meme.hcf.HCF;
import raton.meme.hcf.ymls.SettingsYML;

import java.util.*;

public class MapKitCommand
        implements CommandExecutor, TabCompleter, Listener {
    private final Set<Inventory> tracking = new HashSet();

    public MapKitCommand(HCF plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }
        List<ItemStack> items = new ArrayList();
        for (Map.Entry<Enchantment, Integer> entry : SettingsYML.ENCHANTMENT_LIMITS.entrySet()) {
            items.add(new ItemBuilder(Material.ENCHANTED_BOOK).displayName(ChatColor.YELLOW + Lang.fromEnchantment(entry.getKey()) + ": " + ChatColor.GREEN + entry.getValue()).build());
        }
        for (Map.Entry<PotionType, Integer> entry : SettingsYML.POTION_LIMITS.entrySet()) {
            items.add(new ItemBuilder(new Potion(entry.getKey()).toItemStack(1)).displayName(
                    ChatColor.YELLOW + WordUtils.capitalizeFully(entry.getKey().name().replace('_', ' ')) + ": " + ChatColor.GREEN + entry.getValue()).build());
        }
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(player, InventoryUtils.getSafestInventorySize(items.size()), "HCRiots HCF Map " + SettingsYML.MAP_NUMBER + " Kit");
        this.tracking.add(inventory);
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
        player.openInventory(inventory);
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (this.tracking.contains(event.getInventory())) {
            event.setCancelled(true);
        }
    }
}

