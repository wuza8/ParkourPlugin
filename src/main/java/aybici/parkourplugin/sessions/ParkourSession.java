package aybici.parkourplugin.sessions;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.blockabovereader.OnNewBlockPlayerStandObserver;
import aybici.parkourplugin.events.PlayerEndsParkourEvent;
import aybici.parkourplugin.events.PlayerStartsParkourEvent;
import aybici.parkourplugin.parkours.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ParkourSession implements OnNewBlockPlayerStandObserver {
    private final Player player;
    private Parkour parkourPlayerOn;
    private PlayerGameplayState playerGameplayState = PlayerGameplayState.ON_PARKOUR;
    private final PlayerTimer playerTimer;

    public ParkourSession(Player player){
        this.player = player;
        ParkourPlugin.underPlayerBlockWatcher.registerNewObserver(player, this);
        playerTimer = new PlayerTimer();
    }

    public Parkour getParkour() {
        return parkourPlayerOn;
    }

    public void teleportTo(Parkour parkour){
        parkourPlayerOn = parkour;
        player.teleport(parkour.getLocation());
        playerGameplayState = PlayerGameplayState.ON_PARKOUR;
    }

    public boolean isPlayerOnParkour() {
        return parkourPlayerOn != null;
    }

    public void onPlayerStandOnGreenWool(){
        if(!playerGameplayState.equals(PlayerGameplayState.ON_PARKOUR)) return;

        PlayerStartsParkourEvent event = new PlayerStartsParkourEvent(player, parkourPlayerOn);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(!event.isCancelled()) {
            playerGameplayState = PlayerGameplayState.PARKOURING;
            playerTimer.startTimer();
        }
    }

    public void onPlayerStandOnRedWool(){
        if(!playerGameplayState.equals(PlayerGameplayState.PARKOURING)) return;

        long playerTime = playerTimer.actualTime();

        PlayerEndsParkourEvent event = new PlayerEndsParkourEvent(player, parkourPlayerOn, playerTime);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if(!event.isCancelled()) {
            playerTimer.resetTimer();
            player.sendMessage("Your time: "+playerTime/1000+":"+playerTime%1000);
            teleportTo(parkourPlayerOn);
        }
    }

    @Override
    public void playerStandOnNewBlock(Block block) {
        Material material = block.getType();

        if(material.equals(Material.LIME_WOOL))
            onPlayerStandOnGreenWool();
        else if(material.equals(Material.RED_WOOL))
            onPlayerStandOnRedWool();
        else if(parkourPlayerOn.hasBackBlock(material))
            teleportTo(parkourPlayerOn);
    }
}
