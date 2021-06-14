package AyBiCi.ParkourPlugin;

import AyBiCi.ParkourPlugin.commands.PkCommand;
import AyBiCi.ParkourPlugin.commands.apk.AddCommand;
import AyBiCi.ParkourPlugin.commands.apk.RemoveCommand;
import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {

    public static ParkourSet parkourSet = new ParkourSet();
    public static ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);

    @Override
    public void onEnable() {
        getCommand("pk").setExecutor(new PkCommand());

        SubcommandExecutor executor = new SubcommandExecutor("apk");

        executor.addCommandExecutor(new Subcommand(
            "add",
            "<name>",
            "adds new parkour",
                new AddCommand()
            ));

        executor.addCommandExecutor(new Subcommand(
                "remove",
                "<name>",
                "removes parkour",
                new RemoveCommand()
        ));

        getCommand("apk").setExecutor(executor);
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
