package aybici.parkourplugin.parkours;

import aybici.parkourplugin.FileCreator;
import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class ParkourSet {
    private final Set<Parkour> parkours = new HashSet<>();
    public String parkoursFolder = "dataBase/parkours";

    public void addParkour(String name, Location location) throws IllegalStateException{
        if(doesExist(name))
            throw new IllegalStateException("Parkour with name \""+name+"\" already exists!");
        if(Character.isDigit(name.toCharArray()[0]))
            throw new IllegalStateException("Parkour name can't start from a digit!");
        Parkour parkour = new Parkour(name, location);
        parkours.add(parkour);

        FileCreator.createFile(parkour.folderName + parkour.dataFileNameInsideFolder);
        parkour.saveParkour(parkour.folderName + parkour.dataFileNameInsideFolder);
    }

    public Set<Parkour> getParkours(){
        return parkours;
    }
    public Parkour getParkour(String name) {
        for(Parkour parkour : parkours){
            if(parkour.getName().equalsIgnoreCase(name)) return parkour;
        }
        throw new IllegalStateException("Parkour with name \""+name+"\" doesn't exist!");
    }

    public boolean doesExist(String name) {
        try{
            getParkour(name);
            return true;
        }
        catch(IllegalStateException exception)
        {
            return false;
        }
    }

    public boolean removeParkour(String name) {
        parkours.remove( getParkour(name) );
        File parkourFolder = new File(parkoursFolder + "/parkourMap_" + name);
        for (File file : parkourFolder.listFiles()){
            file.delete();
        }
        return parkourFolder.delete();
    }

    public void loadParkours(String directory){
        if (!new File(parkoursFolder).exists()) return;
        List<String> parkourNames = new ArrayList<>(getMapNamesFromDirectory(directory));
        System.out.println("OTO TWOJE PARKOURY: " + parkourNames.toString());
        for (String name : parkourNames){
            Parkour parkour = new Parkour(name);
            parkour.loadParkour(directory + "/parkourMap_" + parkour.getName());
            parkours.add(parkour);
        }
    }

    private List<String> getMapNamesFromDirectory(String directory){
        File folder = new File(directory);
        File[] files = folder.listFiles();
        List<String> parkourNames = new ArrayList<>();
        for (File file : files){
            if (file.getName().startsWith("parkourMap_")){
                parkourNames.add(file.getName().substring(11));
            }
        }
        return parkourNames;
    }

    public List<Parkour> getAllMapsOfCategory(ParkourCategory category){
        List<Parkour> parkourList = new ArrayList<>();
        for(Parkour parkour : parkours){
            if (parkour.getCategory().equals(category)) parkourList.add(parkour);
        }
        return parkourList;
    }
    public Parkour getParkourByCategoryAndID(ParkourCategory category, int id){
        for (Parkour parkour : getAllMapsOfCategory(category)){
            if (parkour.getIdentifier() == id) return parkour;
        }
        getLogger().info("Cannot find parkour: " + category + " " + id);
        return null;
    }

    public int getOptimalIdentifierToAdd(List<Parkour> parkourList){
        int identifier = 0;
        boolean identifierExists = true;
        while (identifierExists) {
            identifier++;
            identifierExists = false;
            for (Parkour parkour : parkourList) {
                if (parkour.getIdentifier() == identifier) {
                    identifierExists = true;
                    break;
                }
            }
        }
        return identifier;
    }
    public boolean categoryContainsIdentifier(ParkourCategory category, int identifier){
        for (Parkour parkour : parkours){
            if (parkour.getIdentifier() == identifier && parkour.getCategory().equals(category)) return true;
        }
        return false;
    }

    public int getLowestIdentifierOfCategory(ParkourCategory category){
        int id = 1;
        while (!categoryContainsIdentifier(category, id)){
            id++;
        }
        return id;
    }
    public int getPreviousIdentifierOfCategory(ParkourCategory category, int id){
        int previousID = id - 1;
        while (!categoryContainsIdentifier(category, previousID)){
            previousID--;
        }
        return previousID;
    }
    public boolean playerHasAccessToParkour(Player player, Parkour parkour, Boolean sendMessages){
        ParkourCategory category = parkour.getCategory();
        int identifier = parkour.getIdentifier();

        if (!player.hasPermission(ParkourPlugin.permissionSet.allParkoursPermission)) { // zrobic w permisjach inaczej
            if (parkour.getCategory().equals(ParkourCategory.NO_CATEGORY)) {
                if (sendMessages) player.sendMessage(ChatColor.RED + "Nie możesz dołączać do parkourów tej kategorii!");
                return false;
            }

            if (identifier != getLowestIdentifierOfCategory(category)) {
                int previousIdentifier = getPreviousIdentifierOfCategory(category, identifier);
                Parkour previousParkour = getParkourByCategoryAndID(category, previousIdentifier);
                if (!previousParkour.didPlayerFinishParkour(player)) {
                    if (sendMessages) {
                        player.sendMessage(ChatColor.RED + "Musisz ukończyć poprzedni parkour tej kategorii, żeby zagrać na tym parkourze!");
                        player.sendMessage(ChatColor.GREEN + "/pk " + previousParkour.getName());
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
