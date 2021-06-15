package AyBiCi.ParkourPlugin;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class Parkour {
    private String name;
    private Location location;
    private Set<Material> backBlocks = new HashSet<>();

    Parkour(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public Location getLocation(){
        return location.clone();
    }

    public String getName() {
        return name;
    }

    public boolean addBackBlock(Material material) {
        if(!backBlocks.contains(material)){
            backBlocks.add(material);
            return true;
        }
        else return false;
    }
}
