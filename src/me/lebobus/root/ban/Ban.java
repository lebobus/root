package me.lebobus.root.ban;

import me.lebobus.root.Main;
import me.lebobus.root.logs.Logs;
import me.lebobus.root.utils.Files;
import me.lebobus.root.utils.IntegerCheck;
import org.bukkit.*;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import java.util.Date;


public class Ban implements CommandExecutor, Listener {

    private Logs logs = new Logs();

    private IntegerCheck intCheck = new IntegerCheck();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == Result.KICK_BANNED) {
            Player p = event.getPlayer();
            BanList name = Bukkit.getBanList(Type.NAME);
            BanList ip = Bukkit.getBanList(Type.IP);
            BanEntry ban = name.getBanEntry(p.getName());
            if (ban.getExpiration() == null) {
                event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-+---------------------------------------+-\n\n&7You have been &bbanned&7 by &b" + ban.getSource() + "&7.\n&7Reason: &b" + ban.getReason() + "&7.\n&7Appeal on &bhttp://nodepvp.com&7.\n\n&8&m-+---------------------------------------+-"));
                return;
            }
            if (ban.getExpiration() != null) {
                event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-+---------------------------------------+-\n\n&7You have been &bbanned&7 by &b" + ban.getSource() + "&7.\n&7Expires: &b" + ban.getExpiration() + "\n&7Reason: &b" + ban.getReason() + "&7.\n&7Appeal on &bhttp://nodepvp.com&7.\n\n&8&m-+---------------------------------------+-"));
                return;
            }
        }
    }


    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        //Player p = (Player) sender;

        Files pFile = new Files(Main.inst.getDataFolder(), sender.getName() + ".yml");
        pFile.loadFile();

        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (!sender.hasPermission("core.ban")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to &bban&7."));
                return true;
            }

            if (args.length < 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invalid arguments."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/ban &7[&bplayer&7] [&bduration in days&7] [&breason&7]"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Example: &b/ban Fortnite 1 Hacking &8(&7-1 for PERMABAN&8)&7."));
                return true;
            }

            OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
            Files pFilet = new Files(Main.inst.getDataFolder(), target.getName() + ".yml");
            pFilet.loadFile();
            if (args.length >= 3) {

                if (pFilet.getBoolean("player." + target.getName() + ".banned")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + target.getName() + "&7 is already &bbanned&7."));
                    return true;
                }

                if (!intCheck.isInt(args[1])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invalid arguments."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/ban &7[&bplayer&7] [&cduration in days&7] [&breason&7]"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Example: &b/ban Fortnite 1 Hacking &8(&7-1 for PERMABAN&8)&7."));
                    return true;
                }

                Integer arg1 = Integer.parseInt(args[1]);
                if (arg1 == -1) {
                    StringBuilder buffer = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        buffer.append(' ').append(args[i]);
                    }
                    String s = buffer.toString().trim();

                    pFilet.set("player" + "." + target.getName() + "." + "banned", true);
                    pFilet.saveFile();
                    logs.createLog((Player) sender, target, "PERMABAN/IP-BAN", "N/A", s);
                    Bukkit.getBanList(Type.NAME).addBan(target.getName(), s, null, sender.getName());
                    Bukkit.getBanList(Type.IP).addBan(pFilet.getString("player." + target.getName() + ".ip"), s, null, sender.getName());
                    Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7has banned&b " + target.getName() + " &7for&b " + s + "&7."));
                    if (target.isOnline()) {
                        ((Player) target).kickPlayer(ChatColor.translateAlternateColorCodes('&', "&8&m-+---------------------------------------+-\n\n&7You have been &bbanned&7 by &b" + sender.getName() + "&7.\n&7Reason: &b" + s + "&7.\n&7Appeal on &bhttp://nodepvp.com&7.\n\n&8&m-+---------------------------------------+-"));
                    }
                    return true;
                }

                StringBuilder buffer = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    buffer.append(' ').append(args[i]);
                }
                String s = buffer.toString().trim();

                Integer arg1duration = Integer.parseInt(args[1]);
                if (target.isOnline()) {
                    ((Player) target).kickPlayer(ChatColor.translateAlternateColorCodes('&', "&8&m-+---------------------------------------+-\n\n&7You have been &bbanned&7 by &b" + sender.getName() + "&7.\n&7Expires: &b" + new Date(System.currentTimeMillis() + arg1duration + (24 * 60 * 60 * 1000)) + "&7.\n&7Reason: &b" + s + "&7.\n&7Appeal on &bhttp://nodepvp.com&7.\n\n&8&m-+---------------------------------------+-"));
                }

                pFilet.set("player." + target.getName() + ".banned", true);
                pFilet.saveFile();
                logs.createLog((Player) sender, target, "TEMPBAN/IP-BAN", args[1] + " day(s).", s);
                Bukkit.getBanList(Type.NAME).addBan(target.getName(), s, new Date(System.currentTimeMillis() + arg1duration + (24 * 60 * 60 * 1000)), sender.getName());
                Bukkit.getBanList(Type.IP).addBan(pFilet.getString("player." + target.getName() + ".ip"), s, new Date(System.currentTimeMillis() + arg1duration + (24 * 60 * 60 * 1000)), sender.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7has banned&b " + target.getName() + " &7for&b " + s + "&7 for&b " + args[1] + " &7day(s)&7."));
            }
            return true;
        }


        if (cmd.getName().equalsIgnoreCase("unban")) {
            if (!sender.hasPermission("core.unban")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to &bunban&7."));
                return true;
            }

            if (args.length == 0 || args.length > 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invalid arguments."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &b/unban &7[&bplayer&7]"));
                return true;
            }

            OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
            Files pFilet = new Files(Main.inst.getDataFolder(), target.getName() + ".yml");
            pFilet.loadFile();
            if (pFilet.getBoolean("player." + target.getName() + ".banned")) {
                Bukkit.getBanList(Type.NAME).pardon(target.getName());
                Bukkit.getBanList(Type.IP).pardon(pFilet.getString("player." + target.getName() + ".ip"));
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7has unbanned&b " + target.getName() + "&7."));
                pFilet.set("player." + target.getName() + ".banned", false);
                pFilet.saveFile();
                logs.createLog((Player) sender, target, "UNBAN", "N/A", "N/A");
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + target.getName() + "&7 is not &bbanned&7."));
                return true;
            }
        }

        return true;

    }


}
