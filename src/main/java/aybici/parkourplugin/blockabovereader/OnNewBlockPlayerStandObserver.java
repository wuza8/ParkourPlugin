package aybici.parkourplugin.blockabovereader;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerMoveEvent;

public interface OnNewBlockPlayerStandObserver {
    void playerStandOnNewBlock(Block block, PlayerMoveEvent event);
}
