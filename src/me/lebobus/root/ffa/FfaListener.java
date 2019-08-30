package me.lebobus.root.ffa;

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
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lebobus.root.Main;
import me.lebobus.root.utils.ActionBar;
import me.lebobus.root.utils.RandomFireWorks;

@SuppressWarnings("unused")
public class FfaListener
        implements Listener {
    @SuppressWarnings("deprecation")
    public static void winEffects() {
        for (Player players : Ffa.inFfa) {
            Location loc = players.getLocation();
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            players.getInventory().clear();

            World world = Bukkit.getServer().getWorld("world");
            Block Head = world.getBlockAt(-32, 66, 130);
            Head.setType(Material.SKULL);
            Head.setData((byte) 0x1);
            BlockState state = Head.getState();

            Skull skull = (Skull) state;
            skull.setRotation(BlockFace.NORTH);
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(players.getName());
            skull.update();


            Block block = world.getBlockAt(-32, 65, 129);
            BlockState statesign = block.getState();
            if (!(statesign instanceof Sign)) {
                return; // block is not a sign
            }
            Sign sign = (Sign) statesign;
            sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&LLATEST FFA"));
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&LWINNER:"));
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&L" + players.getName()));
            sign.update();
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
                endFfaGame();
            }
        }, 100L);
    }

    public static void endFfaGame() {
        for (Player players : Ffa.inFfa) {
            players.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            //InventoryManager.restoreInventory(players);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        for (Player players : Ffa.inFfaSpec) {
            players.setGameMode(GameMode.SURVIVAL);
            //InventoryManager.restoreInventory(players);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        for (Player players : Ffa.inFfaRespawn) {
            //InventoryManager.restoreInventory(players);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        Ffa.inFfa.clear();
        Ffa.inFfaSpec.clear();
        Ffa.inFfaRespawn.clear();
        Ffa.ffaOpen = false;
        Ffa.ffaStarted = false;

    }

    public static void putInSpec() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
                for (Player players : Ffa.inFfaSpec) {
                    players.setGameMode(GameMode.SPECTATOR);
                }
            }
        }, 5L);
    }

    public static int sec = 31;

    public static void startFfaTimer() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b30 &7seconds until &bFFA&7 starts. (&b/ffa join&7)."));
        new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                if (--FfaListener.sec <= 0) {
                    for (Player players : Ffa.inFfa) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 starting in :&b 0"));
                        resetTimer.sendToPlayer(players);
                    }
                    cancel();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bFFA &7has started. (&b/ffa spectate&7)."));

                    Ffa.ffaStarted = false;
                    for (Player players : Ffa.inFfa) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bFFA starting!"));
                        resetTimer.sendToPlayer(players);
                        players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));

                        ItemStack stick = new ItemStack(Material.STICK);
                        ItemMeta im = stick.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMagic Stick"));
                        stick.setItemMeta(im);
                        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                        players.setItemInHand(stick);

                        players.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 21.0D, 1340.5D));
                    }
                    for (Player players : Ffa.inFfaSpec) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bFFA starting!"));
                        resetTimer.sendToPlayer(players);
                        players.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 21.0D, 1340.5D));
                    }
                    Ffa.ffaStarted = true;
                    FfaListener.sec = 31;
                    return;
                }
                for (Player players : Ffa.inFfa) {
                    ActionBar displayTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 starting in :&b " + FfaListener.sec));
                    displayTimer.sendToPlayer(players);
                }
                for (Player players : Ffa.inFfaSpec) {
                    ActionBar displayTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bFFA&7 starting in :&b " + FfaListener.sec));
                    displayTimer.sendToPlayer(players);
                }
            }
        }.runTaskTimer(Main.getPlugin(), 20L, 20L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == Material.STICK) {
            String magicstick = ChatColor.translateAlternateColorCodes('&', "&bMagic Stick");
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(magicstick)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        if (Ffa.inFfaRespawn.contains(player)) {
            Ffa.inFfaRespawn.remove(player);
            if (!Ffa.ffaStarted) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    public void run() {
                        //InventoryManager.restoreInventory(player);
                    }
                }, 5L);
                return;
            }
            Ffa.inFfaSpec.add(player);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                public void run() {
                    ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&7You are now spectating. Leave with (&b/ffa leave&7)."));
                    resetTimer.sendToPlayer(player);
                    player.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 21.0D, 1340.5D));
                    FfaListener.putInSpec();
                }
            }, 5L);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (Ffa.inFfa.contains(player)) {
            Ffa.inFfaRespawn.add(player);
            Ffa.inFfa.remove(player);
            if (Ffa.inFfa.size() == 1) {
                for (Player players : Ffa.inFfa) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + players.getName() + " &7won &bFFA&7."));
                    player.getInventory().clear();
                }
                winEffects();
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onVoidEnter(PlayerMoveEvent e) {
        if ((e.getTo().getBlockY() < -1) && (!e.getPlayer().isDead())) {
            e.getPlayer().setHealth(0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Ffa.inFfa.contains(player)) {
            Ffa.inFfa.remove(player);
        }
        if (Ffa.inFfaSpec.contains(player)) {
            Ffa.inFfa.remove(player);
        }
        if (Ffa.inFfaRespawn.contains(player)) {
            Ffa.inFfa.remove(player);
        }
    }


    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ffa.bypass")) {
            return;
        }
        if ((Ffa.inFfa.contains(player)) || (Ffa.inFfaSpec.contains(player))) {
            if (event.getMessage().equalsIgnoreCase("/ffa leave")) {
                event.setCancelled(true);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You left &bFFA&7."));
                Ffa.inFfa.remove(player);
                Ffa.inFfaSpec.remove(player);
                return;
            }
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You can't perform commands in &bFFA&7."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7If you want to leave &bFFA&7, use (&b/ffa leave&7)"));
        }
    }
}
