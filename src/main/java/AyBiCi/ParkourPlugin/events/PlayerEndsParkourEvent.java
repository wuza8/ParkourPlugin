package AyBiCi.ParkourPlugin.events;

import AyBiCi.ParkourPlugin.parkours.Parkour;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEndsParkourEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean canceled = false;

    private Player player;
    private Parkour parkour;
    private long timeInMillis;

    public PlayerEndsParkourEvent(Player player, Parkour parkourPlayerOn, long playerTime) {
        this.player = player;
        this.parkour = parkourPlayerOn;
        this.timeInMillis = playerTime;
    }

    public Player getPlayer() {
        return player;
    }

    public Parkour getParkour() {
        return parkour;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean is) {
        canceled = is;
    }
}
