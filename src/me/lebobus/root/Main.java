package me.lebobus.root;

import me.lebobus.root.logs.Command;
import me.lebobus.root.utils.PluginsHider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.lebobus.root.ban.Ban;
import me.lebobus.root.ffa.Ffa;
import me.lebobus.root.ffa.FfaListener;
import me.lebobus.root.kick.Kick;
import me.lebobus.root.kitpvp.Shop;
import me.lebobus.root.kitpvp.Stats;
import me.lebobus.root.kitpvp.kits.Fireman;
import me.lebobus.root.kitpvp.kits.KitsListener;
import me.lebobus.root.kitpvp.kits.Vampire;
import me.lebobus.root.kitpvp.listeners.Killstreak;
import me.lebobus.root.kitpvp.listeners.KitsShopGUI;
import me.lebobus.root.kitpvp.listeners.Signs;
import me.lebobus.root.kts.Kts;
import me.lebobus.root.kts.KtsListener;
import me.lebobus.root.lobby.Menu;
import me.lebobus.root.lobby.MenuInv;
import me.lebobus.root.utils.Files;
import me.lebobus.root.utils.ScoreHelper;
import me.lebobus.root.utils.Scoreboard;

import java.text.NumberFormat;
import java.util.Locale;

public class Main extends JavaPlugin implements Listener {

    public static Main inst;

    private static Plugin plugin;

    public void onEnable() {

        registerEvents(this, this);
        registerEvents(this, new FfaListener(), new KtsListener(), new Menu(), new MenuInv(), new Signs(), new KitsListener(), new Scoreboard(), new Killstreak(), new KitsShopGUI(), new Vampire(), new Fireman(), new PluginsHider());
        getCommand("ffa").setExecutor(new Ffa());
        getCommand("ban").setExecutor(new Ban());
        getCommand("unban").setExecutor(new Ban());
        getCommand("kts").setExecutor(new Kts());
        getCommand("kick").setExecutor(new Kick());
        getCommand("stats").setExecutor(new Stats());
        getCommand("addcredits").setExecutor(new Stats());
        getCommand("takecredits").setExecutor(new Stats());
        getCommand("shop").setExecutor(new Shop());
        getCommand("logs").setExecutor(new Command());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        for (Player player : Bukkit.getOnlinePlayers()) {
            Killstreak.killstreak.put(player.getUniqueId(), 0);

            Files pFile = new Files(getDataFolder(), player.getName() + ".yml");
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
            pFile.set("player." + player.getName() + ".killstreak", Killstreak.killstreak.get(player.getUniqueId()));
            pFile.set("player." + player.getName() + ".bestkillstreak", bestks);

            helper.setTitle("&b" + player.getName());
            helper.setSlot(5, "&8» &7Kills &a" + kills);
            helper.setSlot(4, "&8» &7Deaths &a" + deaths);
            helper.setSlot(3, "&8» &7Killstreak &a" + ks);
            helper.setSlot(2, "&8» &7Best killstreak &a" + bestks);
            helper.setSlot(1, "&8» &7Credits &a" + creditsformatted);
        }


        inst = this;

        plugin = this;
    }

    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            Files pFile = new Files(getDataFolder(), player.getName() + ".yml");
            pFile.loadFile();
            pFile.saveFile();
        }
        plugin = null;
    }

    public static void registerEvents(Plugin plugin, Listener... listeners) {

        Listener[] arrayOfListener;
        int j = (arrayOfListener = listeners).length;
        for (int i = 0; i < j; i++) {
            Listener listener = arrayOfListener[i];
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}