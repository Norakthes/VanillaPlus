package xyz.norakthes.vanillaplus;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
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
import java.util.Objects;

public final class VanillaPlus extends JavaPlugin implements Listener {

    static ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD, 1);
    static ItemStack emeraldPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);

    static int emeraldDurability = 941; //TODO make durability configurable

    // Emerald sword configuration
    static {
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§fDurability: " + emeraldDurability +" / "+ emeraldDurability);

        emeraldSwordMeta.setDisplayName("§fEmerald Sword");
        emeraldSwordMeta.setCustomModelData(1);

        emeraldSwordMeta.setLore(lore);
        emeraldSword.setItemMeta(emeraldSwordMeta);
    }

    // Emerald pickaxe configuration
    static {
        ItemMeta emeraldPickaxeMeta = emeraldPickaxe.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§fDurability: " + emeraldDurability +" / "+ emeraldDurability);

        emeraldPickaxeMeta.setDisplayName("§fEmerald Pickaxe");
        emeraldPickaxeMeta.setCustomModelData(1);

        emeraldPickaxeMeta.setLore(lore);
        emeraldPickaxe.setItemMeta(emeraldPickaxeMeta);
    }

    static ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("vanillaplus"), emeraldSword).shape(
            " * ",
            " * ",
            " - ")
            .setIngredient('*', Material.EMERALD)
            .setIngredient('-', Material.STICK);

    static ShapedRecipe emeraldPickaxeRecipe = new ShapedRecipe(NamespacedKey.minecraft("vanillaplus"), emeraldPickaxe).shape(
            "***",
            " - ",
            " - ")
            .setIngredient('*', Material.EMERALD)
            .setIngredient('-', Material.STICK);


    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this,this);

        getServer().addRecipe(emeraldSwordRecipe);
        getServer().addRecipe(emeraldPickaxeRecipe);

        Objects.requireNonNull(this.getCommand("NBT")).setExecutor(new NBT());

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
        List<String> lore;
        int currentDurability = 0;

        if (event.getItem().hasItemMeta()){
            itemMeta = event.getItem().getItemMeta();
            if (event.getItem().getItemMeta().hasLore()){
                lore = event.getItem().getLore();
                String[] loreArray = lore.toString().split(" ");
                currentDurability = Integer.parseInt(loreArray[1]);
            }
        }

        Player player = event.getPlayer();
        boolean hasEnchantment = event.getItem().containsEnchantment(Enchantment.DURABILITY);
        int durabilityLevel;
        float percentage = 0;

        if (hasEnchantment){
            durabilityLevel = event.getItem().getEnchantmentLevel(Enchantment.DURABILITY);
             percentage = 100 / ((float) durabilityLevel + 1);
        }

        int customModelData = 0;

        if (event.getItem().getItemMeta().hasCustomModelData()){
            customModelData = event.getItem().getItemMeta().getCustomModelData();
        }

//        if (itemMaterial == Material.DIAMOND_SWORD && customModelData == 1 && lore != null){
//            String[] loreArray = lore.toString().split(" ");
//            Player player = event.getPlayer();
//            int currentDurability = Integer.parseInt(loreArray[1]);
//
//            currentDurability--;
//            itemMeta.setLore(Collections.singletonList("§fDurability: " + currentDurability + " / " + emeraldSwordDurability));
//            itemStack.setItemMeta(itemMeta);
//            player.getInventory().setItemInMainHand(itemStack);
//            event.setCancelled(true);
//        }
        switch (itemMaterial) {
            case DIAMOND_SWORD:
                switch (customModelData) {
                    case 1:


                        if (hasEnchantment){
                            double randomNum = Math.random()*100;

                            if (randomNum > percentage){
                                currentDurability--;
                            }
                        }
                        else {currentDurability--;}

                        itemMeta.setLore(Collections.singletonList("§fDurability: " + currentDurability + " / " + emeraldDurability));
                        itemStack.setItemMeta(itemMeta);
                        player.getInventory().setItemInMainHand(itemStack);
                        event.setCancelled(true);
                        break;
                }
            case DIAMOND_PICKAXE:
                switch (customModelData) {
                    case 1:

                }
        }
    }
}
