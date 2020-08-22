package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTItem;
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
import java.util.Collections;
import java.util.List;

public final class VanillaPlus extends JavaPlugin implements Listener {

    static ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD, 1);

    static int emeraldSwordDurability = 941; //TODO make durability configurable

    //  Emerald sword
    static {
        NBTItem nbti = new NBTItem(emeraldSword);
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§fDurability: " + emeraldSwordDurability +" / "+ emeraldSwordDurability);

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
        ItemMeta itemMeta = null;
        int customModelData = 0;
        List<String> lore = null;

        if (event.getItem().hasItemMeta()){
            itemMeta = event.getItem().getItemMeta();
            if (event.getItem().getItemMeta().hasLore()){
                lore = event.getItem().getLore();
            }
        }

        if (event.getItem().getItemMeta().hasCustomModelData()){
            customModelData = event.getItem().getItemMeta().getCustomModelData();
        }

//        if (itemMaterial == Material.DIAMOND_SWORD && customModelData == 1 && lore != null){
//            String[] loreArray = lore.toString().split(" ");
//            Player player = event.getPlayer();
//            int currentDurability = Integer.parseInt(loreArray[1]);
//
//            currentDurability--;
//            itemMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "§fDurability: " + currentDurability + " / " + emeraldSwordDurability));
//            itemStack.setItemMeta(itemMeta);
//            player.getInventory().setItemInMainHand(itemStack);
//            event.setCancelled(true);
//        }
            //Future implementation
        switch (itemMaterial) {
            case DIAMOND_SWORD:
                switch (customModelData) {
                    case 1:
                        String[] loreArray = lore.toString().split(" ");
                        Player player = event.getPlayer();
                        int currentDurability = Integer.parseInt(loreArray[1]);

                        currentDurability--;
                        itemMeta.setLore(Collections.singletonList("§fDurability: " + currentDurability + " / " + emeraldSwordDurability));
                        itemStack.setItemMeta(itemMeta);
                        player.getInventory().setItemInMainHand(itemStack);
                        event.setCancelled(true);
                        break;
                }
        }
    }
}
