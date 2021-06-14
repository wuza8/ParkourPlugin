package AyBiCi.ParkourPlugin;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkourSessionSet {
    private Map<UUID, ParkourSession> parkourSessions = new HashMap<>();
    private ParkourSet parkourSet;
    public ParkourSessionSet(ParkourSet parkourSet) {
        this.parkourSet = parkourSet;
    }

    public void teleportToParkour(Player player, String name) {
        Parkour parkour = parkourSet.getParkour(name);
        if(parkour == null) throw new IllegalStateException("Parkour with name \""+name+"\" doesn't exist!");
        player.teleport(parkour.getLocation());
    }
}
