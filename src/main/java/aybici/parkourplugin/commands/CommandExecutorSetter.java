package aybici.parkourplugin.commands;

import aybici.parkourplugin.commands.apk.*;
import aybici.parkourplugin.commands.times.PrintTimesCommand;
import aybici.parkourplugin.parkours.DisplayingTimesState;
import com.github.aybici.Subcommand;
import com.github.aybici.SubcommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandExecutorSetter {

    public static void setExecutors(JavaPlugin plugin){
        plugin.getCommand("pk").setExecutor(new PkCommand());
        plugin.getCommand("pkat").setExecutor(new PkatCommand());

        SubcommandExecutor apkExecutor = new SubcommandExecutor("apk");
        SubcommandExecutor timesExecutor = new SubcommandExecutor("times");

        timesExecutor.addCommandExecutor(new Subcommand(
                "my",
                "[sort type] [page]",
                "sends to player all his times of parkour",
                new PrintTimesCommand(DisplayingTimesState.ONE_PLAYER_ALL_TIMES)
        ));

        timesExecutor.addCommandExecutor(new Subcommand(
                "top",
                "[sort type] [page]",
                "sends to player best times of all players",
                new PrintTimesCommand(DisplayingTimesState.ALL_PLAYERS_BEST_TIMES)
        ));

        timesExecutor.addCommandExecutor(new Subcommand(
                "all",
                "[sort type] [page]",
                "sends to player all times of parkour",
                new PrintTimesCommand(DisplayingTimesState.ALL_PLAYERS_ALL_TIMES)
        ));


        apkExecutor.addCommandExecutor(new Subcommand(
                "deltop",
                "<player/all> [best]",
                "deletes times of the parkour",
                new DeleteTopsCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "add",
                "<name>",
                "adds new parkour",
                new AddCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand(
                "remove",
                "<name>",
                "removes parkour",
                new RemoveCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand("addbb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "adds new backblocks",
                new AddBackBlockCommand()
        ));

        apkExecutor.addCommandExecutor(new Subcommand("removebb"
                , "<block1> [block2] [block3] [block4] [block5]"
                , "removes backblocks",
                new RemoveBackBlockCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand("setspawn"
                , ""
                , "sets new spawn point",
                new SetSpawnCommand()
        ));
        apkExecutor.addCommandExecutor(new Subcommand("play"
                , ""
                , "teleports player to joined parkour",
                new PlayCommand()
        ));

        plugin.getCommand("times").setExecutor(timesExecutor);
        plugin.getCommand("apk").setExecutor(apkExecutor);
    }
}
