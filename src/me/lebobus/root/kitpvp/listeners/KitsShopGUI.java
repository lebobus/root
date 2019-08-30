package me.lebobus.root.kitpvp.listeners;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lebobus.root.Main;
import me.lebobus.root.utils.Files;
import me.lebobus.root.utils.Scoreboard;

public class KitsShopGUI implements Listener {

    private static Inventory inv;

    public Files stats;

    public KitsShopGUI() {
        inv = Bukkit.getServer().createInventory(null, 9, ChatColor.AQUA + ChatColor.ITALIC.toString() + "Buy new kits!");

        ItemStack kitpvp = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack kitarcher = new ItemStack(Material.BOW);
        ItemStack kitfireman = new ItemStack(Material.BLAZE_POWDER);
        ItemStack kitvampire = new ItemStack(Material.SPECKLED_MELON);

        ItemMeta kitpvpmetameta = kitpvp.getItemMeta();
        ItemMeta kitarchermeta = kitarcher.getItemMeta();
        ItemMeta kitfiremanmeta = kitfireman.getItemMeta();
        ItemMeta kitvampiremeta = kitvampire.getItemMeta();

        kitpvpmetameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oKit PvP"));
        kitarchermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oKit Archer"));
        kitfiremanmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oKit Fireman"));
        kitvampiremeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oKit Vampire"));

        kitpvpmetameta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7&oBasic PvP Kit."), "", ChatColor.translateAlternateColorCodes('&', "&b&oCost &7&o: &b&o10,000 credits&7&o.")));
        kitarchermeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7&oLess damage, more range."), "", ChatColor.translateAlternateColorCodes('&', "&b&oCost &7&o: &b&o10,000 credits&7&o.")));
        kitfiremanmeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7&o10% chance of igniting the enemy per hit."), "", ChatColor.translateAlternateColorCodes('&', "&b&oCost &7&o: &b&o10,000 credits&7&o.")));
        kitvampiremeta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7&oFully heals after a kill."), "", ChatColor.translateAlternateColorCodes('&', "&b&oCost &7&o: &b&o10,000 credits&7&o.")));

        kitpvp.setItemMeta(kitpvpmetameta);
        kitarcher.setItemMeta(kitarchermeta);
        kitfireman.setItemMeta(kitfiremanmeta);
        kitvampire.setItemMeta(kitvampiremeta);

        inv.setItem(0, kitpvp);
        inv.setItem(1, kitarcher);
        inv.setItem(2, kitfireman);
        inv.setItem(3, kitvampire);

        return;
    }


    public static void show(Player p) {
        p.openInventory(inv);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        ItemStack t = e.getCurrentItem();

        Files pFile = new Files(Main.inst.getDataFolder(), p.getName() + ".yml");
        pFile.loadFile();

        Integer pcredits = pFile.getInt("player." + p.getName() + ".credits");
        Integer creditsmissingkitpvp = 10000 - pcredits;
        String s = NumberFormat.getIntegerInstance(Locale.US).format(creditsmissingkitpvp);

        ArrayList<String> list = (ArrayList<String>) pFile.getStringList("player." + p.getName() + ".kits");

        if (!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
        if (t == null || t.getType() == Material.AIR) return;
        if (t.getItemMeta() == null) return;
        if (t.getItemMeta().getDisplayName().contains("Kit PvP")) {
            if (pFile.getString("player." + p.getName() + ".kits").contains("pvp")) {
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already possess the &bPvP &7kit."));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }


        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Kit Archer")) {
            if (pFile.getString("player." + p.getName() + ".kits").contains("archer")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already possess the &bArcher &7kit."));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }


        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Kit Fireman")) {
            if (pFile.getString("player." + p.getName() + ".kits").contains("fireman")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already possess the &bFireman &7kit."));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if (pcredits >= 10000) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You bought the &bFireman &7kit for &b10,000 credits&7."));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + p.getName() + " &7bought the &bFireman &7kit for &b10,000 credits&7."));
                list.add("fireman");
                pFile.set("player." + p.getName() + ".kits", list);
                pFile.saveFile();
                Scoreboard.takeCredits((Player) e.getWhoClicked(), 10000);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You don't have enough &bcredits&7. You're missing &c" + s + " credits&7."));
            }
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }


        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Kit Vampire")) {
            if (pFile.getString("player." + p.getName() + ".kits").contains("vampire")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already possess the &bVampire &7kit."));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if (pcredits >= 10000) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You bought the &bVampire &7kit for &b10,000 credits&7."));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + p.getName() + " &7bought the &bVampire &7kit for &b10,000 credits&7."));
                list.add("vampire");
                pFile.set("player." + p.getName() + ".kits", list);
                pFile.saveFile();
                Scoreboard.takeCredits((Player) e.getWhoClicked(), 10000);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You don't have enough &bcredits&7. You're missing &c" + s + " credits&7."));
            }
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }


    }


}
