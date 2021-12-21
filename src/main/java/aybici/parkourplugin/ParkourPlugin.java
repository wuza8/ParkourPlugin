package aybici.parkourplugin;

import aybici.parkourplugin.blockabovereader.UnderPlayerBlockWatcher;
import aybici.parkourplugin.commands.CommandExecutorSetter;
import aybici.parkourplugin.events.PlayerAndEnvironmentListener;
import aybici.parkourplugin.parkours.ParkourSet;
import aybici.parkourplugin.parkours.TopListDisplay;
import aybici.parkourplugin.sessions.ParkourSessionSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {
    public static final ParkourSet parkourSet = new ParkourSet();
    public static ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);
    public static final UnderPlayerBlockWatcher underPlayerBlockWatcher = new UnderPlayerBlockWatcher();
    public static PositionSaver positionSaver = new PositionSaver();
    private static ParkourPlugin plugin;
    public static ParkourPlugin getInstance(){
        return plugin;
    }
    public static TopListDisplay topListDisplay = new TopListDisplay();
    public static UUIDList uuidList = new UUIDList();
    public static PermissionSet permissionSet = new PermissionSet();
    public static Lobby lobby = new Lobby();

    @Override
    public void onEnable() {
        plugin = this;
        uuidList.loadList();
        uuidList.loadPlayerNames();
        parkourSet.loadParkours(parkourSet.parkoursFolder);
        lobby.loadLobbyLocation(lobby.directory);
        lobby.runTeleportToLobbyAllTask();
        CommandExecutorSetter.setExecutors(this);
        Bukkit.getServer().getPluginManager().registerEvents(underPlayerBlockWatcher, this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerAndEnvironmentListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(positionSaver, this);
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
