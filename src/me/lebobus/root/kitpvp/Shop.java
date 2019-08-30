package me.lebobus.root.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lebobus.root.kitpvp.listeners.KitsShopGUI;
import me.lebobus.root.utils.Cuboid;
import net.md_5.bungee.api.ChatColor;

public class Shop implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("shop")) {
            Player p = (Player) sender;
            Location mainl1 = new Location(Bukkit.getWorld("world"), 213.0D, 16.0D, 200.0D);
            Location mainl2 = new Location(Bukkit.getWorld("world"), 203.0D, 13.0D, 190.0D);

            Location spawnl1 = new Location(Bukkit.getWorld("world"), 24.0D, 19.0D, -26.0D);
            Location spawnl2 = new Location(Bukkit.getWorld("world"), -40.0D, 5.0D, 16.0D);

            if (!Cuboid.inCuboid(p.getLocation(), spawnl1, spawnl2) && !Cuboid.inCuboid(p.getLocation(), mainl1, mainl2)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must be in either the &bspawn &7or the &bmain arena &7to access the &bshop&7."));
                return true;
            }

            if (args.length > 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage : &b/shop"));
                return true;
            } else {
                KitsShopGUI.show((Player) sender);
            }
        }
        return false;
    }


}
