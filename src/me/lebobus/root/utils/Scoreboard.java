package me.lebobus.root.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.lebobus.root.Main;
import me.lebobus.root.kitpvp.listeners.Killstreak;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Scoreboard implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Files pFile = new Files(Main.inst.getDataFolder(), event.getPlayer().getName() + ".yml");
        pFile.saveFile();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Player p = event.getPlayer();

        Files pFile = new Files(Main.inst.getDataFolder(), event.getPlayer().getName() + ".yml");

        ArrayList<String> list = (ArrayList<String>) pFile.getStringList("player." + p.getName() + ".kits");
        if (!pFile.fileExists()) {
            pFile.createFile();
            pFile.loadFile();
            pFile.set("player" + "." + p.getName() + "." + "kills", 0);
            pFile.set("player" + "." + p.getName() + "." + "deaths", 0);
            pFile.set("player" + "." + p.getName() + "." + "kdr", 0);
            pFile.set("player" + "." + p.getName() + "." + "killstreak", 0);
            pFile.set("player" + "." + p.getName() + "." + "bestkillstreak", 0);
            pFile.set("player" + "." + p.getName() + "." + "credits", 0);
            pFile.set("player" + "." + p.getName() + "." + "banned", false);
            pFile.set("player" + "." + p.getName() + "." + "muted", false);
            pFile.set("player" + "." + p.getName() + "." + "uuid", p.getUniqueId().toString());
            list.add("pvp");
            list.add("archer");
            pFile.set("player." + p.getName() + ".kits", list);
            pFile.saveFile();
            return;
        }

        ScoreHelper helper = ScoreHelper.createScore(player);

        pFile.loadFile();
        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");

        Killstreak.killstreak.put(player.getUniqueId(), 0);
        Integer bestks = pFile.getInt("player." + event.getPlayer().getName() + ".bestkillstreak");

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + Killstreak.killstreak.get(event.getPlayer().getUniqueId()));
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);
    }


    public static void addKill(Player player) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer newkills = kills + 1;

        pFile.set("player." + player.getName() + ".credits", credits);
        pFile.set("player." + player.getName() + ".kills", newkills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + newkills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + Killstreak.killstreak.get(player.getUniqueId()));
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void addDeath(Player player) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer newdeaths = deaths + 1;

        pFile.set("player." + player.getName() + ".credits", credits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", newdeaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + newdeaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + Killstreak.killstreak.get(player.getUniqueId()));
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void addCredits(Player player, Integer credits) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer creditss = pFile.getInt("player." + player.getName() + ".credits");
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer totalcredits = creditss + credits;
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(totalcredits);

        pFile.set("player." + player.getName() + ".credits", totalcredits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + Killstreak.killstreak.get(player.getUniqueId()));
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void takeCredits(Player player, Integer credits) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer creditss = pFile.getInt("player." + player.getName() + ".credits");
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer totalcredits = creditss - credits;
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(totalcredits);

        pFile.set("player." + player.getName() + ".credits", totalcredits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + Killstreak.killstreak.get(player.getUniqueId()));
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void addKillstreak(Player player) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer ks = Killstreak.killstreak.get(player.getUniqueId());
        Integer newks = ks + 1;


        Killstreak.killstreak.put(player.getUniqueId(), newks);
        pFile.set("player." + player.getName() + ".credits", credits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + newks);
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void setBestkillstreak(Player player, Integer bestks) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer ks = Killstreak.killstreak.get(player.getUniqueId());


        pFile.set("player." + player.getName() + ".credits", credits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + ks);
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    public static void refreshScoreboard(Player player) {
        Files pFile = new Files(Main.inst.getDataFolder(), player.getName() + ".yml");
        pFile.loadFile();

        ScoreHelper helper = ScoreHelper.createScore(player);

        Integer credits = pFile.getInt("player." + player.getName() + ".credits");
        String creditsformatted = NumberFormat.getIntegerInstance(Locale.US).format(credits);
        Integer kills = pFile.getInt("player." + player.getName() + ".kills");
        Integer deaths = pFile.getInt("player." + player.getName() + ".deaths");
        Integer bestks = pFile.getInt("player." + player.getPlayer().getName() + ".bestkillstreak");
        Integer ks = Killstreak.killstreak.get(player.getUniqueId());

        pFile.set("player." + player.getName() + ".credits", credits);
        pFile.set("player." + player.getName() + ".kills", kills);
        pFile.set("player." + player.getName() + ".deaths", deaths);
        //stats.set("player."+player.getName()+".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
        pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

        helper.setTitle("&b" + player.getName());
        helper.setSlot(5, "&8» &7Kills &a" + kills);
        helper.setSlot(4, "&8» &7Deaths &a" + deaths);
        helper.setSlot(3, "&8» &7Killstreak &a" + ks);
        helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
        helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);

        pFile.saveFile();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (ScoreHelper.hasScore(player)) {
            ScoreHelper.removeScore(player);
        }
    }


}
