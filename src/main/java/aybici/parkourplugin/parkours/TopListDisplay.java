package aybici.parkourplugin.parkours;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopListDisplay {

    public TopLine getBestTime(List<TopLine> topList){
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

    public List<TopLine> getAllTimesOfPlayer(Player player, List<TopLine> topList){
        List<TopLine> allLinesOfPlayer = new ArrayList<>();
        for (TopLine topLine : topList){
            if (topLine.player.equals(player)) allLinesOfPlayer.add(topLine);
        }
        return allLinesOfPlayer;
    }

    public TopLine getBestTimeOfPlayer(Player player, List<TopLine> topList){
        TopLine bestTimeLine = new TopLine(null, 9_223_372_036_854_775_807L );
        for (TopLine topLine : getAllTimesOfPlayer(player, topList)){
            if(topLine.playerTime < bestTimeLine.playerTime) bestTimeLine = topLine;
        }
        if(bestTimeLine.playerTime < 9_223_372_036_854_775_807L){
            return bestTimeLine;
        }
        System.out.println("Player " + player + " did not yet finish the parkour.");
        return null;
    }

    public List<Player> getAllPlayersOfTop(List<TopLine> topList){
        List<Player> players = new ArrayList<>();
        for (TopLine topLine : topList){
            if (!players.contains(topLine.player))
                players.add(topLine.player);
        }
        return players;
    }

    public String timeToString(long playerTime){
        int hours, minutes, seconds, millis;

        millis = (int)playerTime%1000;
        seconds = ((int)playerTime/1000)%60;
        minutes = ((int)playerTime/60_000)%60;
        hours = (int)playerTime/3_600_000;

        String mStr = "", hStr = "", zerosBeforeMillis = "",
                zeroBeforeSeconds = "", zeroBeforeMinutes = "",
                separateMinutesSeconds = "", separateHoursMinutes = "";

        if (millis/10 == 0) zerosBeforeMillis = "00";
        else if (millis/100 == 0) zerosBeforeMillis = "0";

        if (seconds/10 == 0 && minutes != 0) zeroBeforeSeconds = "0";
        if (minutes/10 == 0 && hours != 0) zeroBeforeMinutes = "0";

        if (minutes != 0 || hours != 0) {
            separateMinutesSeconds = ":";
            mStr = minutes + "";
        }
        if (hours != 0) {
            separateHoursMinutes = ":";
            hStr = hours + "";
        }

        return hStr + separateHoursMinutes + zeroBeforeMinutes + mStr
                + separateMinutesSeconds + zeroBeforeSeconds + seconds + ":" + zerosBeforeMillis + millis;
    }

    public boolean printSortedTopList(Player player, List<TopLine> sortedTopList, int pageNumber){
        final int LINES_PER_PAGE = 5;
        int numberOfPages = sortedTopList.toArray().length/LINES_PER_PAGE;
        int lastPageLength = sortedTopList.toArray().length % LINES_PER_PAGE;
        if (lastPageLength != 0) numberOfPages++;
        if (pageNumber > numberOfPages) {
            player.sendMessage("Maksymalna strona to " + numberOfPages + "!");
            return false;
        }
        player.sendMessage("strona nr " + pageNumber + " z " + numberOfPages);

        int startIndex = LINES_PER_PAGE*(pageNumber - 1);
        if (pageNumber != numberOfPages || lastPageLength == 0)
            for (int index = startIndex; index < startIndex + LINES_PER_PAGE; index++)
                player.sendMessage(sortedTopList.get(index).toString());
        else
            for (int index = startIndex; index < startIndex + lastPageLength; index++)
                player.sendMessage(sortedTopList.get(index).toString());
        return true;
    }

    public boolean sortAndPrintTopList(Player player, List<TopLine> topList, DisplayingTimesState displayingTimesState, SortTimesType sortTimesType, int page){
        List<TopLine> topLinesToSort = new ArrayList<>();
        switch (displayingTimesState){
            case ALL_PLAYERS_ALL_TIMES:
                topLinesToSort = topList;
                player.sendMessage("Oto wszystkie czasy na tej mapie");
                break;
            case ONE_PLAYER_ALL_TIMES:
                player.sendMessage("Oto Twoje czasy na tej mapie");
                topLinesToSort = getAllTimesOfPlayer(player, topList);
                break;
            case ALL_PLAYERS_BEST_TIMES:
                player.sendMessage("Oto najlepsze czasy wszystkich graczy na tej mapie");
                for (Player playerLocal : getAllPlayersOfTop(topList)) {
                    topLinesToSort.add(getBestTimeOfPlayer(playerLocal, topList));
                }
                break;
        }
        List<TopLine> topLinesToDisplay = new ArrayList<>();
        switch (sortTimesType){
            case DATE:
                player.sendMessage("posortowane wg daty:");
                topLinesToDisplay.addAll(topLinesToSort);
                break;
            case TIME:
                player.sendMessage("posortowane wg czasu:");
                List<TopLine> topLinesCopy = new ArrayList<>(topLinesToSort);
                for (int i = 0; i < topLinesToSort.size(); i++){
                    TopLine bestTime = getBestTime(topLinesCopy);
                    topLinesToDisplay.add(bestTime);
                    topLinesCopy.remove(bestTime);
                }
                break;
            case PLAYERS:
                player.sendMessage("posortowane alfabetycznie wg graczy:");
                List<String> playerNames = new ArrayList<>();

                for (Player playerLocal : getAllPlayersOfTop(topLinesToSort)){
                    playerNames.add(playerLocal.getName());
                }
                java.util.Collections.sort(playerNames);
                for (String playerName : playerNames){
                    for (TopLine topLine : topLinesToSort){
                        if (topLine.player.getName().equals(playerName))
                            topLinesToDisplay.add(topLine);
                    }
                }
                break;
        }
        return printSortedTopList(player, topLinesToDisplay, page);
    }
}
