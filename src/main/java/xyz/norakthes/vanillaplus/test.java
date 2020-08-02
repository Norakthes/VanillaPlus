package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD);
        NBTItem nbtItem = new NBTItem(emeraldSword);
        ItemMeta itemMeta = emeraldSword.getItemMeta();

        itemMeta.setDisplayName("§fEmerald Sword");
        nbtItem.setInteger("CustomModelData",121);

        emeraldSword.setItemMeta(itemMeta);
        emeraldSword = nbtItem.getItem();

        Player player = (Player) sender;
        player.getInventory().addItem(emeraldSword);
        return true;
    }
}
