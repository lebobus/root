package me.lebobus.root.kitpvp.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PvP implements Listener {

    public static void giveKitPvP(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        ItemStack isword = new ItemStack(Material.IRON_SWORD);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);

        isword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
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

        for (int slot = 0; slot < p.getInventory().getSize(); slot++) {
            if (p.getInventory().getItem(slot) == null) {
                p.getInventory().setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        p.getInventory().setArmorContents(armor);
        p.updateInventory();
    }

}
