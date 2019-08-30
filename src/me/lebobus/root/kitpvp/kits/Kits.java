package me.lebobus.root.kitpvp.kits;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import net.md_5.bungee.api.ChatColor;


public enum Kits implements Listener {

    PvP, Archer, Fireman, Vampire;

    public static HashMap<UUID, Kits> currentKit = new HashMap<UUID, Kits>();

    public static String kitReceived;
    public static String alreadyChosen;

    public static void setKit(Player p, Kits kit) {
        currentKit.put(p.getUniqueId(), kit);

        String kitReceived = ChatColor.GRAY + "You've chosen the " + ChatColor.AQUA + getKit(p) + ChatColor.GRAY + " kit.";

        if (getKit(p).equals(PvP)) {
            me.lebobus.root.kitpvp.kits.PvP.giveKitPvP(p);
            p.sendMessage(kitReceived);
        }

        if (getKit(p).equals(Archer)) {
            me.lebobus.root.kitpvp.kits.Archer.giveKitArcher(p);
            p.sendMessage(kitReceived);
        }

        if (getKit(p).equals(Fireman)) {
            me.lebobus.root.kitpvp.kits.Fireman.giveKitFireman(p);
            p.sendMessage(kitReceived);
        }

        if (getKit(p).equals(Vampire)) {
            me.lebobus.root.kitpvp.kits.Vampire.giveKitVampire(p);
            p.sendMessage(kitReceived);
        }

    }

    public static Kits getKit(Player p) {
        return currentKit.get(p.getUniqueId());
    }

    public static boolean hasKit(Player p) {
        if (currentKit.containsKey(p.getUniqueId())) return true;
        return false;
    }

    public static void clearPlayer(Player p) {
        currentKit.remove(p.getUniqueId());
    }

    public static String getKitName(Player p) {
        return getKit(p).toString();
    }

}
