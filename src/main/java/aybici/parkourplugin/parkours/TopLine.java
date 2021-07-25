package aybici.parkourplugin.parkours;

import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.entity.Player;

public class TopLine {
    TopLine(Player player, long playerTime) {
        this.date = System.currentTimeMillis();
        this.player = player;
        this.playerTime = playerTime;
    }
    public Player player;
    long date;
    long playerTime;

    @Override
    public String toString(){
        String timeToString = ParkourPlugin.topListDisplay.timeToString(playerTime);
        return player.getName() + ", " + ParkourPlugin.dateAndTime.getDateString(date) + ", " + timeToString;
    }
}