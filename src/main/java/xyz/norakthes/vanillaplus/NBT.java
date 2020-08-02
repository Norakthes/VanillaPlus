package xyz.norakthes.vanillaplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NBT implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        sender.sendMessage(mainHand.getItemMeta().toString());
        return true;
    }
}
