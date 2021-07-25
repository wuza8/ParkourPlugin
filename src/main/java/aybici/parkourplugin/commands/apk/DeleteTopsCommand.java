package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.parkours.TopLine;
import aybici.parkourplugin.sessions.ParkourSession;
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
        List<TopLine> topList = session.getParkour().getTopListObject().getTopList();

        Player playerToRemove = ParkourPlugin.getInstance().getServer().getPlayer(args[0]);
        if (args.length == 1){
            if (playerToRemove != null){
                int times = ParkourPlugin.topListDisplay.getAllTimesOfPlayer(playerToRemove, topList).size();
                for (TopLine topLine : ParkourPlugin.topListDisplay.getAllTimesOfPlayer(playerToRemove, topList)){
                    session.getParkour().getTopListObject().removeTopLine(topLine);
                }
                player.sendMessage("Usunięte czasy gracza " + playerToRemove.getName() + ": " + times);
                return true;
            } else if (args[0].equals("all")) {
                int times = session.getParkour().getTopListObject().getTopList().size();
                session.getParkour().getTopListObject().clearTopList();
                player.sendMessage("Usunięto wszystkie czasy: " + times);
                return true;
            }
        } else if (args.length == 2 && args[1].equals("best")){
            if (playerToRemove != null){
                if (session.getParkour().getTopListObject().removeTopLine(ParkourPlugin.topListDisplay.getBestTimeOfPlayer(playerToRemove, topList))){
                    player.sendMessage("Usunięto najlepszy czas gracza " + playerToRemove.getName());
                    return true;
                }
                player.sendMessage("Brak czasów gracza " + playerToRemove.getName());
                return false;
            } else if (args[0].equals("all")) {
                if (session.getParkour().getTopListObject().removeTopLine(ParkourPlugin.topListDisplay.getBestTime(topList))){
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
