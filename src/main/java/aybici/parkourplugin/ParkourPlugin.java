package aybici.parkourplugin;

import aybici.parkourplugin.blockabovereader.UnderPlayerBlockWatcher;
import aybici.parkourplugin.commands.CommandExecutorSetter;
import aybici.parkourplugin.parkours.Parkour;
import aybici.parkourplugin.parkours.ParkourSet;
import aybici.parkourplugin.services.StopPlayerParkourOnGamemodeChange;
import aybici.parkourplugin.sessions.ParkourSessionSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {

    public static final ParkourSet parkourSet = new ParkourSet();
    public static final ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);
    public static final UnderPlayerBlockWatcher underPlayerBlockWatcher = new UnderPlayerBlockWatcher();
    public static ParkourPlugin plugin;

    public static ParkourPlugin getInstance(){
        return plugin;
    }

    //Hibernate
    private static final Configuration configuration = new Configuration();
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    @Override
    public void onEnable() {
        plugin = this;

        try {
            initHibernate();
        }
        catch(Exception exception){
            System.out.println("Hibernate doesn't work! Error: "+exception.getMessage());
        }

        CommandExecutorSetter.setExecutors(this);
        Bukkit.getServer().getPluginManager().registerEvents(underPlayerBlockWatcher, this);
        Bukkit.getServer().getPluginManager().registerEvents(new StopPlayerParkourOnGamemodeChange(), this);
    }

    private void initHibernate(){
        configuration.configure();
        configuration.addAnnotatedClass(Parkour.class);

        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public ParkourPlugin()
    {
        super();
    }

    protected ParkourPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
}
