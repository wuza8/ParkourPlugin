package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            ParkourPlugin.parkourSet.removeParkour(args[0]);
            sender.sendMessage("Parkour with name \""+args[0]+"\" removed!");
        }
        catch(IllegalStateException exception){
            sender.sendMessage(exception.getMessage());
        }
        return true;
    }
}
