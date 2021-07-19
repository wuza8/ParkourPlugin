package aybici.parkourplugin.parkours;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopList {
    private class TopLine {
        TopLine(Player player, long playerTime) {
            this.date = System.currentTimeMillis();
            this.player = player;
            this.playerTime = playerTime;
        }
        Player player;
        long date;
        long playerTime;
        @Override
        public String toString(){
            return player.getName() + ", " + date + ", " + playerTime;
        }
    }

    private List<TopLine> topList = new ArrayList<>();

    public void addTopLine(Player player, long playerTime) {
        TopLine topLine = new TopLine(player, playerTime);
        topList.add(topLine);
    }

    public TopLine getBestTimeEver(){
        TopLine bestTimeLine = new TopLine(null, 9_223_372_036_854_775_807L);
        for (TopLine topLine : topList){
            if(topLine.playerTime < bestTimeLine.playerTime) bestTimeLine = topLine;
        }
        if(bestTimeLine.playerTime < 9_223_372_036_854_775_807L){
            return bestTimeLine;
        }
        System.out.println("Nobody finished the parkour.");
        return null;
    }

    public List<TopLine> getAllTimesOfPlayer(Player player){
        List<TopLine> allLinesOfPlayer = new ArrayList<>();
        for (TopLine topLine : topList){
            if (topLine.player.equals(player)) allLinesOfPlayer.add(topLine);
        }
        return allLinesOfPlayer;
    }

    public TopLine getBestTimeOfPlayer(Player playerName){
        TopLine bestTimeLine = new TopLine(null, 9_223_372_036_854_775_807L );
        for (TopLine topLine : getAllTimesOfPlayer(playerName)){
            if(topLine.playerTime < bestTimeLine.playerTime) bestTimeLine = topLine;
        }
        if(bestTimeLine.playerTime < 9_223_372_036_854_775_807L){
            return bestTimeLine;
        }
        System.out.println("Player " + playerName + " did not yet finish the parkour.");
        return null;
    }

    public boolean removeTopLine(TopLine topLine){
        if (topList.remove(topLine)) {
            System.out.println(topLine.toString() + " deleted");
            return true;
        }
        System.out.println(topLine.toString() + " nie jest w topliscie, lub cos poszlo nie tak");
        return false;
    }
    public void printTopList(Player sender){
        for (TopLine topLine : topList){
            sender.sendMessage(topLine.toString());
        }
    }

    public void clearTopList(){
        for (TopLine topLine : topList){
            topList.remove(topLine);
        }
    }
}
