package aybici.parkourplugin.parkours;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopList {

    private List<TopLine> topList = new ArrayList<>();

    public void addTopLine(Player player, long playerTime) {
        TopLine topLine = new TopLine(player, playerTime);
        topList.add(topLine);
    }

    public List<TopLine> getTopList() {
        return topList;
    }

    public boolean removeTopLine(TopLine topLine){
        return topList.remove(topLine);
    }

    public void clearTopList(){
        topList.clear();
    }
}
