package me.lebobus.root.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class InventoryManager {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static HashMap<String, ItemStack[]> armourContents = new HashMap();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static HashMap<String, ItemStack[]> inventoryContents = new HashMap();

    public static void saveInventory(Player player) {
        armourContents.put(player.getName(), player.getInventory().getArmorContents());
        inventoryContents.put(player.getName(), player.getInventory().getContents());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    public static void restoreInventory(Player player) {
        player.getInventory().clear();

        player.getInventory().setContents((ItemStack[]) inventoryContents.get(player.getName()));
        player.getInventory().setArmorContents((ItemStack[]) armourContents.get(player.getName()));

        armourContents.remove(player.getName());
        inventoryContents.remove(player.getName());
    }
}
