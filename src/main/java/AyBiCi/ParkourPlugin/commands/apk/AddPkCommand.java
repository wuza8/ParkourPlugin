package AyBiCi.ParkourPlugin.commands.apk;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        try {
            ParkourPlugin.parkourSet.addParkour(args[0], player.getLocation());
            player.sendMessage("Parkour with name \""+args[0]+"\" added!");
        }
        catch(Exception exception){
            player.sendMessage(exception.getMessage());
        }

        return true;
    }
}
