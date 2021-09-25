package aybici.parkourplugin.sessions;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.blockabovereader.OnNewBlockPlayerStandObserver;
import aybici.parkourplugin.events.PlayerEndsParkourEvent;
import aybici.parkourplugin.events.PlayerStartsParkourEvent;
import aybici.parkourplugin.parkours.DisplayingTimesState;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.SortTimesType;
import aybici.parkourplugin.parkours.fails.Fail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParkourSession implements OnNewBlockPlayerStandObserver {
    private final Player player;
    private Parkour parkourPlayerOn;
    private PlayerGameplayState playerGameplayState = PlayerGameplayState.ON_PARKOUR;
    private final PlayerTimer playerTimer;
    private int startPing;

    public PlayerTimer getPlayerTimer() {
        return playerTimer;
    }

    public ParkourSession(Player player){
        this.player = player;
        ParkourPlugin.underPlayerBlockWatcher.registerNewObserver(player, this);
        playerTimer = new PlayerTimer(player);
    }

    public int getStartPing(){
        return startPing;
    }
    public Parkour getParkour() {
        return parkourPlayerOn;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void teleportTo(Parkour parkour){
        parkourPlayerOn = parkour;
        player.teleport(parkour.getLocation());
        playerGameplayState = PlayerGameplayState.ON_PARKOUR;
        playerTimer.resetTimer();
        player.setGameMode(GameMode.ADVENTURE);
    }

    public boolean isPlayerOnParkour() {
        return parkourPlayerOn != null;
    }

    public void onPlayerStandOnGreenWool() throws InterruptedException {
        if(!playerGameplayState.equals(PlayerGameplayState.ON_PARKOUR)) return;

        PlayerStartsParkourEvent event = new PlayerStartsParkourEvent(player, parkourPlayerOn);
        Bukkit.getServer().getPluginManager().callEvent(event);
        player.sendMessage(ChatColor.AQUA + "Rozpoczęto parkour " + parkourPlayerOn.getName().replaceAll("_", " "));

        if(!event.isCancelled()) {
            playerGameplayState = PlayerGameplayState.PARKOURING;
            playerTimer.startTimer();
            startPing = player.getPing();
        }
    }

    public void onPlayerStandOnRedWool(){
        if(!playerGameplayState.equals(PlayerGameplayState.PARKOURING)) return;

        long playerTime = playerTimer.actualTime();

        PlayerEndsParkourEvent event = new PlayerEndsParkourEvent(player, parkourPlayerOn, playerTime);
        Bukkit.getServer().getPluginManager().callEvent(event);
        player.sendMessage(ChatColor.GREEN + "Zakonczono parkour");

        if(!event.isCancelled()) {
            playerTimer.resetTimer();
            player.sendMessage(ChatColor.GREEN + "Your time: " + ParkourPlugin.topListDisplay.timeToString(playerTime));
            parkourPlayerOn.getTopListObject().addTopLine(player, playerTime, startPing);
            teleportTo(parkourPlayerOn);
            ParkourPlugin.topListDisplay.displayTimesOnScoreboard(player, DisplayingTimesState.ALL_PLAYERS_BEST_TIMES, SortTimesType.TIME);
            ParkourPlugin.topListDisplay.displayScoreboardToOtherPlayers(parkourPlayerOn, DisplayingTimesState.ALL_PLAYERS_BEST_TIMES, SortTimesType.TIME);
        }
    }

    @Override
    public void playerStandOnNewBlock(List<Block> blockList) {
        if (parkourPlayerOn == null) return;
        List<Material> materialList = new ArrayList<>();
        for (Block block : blockList){
            if (!materialList.contains(block.getType()))
            materialList.add(block.getType());
        }

        if(materialList.contains(Material.LIME_WOOL)) {
            try {
                onPlayerStandOnGreenWool();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(materialList.contains(Material.RED_WOOL))
            onPlayerStandOnRedWool();
        else if(parkourPlayerOn.hasAnyBackBlock(materialList) || player.getLocation().getY() < 0) {
            try {
                new Fail(player.getUniqueId(), System.currentTimeMillis(), player.getLocation()).saveFail(parkourPlayerOn.folderName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            teleportTo(parkourPlayerOn);
            playerTimer.resetTimer();
        }
    }
}
