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
        player.teleport(parkourSet.getParkour(name).getLocation());
    }
}
