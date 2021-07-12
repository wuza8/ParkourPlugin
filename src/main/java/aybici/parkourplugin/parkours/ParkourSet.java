package aybici.parkourplugin.parkours;

import aybici.parkourplugin.ParkourPlugin;
import org.bukkit.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashSet;
import java.util.Set;

public class ParkourSet {
    private final Set<Parkour> parkours = new HashSet<>();

    public void addParkour(String name, Location location) throws IllegalStateException{
        if(doesExist(name))
            throw new IllegalStateException("Parkour with name \""+name+"\" already exists!");
        if(Character.isDigit(name.toCharArray()[0]))
            throw new IllegalStateException("Parkour name can't start from a digit!");

        parkours.add(new Parkour(name, location));
    }

    public Parkour getParkour(String name) {
        for(Parkour parkour : parkours){
            if(parkour.getName().equals(name)) return parkour;
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

    public void removeParkour(String name) {
        parkours.remove( getParkour(name) );
    }

    public void saveParkours(){
        Session session = ParkourPlugin.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        for(Parkour parkour : parkours){
            session.save(parkour);
        }

        transaction.commit();
        session.close();
    }

    public void loadParkours(){

    }
}
