package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.TopLine;
import aybici.parkourplugin.parkours.TopList;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeleteTopsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
        Player player = (Player) sender;
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);

        if (!player.hasPermission(ParkourPlugin.permissionSet.apkPermission)) {
            player.sendMessage(ChatColor.RED + "Nie masz dostępu do komend admin-parkour!");
            return true;
        }

        List<TopLine> topList = session.getParkour().getTopListObject().getTopList();
        TopList topListObject = session.getParkour().getTopListObject();

        OfflinePlayer playerToRemove = ParkourPlugin.getInstance().getServer().getOfflinePlayer(args[0]);
        if (args.length == 1){
            if (playerToRemove != null){
                int times = ParkourPlugin.topListDisplay.getAllTimesOfPlayer(playerToRemove, topList).size();
                for (TopLine topLine : ParkourPlugin.topListDisplay.getAllTimesOfPlayer(playerToRemove, topList)){
                    topListObject.removeTopLine(topLine,session.getParkour());
                }
                topListObject.saveTopList();
                player.sendMessage("Usunięte czasy gracza " + playerToRemove.getName() + ": " + times);
                return true;
            } else if (args[0].equals("all")) {
                int times = topListObject.getTopList().size();
                topListObject.clearTopList(session.getParkour());
                topListObject.saveTopList();
                player.sendMessage("Usunięto wszystkie czasy: " + times);
                return true;
            }
        } else if (args.length == 2 && args[1].equals("best")){
            if (playerToRemove != null){
                if (topListObject.removeTopLine(ParkourPlugin.topListDisplay.getBestTimeOfPlayer(playerToRemove, topList),
                        session.getParkour())){
                    topListObject.saveTopList();
                    player.sendMessage("Usunięto najlepszy czas gracza " + playerToRemove.getName());
                    return true;
                }
                player.sendMessage("Brak czasów gracza " + playerToRemove.getName());
                return false;
            } else if (args[0].equals("all")) {
                if (topListObject.removeTopLine(ParkourPlugin.topListDisplay.getBestTime(topList),
                        session.getParkour())){
                    topListObject.saveTopList();
                    player.sendMessage("Usunięto najlepszy czas na mapie");
                    return true;
                }
                player.sendMessage("Brak czasów na tej mapie");
                return false;
            }
        }
        return false;
    }
}
