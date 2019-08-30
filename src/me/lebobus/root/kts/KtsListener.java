package me.lebobus.root.kts;

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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
public class KtsListener
        implements Listener {
    @SuppressWarnings("deprecation")
    public static void winEffects() {
        for (Player players : Kts.inKts) {
            Location loc = players.getLocation();
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            RandomFireWorks.getManager().launchRandomFirework(loc);
            players.getInventory().clear();

            World world = Bukkit.getServer().getWorld("world");
            Block Head = world.getBlockAt(-32, 66, 124);
            Head.setType(Material.SKULL);
            Head.setData((byte) 0x1);
            BlockState state = Head.getState();

            Skull skull = (Skull) state;
            skull.setRotation(BlockFace.SOUTH);
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(players.getName());
            skull.update();


            Block block = world.getBlockAt(-32, 65, 125);
            BlockState statesign = block.getState();
            if (!(statesign instanceof Sign)) {
                return; // block is not a sign
            }
            Sign sign = (Sign) statesign;
            sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&LLATEST KTS"));
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&LWINNER:"));
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&L" + players.getName()));
            sign.update();

        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
                endKtsGame();
            }
        }, 100L);
    }

    public static void endKtsGame() {
        for (Player players : Kts.inKts) {
            players.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            //InventoryManager.restoreInventory(players);
            players.getInventory().clear();
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        for (Player players : Kts.inKtsSpec) {
            players.setGameMode(GameMode.SURVIVAL);
            //InventoryManager.restoreInventory(players);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        for (Player players : Kts.inKtsRespawn) {
            //InventoryManager.restoreInventory(players);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        for (Player players : Kts.inKtsStreamer) {
            //InventoryManager.restoreInventory(players);
            players.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            players.removePotionEffect(PotionEffectType.REGENERATION);
            players.getInventory().clear();
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + players.getName() + " world");
        }
        Kts.inKts.clear();
        Kts.inKtsSpec.clear();
        Kts.inKtsRespawn.clear();
        Kts.ktsOpen = false;
        Kts.ktsStarted = false;

    }

    public static void putInSpec() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
                for (Player players : Kts.inKtsSpec) {
                    players.setGameMode(GameMode.SPECTATOR);
                }
            }
        }, 5L);
    }

    public static int sec = 31;

    public static void startKtsTimer() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b30 &7seconds until &bKTS&7 starts. (&b/kts join&7)."));
        new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                if (--KtsListener.sec <= 0) {
                    for (Player players : Kts.inKts) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 starting in :&b 0"));
                        resetTimer.sendToPlayer(players);
                    }
                    cancel();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bKTS &7has started. (&b/kts spectate&7)."));

                    Kts.ktsStarted = false;
                    for (Player players : Kts.inKts) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS starting!"));
                        resetTimer.sendToPlayer(players);

                        players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 10));
                        ItemStack sword = new ItemStack(Material.STONE_SWORD);
                        ItemMeta im = sword.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bViewer's deadly sword"));
                        sword.setItemMeta(im);
                        sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                        players.setItemInHand(sword);

                        players.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 56.0D, 1390.5D, 180.0F, 2.0F));
                    }
                    for (Player players : Kts.inKtsStreamer) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS starting!"));
                        resetTimer.sendToPlayer(players);
                        players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 2));
                        players.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 2));

                        ItemStack stick = new ItemStack(Material.STICK);
                        ItemMeta im = stick.getItemMeta();
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMagic Stick"));
                        stick.setItemMeta(im);
                        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                        players.setItemInHand(stick);

                        players.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 56.0D, 1390.5D, 180.0F, 2.0F));
                    }
                    for (Player players : Kts.inKtsSpec) {
                        ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS starting!"));
                        resetTimer.sendToPlayer(players);
                        players.teleport(new Location(Bukkit.getWorld("FFA"), 1596.5D, 21.0D, 1340.5D));
                    }
                    Kts.ktsStarted = true;
                    KtsListener.sec = 31;
                    return;
                }
                for (Player players : Kts.inKts) {
                    ActionBar displayTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 starting in :&b " + KtsListener.sec));
                    displayTimer.sendToPlayer(players);
                }
                for (Player players : Kts.inKtsStreamer) {
                    ActionBar displayTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 starting in :&b " + KtsListener.sec));
                    displayTimer.sendToPlayer(players);
                }
                for (Player players : Kts.inKtsSpec) {
                    ActionBar displayTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&bKTS&7 starting in :&b " + KtsListener.sec));
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
        if (e.getItemDrop().getItemStack().getType() == Material.STONE_SWORD) {
            String magicsword = ChatColor.translateAlternateColorCodes('&', "&bViewer's deathly sword");
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(magicsword)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        if (Kts.inKtsRespawn.contains(player)) {
            Kts.inKtsRespawn.remove(player);
            if (!Kts.ktsStarted) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    public void run() {
                        //InventoryManager.restoreInventory(player);
                    }
                }, 5L);
                return;
            }
            Kts.inKtsSpec.add(player);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                public void run() {
                    ActionBar resetTimer = new ActionBar(ChatColor.translateAlternateColorCodes('&', "&7You are now spectating. Leave with (&b/kts leave&7)."));
                    resetTimer.sendToPlayer(player);
                    player.teleport(new Location(Bukkit.getWorld("FFA"), 1787.5D, 56.0D, 1390.5D, 180.0F, 2.0F));
                    KtsListener.putInSpec();
                }
            }, 5L);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = player.getKiller();
        if (Kts.inKtsStreamer.contains(player)) {
            Kts.inKtsStreamer.remove(player);
            if (Kts.inKtsStreamer.size() == 0 && Kts.ktsStarted == true) {
                for (Player players : Kts.inKtsStreamer) {
                    player.getInventory().clear();
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + killer.getName() + " &7won &bKTS&7."));
                winEffects();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Kts.inKts.contains(player)) {
            Kts.inKts.remove(player);
        }
        if (Kts.inKtsStreamer.contains(player)) {
            Kts.inKtsStreamer.remove(player);
            endKtsGame();
        }
        if (Kts.inKtsSpec.contains(player)) {
            Kts.inKts.remove(player);
        }
        if (Kts.inKtsRespawn.contains(player)) {
            Kts.inKts.remove(player);
        }
    }


    @EventHandler
    public void onFriendlyFire(EntityDamageByEntityEvent event) {
        if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
            Player damaged = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if ((Kts.inKts.contains(damaged)) && (Kts.inKts.contains(damager))) {
                event.setCancelled(true);
                return;
            }
        }
    }

//  @EventHandler
//  public void respawnOnPlayerDeath(PlayerDeathEvent event) {
//      if(event.getEntity() instanceof Player) {
//          event.getEntity().spigot().respawn();
//      }
//  }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("kts.bypass")) {
            return;
        }
        if ((Kts.inKts.contains(player)) || (Kts.inKtsSpec.contains(player))) {
            if (event.getMessage().equalsIgnoreCase("/kts leave")) {
                event.setCancelled(true);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You left &bKTS&7."));
                Kts.inKts.remove(player);
                Kts.inKtsSpec.remove(player);
                return;
            }
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You can't perform commands in &bKTS&7."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7If you want to leave &bKTs&7, use (&b/kts leave&7)"));
        }
    }
}
