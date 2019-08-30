package me.lebobus.root.ffa;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lebobus.root.Main;
import me.lebobus.root.utils.ActionBar;
import me.lebobus.root.utils.RandomFireWorks;

@SuppressWarnings("unused")
public class Ffa
        implements CommandExecutor {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inFfa = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inFfaRespawn = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inFfaSpec = new ArrayList();
    public static boolean ffaOpen = false;
    public static boolean ffaStarted = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ffa")) {
            if (args.length < 1) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage : &b/ffa &7<&bjoin&7, &bleave&7, &bhost&7, &bspectate&7, &bclose&7, &bstart&7, &badmin&7>"));
                return true;
            }
            if (args.length > 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage : &b/ffa &7<&bjoin&7, &bleave&7, &bhost&7, &bspectate&7, &bclose&7, &bstart&7, &badmin&7>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("host")) {
                if (!sender.hasPermission("ffa.host")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to host &bFFA&7."));
                    return true;
                }
                if (ffaOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 is already being hosted."));
                    return true;
                }
                if (ffaStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 has already started."));
                    return true;
                }
                ffaOpen = true;
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7is now hosting &bFFA&7. &7(&b/ffa join&7)"));
                return true;
            }
            if (args[0].equalsIgnoreCase("close")) {
                if (!sender.hasPermission("ffa.close")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to close &bFFA&7."));
                    return true;
                }
                if (!ffaOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7is already closed."));
                    return true;
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7is now closed."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You closed &bFFA&7."));

                FfaListener.endFfaGame();

                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot join &bFFA&7."));
                    return true;
                }
                if (!ffaOpen) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7is not being hosted at the moment."));
                    return true;
                }
                if (ffaStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7has already started. (&b/ffa spectate&7)"));
                    return true;
                }
                if (ffaOpen) {
                    if (!inFfa.contains(player)) {
                        player.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 39.0D, 1340.5D, 180.0F, 2.0F));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You joined &bFFA&7."));
                        //InventoryManager.saveInventory(player);
                        inFfa.add(player);
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7is now closed."));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already joined &bFFA&7."));
                        return true;
                    }
                }
            }
            if (args[0].equalsIgnoreCase("leave")) {
                final Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot leave &bFFA&7."));
                    return true;
                }
                if ((inFfaSpec.contains(player)) || (inFfa.contains(player))) {
                    inFfa.remove(player);
                    inFfaSpec.remove(player);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You left &bFFA&7."));

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                        public void run() {
                            player.setGameMode(GameMode.SURVIVAL);
                            //InventoryManager.restoreInventory(player);
                        }
                    }, 5L);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You haven't joined &bFFA&7."));
                    return true;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("spectate")) {
                Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot spectate &bFFA&7."));
                    return true;
                }
                if (!ffaOpen) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 is not being hosted at the moment."));
                    return true;
                }
                if ((ffaOpen) &&
                        (!inFfa.contains(player)) && (!inFfaSpec.contains(player))) {
                    inFfaSpec.add(player);
                    //InventoryManager.saveInventory(player);
                    player.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 21.0D, 1340.5D));
                    ActionBar spectate = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&7You are now spectating. Leave with (&b/ffa leave&7)."));
                    spectate.sendToPlayer(player);
                    FfaListener.putInSpec();
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("start")) {
                if (!sender.hasPermission("ffa.start")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to start &bFFA&7."));
                    return true;
                }
                if (!ffaOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must host &bFFA&7. (&b/ffa host&7)."));
                    return true;
                }
                if (ffaStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7has already started."));
                    return true;
                }
                FfaListener.startFfaTimer();

                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                Player p = (Player) sender;
                if (!p.hasPermission("ffa.admin")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to teleport there."));
                    return true;
                }
                p.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 39.0D, 1340.5D, 180.0F, 2.0F));
                return true;
            }
            if (args[0].equalsIgnoreCase("test")) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                RandomFireWorks.getManager().launchRandomFirework(loc);
                RandomFireWorks.getManager().launchRandomFirework(loc);
                RandomFireWorks.getManager().launchRandomFirework(loc);
                RandomFireWorks.getManager().launchRandomFirework(loc);
                RandomFireWorks.getManager().launchRandomFirework(loc);
                RandomFireWorks.getManager().launchRandomFirework(loc);
                return true;
            }
        }
        return false;
    }
}
