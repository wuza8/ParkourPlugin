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
