package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class VanillaPlus extends JavaPlugin implements Listener {

    static ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD, 1);

    static int emeraldSwordDurability = 941; //TODO make durability configurable

    //  Emerald sword
    static {
        NBTItem nbti = new NBTItem(emeraldSword);
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "§fDurability: " +/* "§f" +*/ emeraldSwordDurability);

        emeraldSwordMeta.setDisplayName("§fEmerald Sword");
        emeraldSwordMeta.setCustomModelData(1);

        emeraldSwordMeta.setLore(lore);
        emeraldSword.setItemMeta(emeraldSwordMeta);
    }

    static ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("vanillaplus"), emeraldSword).shape(
            " * ",
            " * ",
            " - ")
            .setIngredient('*', Material.EMERALD)
            .setIngredient('-',Material.STICK);


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
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Material itemMaterial = event.getItem().getType();
        ItemStack itemStack = event.getItem();
        ItemMeta swordMeta = event.getItem().getItemMeta();
        int customModelData = event.getItem().getItemMeta().getCustomModelData();
        NBTItem nbti = new NBTItem(event.getItem());

        if (itemMaterial == Material.DIAMOND_SWORD && customModelData == 1){
            Integer swordDamage = nbti.getInteger("itemDamage");
            Player player = event.getPlayer();

            swordDamage--;
            nbti.setInteger("itemDamage",swordDamage);

            itemStack = nbti.getItem();
            player.getInventory().setItemInMainHand(itemStack);

            if (swordDamage == 0){
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
            event.setCancelled(true);
        }
            //Future implementation
//        switch (itemMaterial) {
//            case DIAMOND_SWORD:
//                List<String> lore = event.getItem().getLore();
//                assert lore != null;
//                String[] loreArray = new String[lore.size()];
//                lore.toArray(loreArray);
//                switch (customModelData){
//                    case 1:
//                        int itemMeta = Integer.parseInt(loreArray[1]);
//                        ArrayList<String> swordLore = new ArrayList<>();
//                        itemMeta--;
//
//
//
//                        swordMeta.setLore(swordLore);
//                        event.setCancelled(true);
//                        itemStack.setItemMeta(swordMeta);
//                        break;
//                }
//                break;
//        }
    }
}
