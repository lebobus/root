package me.lebobus.root.kts;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lebobus.root.Main;
import me.lebobus.root.utils.ActionBar;

public class Kts
        implements CommandExecutor {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inKts = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inKtsRespawn = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inKtsSpec = new ArrayList();
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ArrayList<Player> inKtsStreamer = new ArrayList();

    public static boolean ktsOpen = false;
    public static boolean ktsStarted = false;

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kts")) {
            if (args.length < 1) {

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage : &b/kts &7<&bjoin&7, &bleave&7, &bhost&7, &bspectate&7, &bclose&7, &bstart&7, &badmin&7>"));
                return true;
            }
            if (args.length > 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage : &b/kts &7<&bjoin&7, &bleave&7, &bhost&7, &bspectate&7, &bclose&7, &bstart&7, &badmin&7>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("host")) {
                if (!sender.hasPermission("kts.host")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to host &bKTS&7."));
                    return true;
                }
                if (ktsOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 is already being hosted."));
                    return true;
                }
                if (ktsStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 has already started."));
                    return true;
                }
                ktsOpen = true;
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &7is now hosting &bKTS&7. &7(&b/kts join&7)"));
                return true;
            }
            if (args[0].equalsIgnoreCase("close")) {
                if (!sender.hasPermission("kts.close")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to close &bKTS&7."));
                    return true;
                }
                if (!ktsOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7is already closed."));
                    return true;
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7is now closed."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You closed &bKTS&7."));

                KtsListener.endKtsGame();

                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot join &bKTS&7."));
                    return true;
                }
                if (!ktsOpen) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7is not being hosted at the moment."));
                    return true;
                }
                if (ktsStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7has already started. (&b/kts spectate&7)"));
                    return true;
                }
                if (ktsOpen) {
                    if (!inKts.contains(player) && !inKtsSpec.contains(player) && !inKtsStreamer.contains(player)) {
                        if (player.getName().equalsIgnoreCase("Shuultz")) {
                            player.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 74.0D, 1390.5D, 180.0F, 2.0F));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You joined &bKTS&7."));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are the &bStreamer&7."));
                            //InventoryManager.saveInventory(player);
                            inKtsStreamer.add(player);
                            return true;
                        }

                        player.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 74.0D, 1390.5D, 180.0F, 2.0F));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You joined &bKTS&7."));
                        //InventoryManager.saveInventory(player);
                        inKts.add(player);
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already joined &bKTS&7."));
                        return true;
                    }

                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You already joined &bKTS&7."));
                    return true;
                }


            }
            if (args[0].equalsIgnoreCase("leave")) {
                final Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot leave &bKTS&7."));
                    return true;
                }
                if ((inKtsSpec.contains(player)) || (inKts.contains(player))) {
                    inKts.remove(player);
                    inKtsSpec.remove(player);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You left &bKTS&7."));

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                        public void run() {
                            player.setGameMode(GameMode.SURVIVAL);
                            //InventoryManager.restoreInventory(player);
                        }
                    }, 5L);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You haven't joined &bKTS&7."));
                    return true;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("spectate")) {
                Player player = (Player) sender;
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Console cannot spectate &bKTS&7."));
                    return true;
                }
                if (!ktsOpen) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 is not being hosted at the moment."));
                    return true;
                }
                if ((ktsOpen) &&
                        (!inKts.contains(player)) && (!inKtsSpec.contains(player))) {
                    inKtsSpec.add(player);
                    //InventoryManager.saveInventory(player);
                    player.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 56.0D, 1390.5D, 180.0F, 2.0F));
                    ActionBar spectate = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&7You are now spectating. Leave with (&b/kts leave&7)."));
                    spectate.sendToPlayer(player);
                    KtsListener.putInSpec();
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("start")) {
                if (!sender.hasPermission("ffa.start")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to start &bKTS&7."));
                    return true;
                }
                if (!ktsOpen) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must host &bKTS&7. (&b/kts host&7)."));
                    return true;
                }
                if (ktsStarted) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7has already started."));
                    return true;
                }
                KtsListener.startKtsTimer();

                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                Player p = (Player) sender;
                if (!p.hasPermission("kts.admin")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are not allowed to teleport there."));
                    return true;
                }
                p.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 74.0D, 1390.5D, 180.0F, 2.0F));
                return true;
            }
            if (args[0].equalsIgnoreCase("test")) {
                Player p = (Player) sender;

                World world = Bukkit.getServer().getWorld("world");
                Block Head = world.getBlockAt(-32, 66, 130);
                Head.setType(Material.SKULL);
                Head.setData((byte) 0x1);
                BlockState state = Head.getState();

                Skull skull = (Skull) state;
                skull.setRotation(BlockFace.NORTH);
                skull.setSkullType(SkullType.PLAYER);
                skull.setOwner(p.getName());
                skull.update();

                return true;
            }
        }
        return false;
    }
}
