package aybici.parkourplugin.commands;

import aybici.parkourplugin.commands.apk.*;
import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandExecutorSetter {

    public static void setExecutors(JavaPlugin plugin){
        plugin.getCommand("pk").setExecutor(new PkCommand());
        plugin.getCommand("pkat").setExecutor(new PkatCommand());

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

        executor.addCommandExecutor(new Subcommand("addbb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "adds new backblocks",
                new AddBackBlockCommand()
        ));

        executor.addCommandExecutor(new Subcommand("removebb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "removes backblocks",
                new RemoveBackBlockCommand()
        ));
        executor.addCommandExecutor(new Subcommand("setspawn"
                , ""
                , "sets new spawn point",
                new SetSpawnCommand()
        ));
        executor.addCommandExecutor(new Subcommand("play"
                , ""
                , "teleports player to joined parkour",
                new PlayCommand()
        ));


        plugin.getCommand("apk").setExecutor(executor);
    }
}
