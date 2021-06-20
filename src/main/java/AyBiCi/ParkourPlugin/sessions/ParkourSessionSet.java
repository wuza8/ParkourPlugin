package AyBiCi.ParkourPlugin.sessions;

import AyBiCi.ParkourPlugin.parkours.Parkour;
import AyBiCi.ParkourPlugin.parkours.ParkourSet;
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
        getSession(player).teleportTo(parkour);
    }

    public ParkourSession getSession(Player player) {
        if(!parkourSessions.containsKey(player.getUniqueId()))
            createParkourSession(player);

        return parkourSessions.get(player.getUniqueId());
    }

    private void createParkourSession(Player player){
        parkourSessions.put(player.getUniqueId(), new ParkourSession(player));
    }
}
