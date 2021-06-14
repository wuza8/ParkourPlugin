package AyBiCi.ParkourPlugin.commands;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        try {
            ParkourPlugin.parkourSessionSet.teleportToParkour(player, args[0]);
        }
        catch(IllegalStateException exception){
            player.sendMessage(exception.getMessage());
        }
        return true;
    }
}
