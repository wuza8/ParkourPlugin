package AyBiCi.ParkourPlugin.sessions;

import AyBiCi.ParkourPlugin.parkours.Parkour;
import org.bukkit.entity.Player;

public class ParkourSession {
    private Player player;
    private Parkour parkourPlayerOn;

    public ParkourSession(Player player){
        this.player = player;
    }

    public Parkour getParkour() {
        return parkourPlayerOn;
    }

    public void teleportTo(Parkour parkour){
        parkourPlayerOn = parkour;
        player.teleport(parkour.getLocation());
    }

    public boolean isPlayerOnParkour() {
        return parkourPlayerOn != null;
    }
}