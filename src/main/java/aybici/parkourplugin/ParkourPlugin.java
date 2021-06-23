package aybici.parkourplugin;

import aybici.parkourplugin.blockabovereader.UnderPlayerBlockWatcher;
import aybici.parkourplugin.commands.CommandExecutorSetter;
import aybici.parkourplugin.parkours.ParkourSet;
import aybici.parkourplugin.sessions.ParkourSessionSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {

    public static final ParkourSet parkourSet = new ParkourSet();
    public static final ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);
    public static final UnderPlayerBlockWatcher underPlayerBlockWatcher = new UnderPlayerBlockWatcher();

    @Override
    public void onEnable() {
        CommandExecutorSetter.setExecutors(this);
        Bukkit.getServer().getPluginManager().registerEvents(underPlayerBlockWatcher, this);
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
