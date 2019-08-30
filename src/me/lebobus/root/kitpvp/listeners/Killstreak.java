package me.lebobus.root.kitpvp.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.lebobus.root.Main;
import me.lebobus.root.kitpvp.kits.Kits;
import me.lebobus.root.utils.Files;
import me.lebobus.root.utils.Scoreboard;

public class Killstreak implements Listener {

    public static Map<UUID, Integer> killstreak = new HashMap<UUID, Integer>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if ((event.getEntity() instanceof Player) && (event.getEntity().getKiller() instanceof Player)) {
            Files pFile = new Files(Main.inst.getDataFolder(), event.getEntity().getPlayer().getName() + ".yml");
            pFile.loadFile();

            Entity p = event.getEntity().getPlayer();
            Entity killer = event.getEntity().getPlayer().getKiller();

            Scoreboard.addKill(event.getEntity().getPlayer().getKiller());
            Scoreboard.addDeath(event.getEntity().getPlayer());
            Scoreboard.addKillstreak(event.getEntity().getPlayer().getKiller());
            Scoreboard.addCredits(event.getEntity().getPlayer().getKiller(), 15);
            Scoreboard.takeCredits(event.getEntity().getPlayer(), 5);


            Integer ks = Killstreak.killstreak.get(p.getUniqueId());
            Integer killerbestks = pFile.getInt("player." + killer.getName() + ".bestkillstreak");
            Integer bestks = pFile.getInt("player." + p.getName() + ".bestkillstreak");
            Integer killerks = Killstreak.killstreak.get(killer.getUniqueId());

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b" + killer.getName() + " &7has killed &c" + p.getName() + "&7."));
            killer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b+15c &7for killing &b" + p.getName() + "&7."));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c-5c &7for getting killed by &b" + killer.getName() + "&7."));

            if (Killstreak.killstreak.containsKey(killer.getUniqueId())) {
                if (killerks > killerbestks) {
                    Scoreboard.setBestkillstreak(event.getEntity().getPlayer().getKiller(), killerks);
                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You've set a new &bkillstreak &7record of &b" + killerks + "&7."));
                }
            }

            if (Killstreak.killstreak.containsKey(p.getUniqueId())) {
                if (ks > bestks) {
                    Scoreboard.setBestkillstreak(event.getEntity().getPlayer(), ks);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You've set a new &bkillstreak &7record of &b" + ks + "&7."));
                    Kits.clearPlayer((Player) p);
                }
            }

            Killstreak.killstreak.put(p.getUniqueId(), 0);
            Scoreboard.refreshScoreboard((Player) p);
            Kits.clearPlayer((Player) p);
        }
    }


}