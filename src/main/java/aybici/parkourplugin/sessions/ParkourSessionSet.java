package aybici.parkourplugin.sessions;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.DisplayingTimesState;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.ParkourSet;
import aybici.parkourplugin.parkours.SortTimesType;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkourSessionSet {
    private final Map<UUID, ParkourSession> parkourSessions = new HashMap<>();
    private final ParkourSet parkourSet;

    public ParkourSessionSet(ParkourSet parkourSet) {
        this.parkourSet = parkourSet;
    }

    public void teleportToParkour(Player player, String name) {
        Parkour parkour = parkourSet.getParkour(name);
        if (!ParkourPlugin.parkourSet.playerHasAccessToParkour(player, parkour, true)) return;
        getSession(player).teleportTo(parkour);

        player.sendMessage(ChatColor.GOLD + "Dołączono do parkoura " + ChatColor.DARK_GREEN +
                parkour.getName().replaceAll("_"," ") + ChatColor.GOLD + " w kategorii " + ChatColor.GRAY
                + parkour.getCategory().toString().toLowerCase() + ChatColor.GOLD + " o ID = " + ChatColor.GRAY
                + parkour.getIdentifier());
        player.sendMessage(ChatColor.BLUE + parkour.getDescription().replaceAll("#", "\n"));
        ParkourPlugin.topListDisplay.displayTimesOnScoreboard(player, DisplayingTimesState.ALL_PLAYERS_BEST_TIMES, SortTimesType.TIME);
        player.playNote(player.getLocation(), Instrument.BANJO, Note.flat(1, Note.Tone.A));
    }

    public ParkourSession getSession(Player player) {
        if(!parkourSessions.containsKey(player.getUniqueId()))
            createParkourSession(player);

        return parkourSessions.get(player.getUniqueId());
    }

    private void createParkourSession(Player player){
        parkourSessions.put(player.getUniqueId(), new ParkourSession(player));
    }
    public void deleteParkourSession(Player player){
        ParkourPlugin.parkourSessionSet.getSession(player).getPlayerTimer().resetTimer();
        parkourSessions.remove(player.getUniqueId());
        ParkourPlugin.underPlayerBlockWatcher.underPlayerBlockObservers.get(player.getUniqueId()).clear();
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
}
