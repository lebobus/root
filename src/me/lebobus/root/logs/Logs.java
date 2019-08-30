package me.lebobus.root.logs;

import me.lebobus.root.utils.Files;
import me.lebobus.root.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Logs {

    private Random rand = new Random();

    private int getRandomID(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    public void createLog(Player staff, OfflinePlayer victim, String type, String duration, String reason) {
        Files playerFile = new Files(Main.inst.getDataFolder(), victim.getName() + "_logs.yml");
        if (!playerFile.fileExists()) {
            playerFile.createFile();
            playerFile.loadFile();
            playerFile.saveFile();
        }

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String date = format.format(now);

        int ID = getRandomID(1000000, 2000000);

        playerFile.loadFile();

        playerFile.set("logs." + "ID-" + ID + ".staff", staff.getName());
        playerFile.set("logs." + "ID-" + ID + ".type", type);
        playerFile.set("logs." + "ID-" + ID + ".duration", duration);
        playerFile.set("logs." + "ID-" + ID + ".reason", reason);
        playerFile.set("logs." + "ID-" + ID + ".date", date);
        playerFile.saveFile();
    }

}
