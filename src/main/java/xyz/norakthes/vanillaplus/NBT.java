package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NBT implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        NBTItem nbti = new NBTItem(player.getInventory().getItemInMainHand());
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        sender.sendMessage(mainHand.getItemMeta().toString() + " | " + nbti);
        return true;
    }
}
