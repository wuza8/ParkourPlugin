package aybici.parkourplugin.parkours;

import aybici.parkourplugin.parkours.hibernate.BackBlockSet;
import aybici.parkourplugin.parkours.hibernate.EmbedableLocation;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.persistence.*;

@Entity
@Table(name="ParkourPlugin_Parkours")
public class Parkour {
    @Id
    private final String name;
    private TopList topList = new TopList();

    // New wrapper classes are used for Hibernate (EmbedableLocation, BackBlockSet)
    private EmbedableLocation embedableLocation = new EmbedableLocation();
    private BackBlockSet backBlocks = new BackBlockSet();

    Parkour(String name, Location location){
        this.name = name;
        setLocation(location);
    }

    public TopList getTopListObject(){
        return topList;
    }

    public Location getLocation(){
        return embedableLocation.getLocation();
    }

    public void setLocation(Location location){
        embedableLocation.setLocation(location);
    }

    public String getName() {
        return name;
    }

    public Material addBackBlock(String materialName){
        Material material = getMaterial(materialName);

        if(material == Material.AIR)
            throw new IllegalStateException("AIR can't be a backblock!");
        else if(hasBackBlock(material))
            throw new IllegalStateException(material.name() + " is already a backblock!");

        addBackBlock(material);
        return material;
    }

    public void addBackBlock(Material material) {
        backBlocks.add(material);
    }

    public Material removeBackBlock(String materialName){
        Material material = getMaterial(materialName);

        if(!hasBackBlock(material))
            throw new IllegalStateException(material.name() + " is not a backblock!");

        removeBackBlock(material);
        return material;
    }

    public void removeBackBlock(Material material) {
        backBlocks.remove(material);
    }

    public boolean hasBackBlock(Material material) {
        return backBlocks.contains(material);
    }

    private static Material getMaterial(String materialName){
        Material material = Material.matchMaterial(materialName);

        if(material == null)
            throw new IllegalStateException("\"" + materialName + "\" is not a material!");

        return material;
    }
}
