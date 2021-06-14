package AyBiCi.ParkourPlugin;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class ParkourSet {
    private Set<Parkour> parkours = new HashSet<>();


    public void addParkour(String name, Location location) throws IllegalStateException{
        if(getParkour(name) != null)
            throw new IllegalStateException("Parkour with name \""+name+"\" already exists!");
        if(Character.isDigit(name.toCharArray()[0]))
            throw new IllegalStateException("Parkour name can't start from a digit!");

        parkours.add(new Parkour(name, location));
    }

    public Parkour getParkour(String name) {
        for(Parkour parkour : parkours){
            if(parkour.getName().equals(name)) return parkour;
        }
        return null;
    }
}
