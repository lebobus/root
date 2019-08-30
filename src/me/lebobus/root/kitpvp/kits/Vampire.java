package me.lebobus.root.kitpvp.kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Vampire implements Listener {

    @EventHandler
    public void lifestealOnDeath(PlayerDeathEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player) && (Kits.getKit(e.getEntity().getKiller()).equals(Kits.Vampire))) {
            e.getEntity().getKiller().setHealth(20);
        }
    }

    public static void giveKitVampire(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        ItemStack isword = new ItemStack(Material.GOLD_SWORD);
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);

        ItemMeta iswordmeta = isword.getItemMeta();
        iswordmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&oBlood stealer"));
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
