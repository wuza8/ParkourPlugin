package AyBiCi.ParkourPlugin.sessions;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import AyBiCi.ParkourPlugin.blockabovereader.OnNewBlockPlayerStandObserver;
import AyBiCi.ParkourPlugin.events.PlayerEndsParkourEvent;
import AyBiCi.ParkourPlugin.events.PlayerStartsParkourEvent;
import AyBiCi.ParkourPlugin.parkours.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ParkourSession implements OnNewBlockPlayerStandObserver {
    private Player player;
    private Parkour parkourPlayerOn;
    private PlayerGameplayState playerGameplayState = PlayerGameplayState.ON_PARKOUR;
    private PlayerTimer playerTimer = new PlayerTimer(player);

    public ParkourSession(Player player){
        this.player = player;
        ParkourPlugin.underPlayerBlockWatcher.registerNewObserver(player, this);
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
            playerGameplayState = PlayerGameplayState.ON_PARKOUR;
            playerTimer.resetTimer();
            player.sendMessage("Your time: "+playerTime/1000+":"+playerTime%1000);
        }
    }

    @Override
    public void playerStandOnNewBlock(Block block) {
        Material material = block.getType();

        if(material.equals(Material.LIME_WOOL))
            onPlayerStandOnGreenWool();
        else if(material.equals(Material.RED_WOOL))
            onPlayerStandOnRedWool();
    }
}
