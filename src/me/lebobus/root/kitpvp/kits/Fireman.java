package me.lebobus.root.kitpvp.kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Fireman implements Listener {


    public Boolean percentChance(double chance) {
        return Math.random() <= chance;

    }


    @EventHandler
    public void igniteChances(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
            if (Kits.hasKit((Player) e.getDamager()) == true && Kits.getKit((Player) e.getDamager()).equals(Kits.Fireman)) {
                if (percentChance(0.10)) {
                    e.getEntity().setFireTicks(20 * 8);
                }
            }
        }
    }


    public static void giveKitFireman(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        ItemStack isword = new ItemStack(Material.IRON_SWORD);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);

        ItemMeta iswordmeta = isword.getItemMeta();
        iswordmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&oCursed sword"));
        isword.setItemMeta(iswordmeta);

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
