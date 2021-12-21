package aybici.parkourplugin.sessions;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.blockabovereader.OnNewBlockPlayerStandObserver;
import aybici.parkourplugin.events.PlayerEndsParkourEvent;
import aybici.parkourplugin.events.PlayerStartsParkourEvent;
import aybici.parkourplugin.parkours.DisplayingTimesState;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.SortTimesType;
import aybici.parkourplugin.parkours.fails.Fail;
import org.bukkit.*;
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
    public Checkpoint checkpoint = new Checkpoint();
    public StaticCheckpoint staticCheckpoint;
    public boolean playerWasGivenAttention = false;

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
    public PlayerGameplayState getPlayerGameplayState(){
        return playerGameplayState;
    }

    public void teleportTo(Parkour parkour){
        ParkourPlugin.positionSaver.setPlayerWatching(player,false);
        parkourPlayerOn = parkour;
        player.teleport(parkour.getLocation());
        playerGameplayState = PlayerGameplayState.ON_PARKOUR;
        playerTimer.resetTimer();
        player.setGameMode(GameMode.ADVENTURE);
        checkpoint.reset();
        if (staticCheckpoint != null)
            staticCheckpoint.cancelSession();
        playerWasGivenAttention = false;
        staticCheckpoint = new StaticCheckpoint(this);
        staticCheckpoint.runSession();
        ParkourPlugin.positionSaver.stop(player);
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
            ParkourPlugin.positionSaver.start(player);
        }
    }

    public void onPlayerStandOnRedWool(){
        if(!playerGameplayState.equals(PlayerGameplayState.PARKOURING)) return;
        if(checkpoint.isPlaced()) return;
        if(!staticCheckpoint.playerEnteredAllCheckpoints()){
            if (!playerWasGivenAttention) {
                player.sendMessage(ChatColor.RED + "Pominięto checkpointy!");
                playerWasGivenAttention = true;
            }
            return;
        }


        long playerTime = playerTimer.actualTime();

        PlayerEndsParkourEvent event = new PlayerEndsParkourEvent(player, parkourPlayerOn, playerTime);
        Bukkit.getServer().getPluginManager().callEvent(event);
        player.sendMessage(ChatColor.GREEN + "Zakonczono parkour");

        if(!event.isCancelled()) {
            playerTimer.resetTimer();
            ParkourPlugin.positionSaver.stop(player);
            player.sendMessage(ChatColor.GREEN + "Your time: " + ParkourPlugin.topListDisplay.timeToString(playerTime));

            teleportTo(parkourPlayerOn);

            if(startPing <= 180 && player.getPing() <= 180) {
                if (ParkourPlugin.topListDisplay.getBestTimeOfPlayer(player,parkourPlayerOn.getTopListObject().getTopList()) != null) {
                    if (playerTime < ParkourPlugin.topListDisplay.getBestTimeOfPlayer(player,parkourPlayerOn.getTopListObject().getTopList()).playerTime)
                        ParkourPlugin.positionSaver.saveToFile(player, parkourPlayerOn.folderName);
                } else ParkourPlugin.positionSaver.saveToFile(player, parkourPlayerOn.folderName);
            }
            parkourPlayerOn.getTopListObject().addTopLine(player, playerTime, startPing);
            ParkourPlugin.topListDisplay.displayTimesOnScoreboard(player, DisplayingTimesState.ALL_PLAYERS_BEST_TIMES, SortTimesType.TIME);
            ParkourPlugin.topListDisplay.displayScoreboardToOtherPlayers(parkourPlayerOn, DisplayingTimesState.ALL_PLAYERS_BEST_TIMES, SortTimesType.TIME);
        }
    }
    public void onPlayerFails(){
        try {
            new Fail(player.getUniqueId(), System.currentTimeMillis(), player.getLocation()).saveFail(parkourPlayerOn.folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (checkpoint.isPlaced()){
            staticCheckpoint.resetReachedCheckpoints();
            ParkourPlugin.positionSaver.stop(player);
            player.teleport(checkpoint.getLocation());
        } else if (staticCheckpoint.placedCheckpointNumber == -1){
            teleportTo(parkourPlayerOn);
        } else player.teleport(staticCheckpoint.getCurrentCheckpointLocation());
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
            onPlayerFails();
        }
    }
    public class Checkpoint{
        private boolean placed = false;
        private Location location;

        public boolean isPlaced(){
            return placed;
        }
        public void setCheckpoint(){
            if (playerGameplayState.equals(PlayerGameplayState.PARKOURING)) {
                this.location = player.getLocation();
                placed = true;
                playerTimer.resetTimer();
                player.sendMessage(ChatColor.GRAY + "Ustawiono checkpoint");
            } else player.sendMessage(ChatColor.GRAY + "Musisz rozpocząć parkour!");
        }

        public Location getLocation() {
            return location;
        }
        public void reset(){
            placed = false;
        }
    }
}
