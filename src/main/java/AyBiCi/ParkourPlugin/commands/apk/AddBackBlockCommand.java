package AyBiCi.ParkourPlugin.commands.apk;

import AyBiCi.ParkourPlugin.parkours.Parkour;
import AyBiCi.ParkourPlugin.ParkourPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddBackBlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        Parkour playerParkour = ParkourPlugin.parkourSessionSet.getSession(player).getParkour();

        if(playerParkour == null) {
            sender.sendMessage("You need to join parkour to use this command!");
            return false;
        }

        for(String materialName : args){
            try {
                Material material = playerParkour.addBackBlock(materialName);
                player.sendMessage("Added backblock "+material.name());
            }
            catch(IllegalStateException exception){
                player.sendMessage(exception.getMessage());
            }
        }

        return true;
    }
}
