package me.lebobus.root.kitpvp.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Archer {

    public static void giveKitArcher(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        ItemStack isword = new ItemStack(Material.IRON_SWORD);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);

        isword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        ItemStack[] armor = new ItemStack[4];
        armor[0] = boots;
        armor[1] = leggings;
        armor[2] = chestplate;
        armor[3] = helmet;

        p.getInventory().setItem(0, isword);
        p.getInventory().setItem(1, bow);
        p.getInventory().setItem(17, new ItemStack(Material.ARROW));

        for (int slot = 0; slot < p.getInventory().getSize(); slot++) {
            if (p.getInventory().getItem(slot) == null) {
                p.getInventory().setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        p.getInventory().setArmorContents(armor);
        p.updateInventory();
    }

}
