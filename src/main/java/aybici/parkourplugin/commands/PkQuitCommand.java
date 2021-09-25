package aybici.parkourplugin.commands;

import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PkQuitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission(ParkourPlugin.permissionSet.pkQuitPermission)){
            player.sendMessage(ChatColor.RED + "Nie masz permisji, żeby wyjść z parkoura!");
            return true;
        }
        player.sendMessage("Opuszczono parkour");
        ParkourPlugin.parkourSessionSet.deleteParkourSession(player);
        return true;
    }
}
