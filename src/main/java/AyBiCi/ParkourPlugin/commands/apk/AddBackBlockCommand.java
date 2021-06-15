package AyBiCi.ParkourPlugin.commands.apk;

import AyBiCi.ParkourPlugin.Parkour;
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
            Material material = Material.matchMaterial(materialName);

            if(material == null){
                player.sendMessage("\"" + materialName + "\" is not a material!");
                continue;
            }
            else if(material == Material.AIR){
                player.sendMessage("AIR can't be a backblock!");
                continue;
            }

            boolean wasAdded = playerParkour.addBackBlock(material);
            if(wasAdded){
                player.sendMessage("Added backblock "+material.name());
                continue;
            }
            else{
                player.sendMessage(material.name() + " is already a backblock!");
                continue;
            }
        }

        return true;
    }
}
