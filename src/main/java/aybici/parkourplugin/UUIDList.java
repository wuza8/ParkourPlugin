package aybici.parkourplugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDList {
    private final String directory = "dataBase/uuidList.txt";
    private List<UUID> identifiers = new ArrayList<>();

    public void addIdentifier(UUID identifier){
        identifiers.add(identifier);
        saveIdentifierInfile(identifier);
    }
    public boolean contains(UUID identifier){
        return identifiers.contains(identifier);
    }
    public short getShortIdentifier(UUID identifier){
        return (short) (identifiers.indexOf(identifier) - 32767);
    }
    public UUID getUUIDFromShort(short index){
        return identifiers.get(index + 32767);
    }
    public int getLength(){
        return identifiers.size();
    }

    public void saveIdentifierInfile(UUID identifier){
        if (!new File(directory).exists()) FileCreator.createFile(directory);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory, true));
            writer.write(identifier.getMostSignificantBits() + "," + identifier.getLeastSignificantBits() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadList(){
        if (!new File(directory).exists()) return;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(directory));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String currentLine = null;
        boolean lineExists = true;
        while (lineExists) {
            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (currentLine == null) lineExists = false;
            else {
                long mostSignBits = Long.parseLong(currentLine.substring(0, currentLine.indexOf(',')));
                long leastSignBits = Long.parseLong(currentLine.substring(currentLine.indexOf(',') + 1));
                identifiers.add(new UUID(mostSignBits, leastSignBits));
            }
        }
    }
}
