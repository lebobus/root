package me.lebobus.root.kick;

import me.lebobus.root.logs.Logs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Kick implements CommandExecutor {

    private Logs logs = new Logs();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kick")) {
            if (!sender.hasPermission("core.kick")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to &bkick&7."));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invalid arguments."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/kick &7[&bplayer&7] [&breason&7]"));
                return true;
            }


            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (args.length >= 2) {

                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + args[0] + "&7 is not &bonline&7."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/kick &7[&bplayer&7]"));
                    return true;
                }

                if (target.isOnline()) {
                    StringBuilder buffer = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        buffer.append(' ').append(args[i]);
                    }
                    String s = buffer.toString().trim();

                    logs.createLog((Player) sender, target, "KICK", "N/A", s);
                    ((Player) target).kickPlayer(ChatColor.translateAlternateColorCodes('&', "&7You have been &bkicked&7 for &b" + s + "&7."));
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7has kicked&b " + target.getName() + "&7 for &b" + s + "&7."));
                    return true;
                }

            }


        }
        return true;
    }
}
