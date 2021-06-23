package AyBiCi.ParkourPlugin;

import AyBiCi.ParkourPlugin.blockabovereader.UnderPlayerBlockWatcher;
import AyBiCi.ParkourPlugin.commands.CommandExecutorSetter;
import AyBiCi.ParkourPlugin.parkours.ParkourSet;
import AyBiCi.ParkourPlugin.sessions.ParkourSessionSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {

    public static ParkourSet parkourSet = new ParkourSet();
    public static ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);
    public static UnderPlayerBlockWatcher underPlayerBlockWatcher = new UnderPlayerBlockWatcher();

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
