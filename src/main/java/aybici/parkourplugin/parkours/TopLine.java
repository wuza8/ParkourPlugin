package aybici.parkourplugin.parkours;

import aybici.parkourplugin.DateAndTime;
import aybici.parkourplugin.FileCreator;
import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;


public class TopLine implements Cloneable {
    TopLine(Player player, long playerTime, int startPing) {
        this.date = System.currentTimeMillis();
        this.player = player;
        this.playerTime = playerTime;
        this.startPing = startPing;
        this.endPing = player.getPing();
    }
    TopLine(){
        this.date = 0;
        this.player = null;
        this.playerTime = Long.MAX_VALUE;
        this.startPing = 0;
        this.endPing = 0;
    }
    public OfflinePlayer player;
    long date;
    long playerTime;
    int startPing;
    int endPing;

    public void saveTopLineString(String directory) {
        if (!new File(directory).exists()) FileCreator.createFile(directory);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory, true));
            writer.write(ParkourPlugin.uuidList.getShortIdentifier(player.getUniqueId()) + ","
                    +date+"," +playerTime+","+startPing+","+endPing+ "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean isLagged(){
        return (startPing > 180 || endPing > 180);
    }
    public String toScoreboardDisplay(){
        String timeToString = ParkourPlugin.topListDisplay.timeToString(playerTime);
        return ChatColor.AQUA + timeToString + ChatColor.DARK_GREEN + ", " + ChatColor.WHITE + player.getName();
    }

    @Override
    public String toString(){
        String color;
        if (isLagged()) color = "RED";
        else color = "WHITE";
        String timeToString = ParkourPlugin.topListDisplay.timeToString(playerTime);
        return player.getName() + ", " +ChatColor.DARK_GREEN+ DateAndTime.getDateString(date)+
                ChatColor.WHITE + ", " + ChatColor.valueOf(color) + timeToString;
    }
}