package me.lebobus.root.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.lebobus.root.Main;
import me.lebobus.root.kitpvp.kits.Kits;
import me.lebobus.root.utils.Files;
import net.md_5.bungee.api.ChatColor;

public class Signs implements Listener {

    public Files stats;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        String alreadyChosen = ChatColor.GRAY + "You've already chosen the " + ChatColor.AQUA + Kits.getKit(e.getPlayer()) + ChatColor.GRAY + " kit.";
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign s = (Sign) e.getClickedBlock().getState();

            Files pFile = new Files(Main.inst.getDataFolder(), e.getPlayer().getName() + ".yml");
            pFile.loadFile();

            if (s.getLine(1).contains("Arena") && s.getLine(2).equalsIgnoreCase("Main")) {
                e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 208.5D, 14.0D, 195.5D, 0.0F, 0.0F));
                return;
            }


            if (s.getLine(1).contains("Kit") && s.getLine(2).equalsIgnoreCase("PvP")) {
                int y = e.getPlayer().getLocation().getBlockY();
                if (Kits.hasKit(e.getPlayer()) == true && y >= 14 && !(Kits.getKit(e.getPlayer()).equals(Kits.PvP))) {
                    Kits.setKit(e.getPlayer(), Kits.PvP);
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && Kits.getKit(e.getPlayer()).equals(Kits.PvP) && y >= 14) {
                    e.getPlayer().sendMessage(alreadyChosen);
                    return;
                }
                Kits.setKit(e.getPlayer(), Kits.PvP);
            }


            if (s.getLine(1).contains("Kit") && s.getLine(2).equalsIgnoreCase("Archer")) {
                int y = e.getPlayer().getLocation().getBlockY();
                if (Kits.hasKit(e.getPlayer()) == true && y >= 14 && !(Kits.getKit(e.getPlayer()).equals(Kits.Archer))) {
                    Kits.setKit(e.getPlayer(), Kits.Archer);
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && Kits.getKit(e.getPlayer()).equals(Kits.Archer) && y >= 14) {
                    e.getPlayer().sendMessage(alreadyChosen);
                    return;
                }
                Kits.setKit(e.getPlayer(), Kits.Archer);
            }


            if (s.getLine(1).contains("Kit") && s.getLine(2).equalsIgnoreCase("Fireman")) {
                int y = e.getPlayer().getLocation().getBlockY();
                if (!(pFile.getString("player." + e.getPlayer().getName() + ".kits").contains("fireman"))) {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must buy the &bFireman &7kit in order to use it. Visit &b/shop&7."));
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && y >= 14 && !(Kits.getKit(e.getPlayer()).equals(Kits.Fireman))) {
                    Kits.setKit(e.getPlayer(), Kits.Fireman);
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && Kits.getKit(e.getPlayer()).equals(Kits.Fireman) && y >= 14) {
                    e.getPlayer().sendMessage(alreadyChosen);
                    return;
                }
                Kits.setKit(e.getPlayer(), Kits.Fireman);
            }


            if (s.getLine(1).contains("Kit") && s.getLine(2).equalsIgnoreCase("Vampire")) {
                int y = e.getPlayer().getLocation().getBlockY();
                if (!(pFile.getString("player." + e.getPlayer().getName() + ".kits").contains("vampire"))) {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You must buy the &bVampire &7kit in order to use it. Visit &b/shop&7."));
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && y >= 14 && !(Kits.getKit(e.getPlayer()).equals(Kits.Vampire))) {
                    Kits.setKit(e.getPlayer(), Kits.Vampire);
                    return;
                }

                if (Kits.hasKit(e.getPlayer()) == true && Kits.getKit(e.getPlayer()).equals(Kits.Vampire) && y >= 14) {
                    e.getPlayer().sendMessage(alreadyChosen);
                    return;
                }
                Kits.setKit(e.getPlayer(), Kits.Vampire);
            }


        }
    }


}
