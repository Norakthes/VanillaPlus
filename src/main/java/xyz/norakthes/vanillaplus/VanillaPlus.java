package xyz.norakthes.vanillaplus;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanillaPlus extends JavaPlugin {
//    public static ItemStack setAttackSpeed(ItemStack weapon, float amount) {
//
//    }

    static ItemStack emeraldSword = new ItemStack(Material.DIAMOND_SWORD);

    static {
        emeraldSword.getItemMeta().setDisplayName("Emerald Sword");
        emeraldSword.getItemMeta().addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed",20, AttributeModifier.Operation.ADD_NUMBER));
    }

    static ShapedRecipe emeraldSwordRecipe = new ShapedRecipe(NamespacedKey.minecraft("vanillaplus"), emeraldSword).shape(
            " * ",
            " * ",
            " - ")
            .setIngredient('*', Material.EMERALD).setIngredient('-',Material.STICK);

    static boolean compound;



    @Override
    public void onEnable() {

        getServer().addRecipe(emeraldSwordRecipe);

        this.getCommand("compound").setExecutor(new compoundDebug());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
