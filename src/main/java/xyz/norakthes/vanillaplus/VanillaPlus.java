package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class VanillaPlus extends JavaPlugin implements Listener {
//    public static ItemStack setAttackSpeed(ItemStack weapon, float amount) {
//
//    }

    static ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD, 1);

    static int emeraldSwordDurability = 941;

    static {
        NBTItem nbti = new NBTItem(emeraldSword);
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        lore.add("Durability: " + emeraldSwordDurability);

        emeraldSwordMeta.setDisplayName("§fEmerald Sword");
        emeraldSwordMeta.setCustomModelData(1);

        emeraldSwordMeta.setLore(lore);
        emeraldSword = nbti.getItem();
        emeraldSword.setItemMeta(emeraldSwordMeta);
    }

    static ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("vanillaplus"), emeraldSword).shape(
            " * ",
            " * ",
            " - ")
            .setIngredient('*', Material.EMERALD).setIngredient('-',Material.STICK);


    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this,this);

        getServer().addRecipe(emeraldSwordRecipe);

        this.getCommand("NBT").setExecutor(new NBT());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event){
        if (event.getItem().getItemMeta().getCustomModelData() == 1 && event.getItem().getType() == Material.DIAMOND_SWORD){
            ItemMeta itemMeta = event.getItem().getItemMeta();
            List<String> lore = event.getItem().getLore();
            Bukkit.broadcastMessage(String.valueOf(lore.get(1)));
            int durabilityInt;
//            if (Integer.parseInt(durability)  != -1){
//                durabilityInt = Integer.parseInt(durability);
//                durabilityInt--;
//                lore.add("Durability: " + durabilityInt);
//            }
//            itemMeta.setLore(lore);
//            event.getItem().setItemMeta(itemMeta);
            event.setCancelled(true);


        }
    }
}
