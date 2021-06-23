package AyBiCi.ParkourPlugin.sessions;

import org.bukkit.entity.Player;

public class PlayerTimer {
    private Player player;
    private long time;

    public PlayerTimer(Player player) {
        this.player = player;
    }

    public void startTimer() {
        time = System.currentTimeMillis();
    }

    public long actualTime() {
        return System.currentTimeMillis() - time;
    }

    public void resetTimer() {
        time = 0;
    }
}
