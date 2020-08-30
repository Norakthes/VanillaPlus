package xyz.norakthes.vanillaplus;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
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
    static ItemStack emeraldPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);

    static int emeraldDurability = 10; //TODO make durability configurable

    // Emerald sword configuration
    static {
        ItemMeta emeraldSwordMeta = emeraldSword.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§fDurability: " + emeraldDurability +" / "+ emeraldDurability);

        emeraldSwordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackSpeed", 3, AttributeModifier.Operation.ADD_NUMBER));
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

        emeraldPickaxeMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.attackSpeed", 3, AttributeModifier.Operation.ADD_NUMBER));
        emeraldPickaxeMeta.setDisplayName("§fEmerald Pickaxe");
        emeraldPickaxeMeta.setCustomModelData(1);

        emeraldPickaxeMeta.setLore(lore);
        emeraldPickaxe.setItemMeta(emeraldPickaxeMeta);
    }
    // TODO make custom namespacedKey
    static ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("emerald_sword"), emeraldSword).shape(
            " * ",
            " * ",
            " - ")
            .setIngredient('*', Material.EMERALD)
            .setIngredient('-', Material.STICK);

    static ShapedRecipe emeraldPickaxeRecipe = new ShapedRecipe(NamespacedKey.minecraft("emerald_pickaxe"), emeraldPickaxe).shape(
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
        switch (itemMaterial) {
            case DIAMOND_SWORD:
                emeraldDurability(event, itemStack, itemMeta, currentDurability, player, hasEnchantment, percentage, customModelData);
            case DIAMOND_PICKAXE:
                emeraldDurability(event, itemStack, itemMeta, currentDurability, player, hasEnchantment, percentage, customModelData);
        }
    }

    private void emeraldDurability(PlayerItemDamageEvent event, ItemStack itemStack, ItemMeta itemMeta, int currentDurability, Player player, boolean hasEnchantment, float percentage, int customModelData) {
        switch (customModelData) {
            case 1:
                if (hasEnchantment){
                    double randomNum = Math.random()*100;

                    if (randomNum > percentage){
                        currentDurability--;
                    }
                }
                else {currentDurability--;}

                if (currentDurability < 1) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                }

                else {
                    itemMeta.setLore(Collections.singletonList("§fDurability: " + currentDurability + " / " + emeraldDurability));
                    itemStack.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(itemStack);
                    event.setCancelled(true);
                    break;
                }
        }
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        List<String> lore = null;
        if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()){
            lore = event.getPlayer().getInventory().getItemInMainHand().getLore();
        }
        String[] loreArray;
        if (!lore.isEmpty()){
            loreArray = lore.toString().split(" ");
        }
        
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        double randomNum = Math.random()*100;
        int enchantmentLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DURABILITY);
        float percentage = 100 / (float)(enchantmentLevel + 1);
        if (player.getName().equals("Norakthes")){

            Bukkit.broadcastMessage(percentage + " | " + enchantmentLevel + " | " + randomNum);
            if (randomNum > percentage){
                Bukkit.broadcastMessage(ChatColor.GREEN + "Yes");
            }
        }
    }
}
