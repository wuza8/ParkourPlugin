package aybici.parkourplugin.services;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.sessions.ParkourSession;
import aybici.parkourplugin.sessions.PlayerGameplayState;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class StopPlayerParkourOnGamemodeChange implements Listener {

    @EventHandler
    public void onAnyPlayerChangeGamemode(PlayerGameModeChangeEvent event){
        if(event.getNewGameMode() != GameMode.CREATIVE) return;

        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(event.getPlayer());
        if(session.getPlayerGameplayState() == PlayerGameplayState.PARKOURING) {
            session.stopParkour();
            session.getPlayer().sendMessage("Parkour stopped!");
        }
    }
}
