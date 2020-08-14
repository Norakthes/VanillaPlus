package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

    static {
        NBTItem nbti = new NBTItem(emeraldSword);
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

    lore.add("§fDurability: " + "§" + emeraldSwordDurability);

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
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Material itemMaterial = event.getItem().getType();
        ItemMeta swordMeta = event.getItem().getItemMeta();
        ItemStack itemStack = event.getItem();
        int customModelData = event.getItem().getItemMeta().getCustomModelData();


        if (itemMaterial == Material.DIAMOND_SWORD && customModelData == 1){
            List<String> lore = event.getItem().getLore();
            assert lore != null;


            Bukkit.broadcastMessage(lore + " | " + lore.size());
//            damage--;
//
//            loreArray[1] = String.valueOf(damage);
//
//            lore = Arrays.asList(loreArray);
//                swordMeta.setLore(lore);
//                itemStack.setItemMeta(swordMeta);
//            event.setCancelled(true);
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
