package me.lebobus.root.logs;

import me.lebobus.root.Main;
import me.lebobus.root.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Command implements CommandExecutor {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("logs")) {
            if (!sender.hasPermission("core.logs")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to access &blogs&7."));
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invalid arguments."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/logs &7[&bplayer&7]"));
                return true;
            }


            OfflinePlayer target = Bukkit.getServer().getOfflinePlayer((args[0]));


            Files playerFile = new Files(Main.inst.getDataFolder(), target.getName() + "_logs.yml");
            if (!playerFile.fileExists()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7There are no logs for &b" + target.getName()));
                return true;
            }

            final File logsfile = new File(Main.inst.getDataFolder(), target.getName() + "_logs.yml");
            FileConfiguration playerFileconf = YamlConfiguration.loadConfiguration(logsfile);

            playerFile.loadFile();

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + target.getName() + "&7's logs :"));
            for (String id : playerFileconf.getConfigurationSection("logs").getKeys(false)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7ID: &b" + id));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Staff: &b" + playerFile.getString("logs." + id + "." + ".staff")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Type: &b" + playerFile.getString("logs." + id + "." + ".type")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Duration: &b" + playerFile.getString("logs." + id + "." + ".duration")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Reason: &b" + playerFile.getString("logs." + id + "." + ".reason")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Date: &b" + playerFile.getString("logs." + id + "." + ".date")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------------------------"));
            }


        }
        return true;
    }

}
