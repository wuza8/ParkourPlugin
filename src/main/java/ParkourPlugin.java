import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class ParkourPlugin extends JavaPlugin {

    private ParkourSet parkourSet = new ParkourSet();
    private ParkourSessionSet parkourSessionSet = new ParkourSessionSet(parkourSet);

    @Override
    public void onEnable() {
        SubcommandExecutor executor = new SubcommandExecutor("apk");

        executor.addCommandExecutor(new Subcommand(
            "addpk",
            "<name>",
            "adds new parkour",
            (CommandSender sender, Command command, String s, String[] args) -> {
                Player player = (Player) sender;

                try {
                    parkourSet.addParkour(args[0], player.getLocation());
                    player.sendMessage("Parkour with name \""+args[0]+"\" added!");
                }
                catch(Exception exception){
                    player.sendMessage(exception.getMessage());
                }

                return true;
            }
            ));

        getCommand("apk").setExecutor(executor);

        getCommand("pk").setExecutor((CommandSender sender, Command command, String s, String[] args) -> {
            Player player = (Player) sender;
            parkourSessionSet.teleportToParkour(player, args[0]);
            return true;
        });

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
