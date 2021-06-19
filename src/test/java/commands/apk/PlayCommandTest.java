package commands.apk;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayCommandTest {
    private ServerMock server;
    private ParkourPlugin plugin;
    private PlayerMock player;
    private World world;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        plugin = (ParkourPlugin) MockBukkit.load(ParkourPlugin.class);
        player = server.addPlayer();
        world = player.getLocation().getWorld();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void playerNotOnParkour(){
        player.performCommand("apk play");
        assertEquals("You need to join parkour to use this command!", player.nextMessage());
    }

    @Test
    public void doesItTeleportToParkourSpawn() {
        player.performCommand("apk add play1");
        player.assertTeleported(player.getLocation(), 1);
        player.performCommand("apk play");
        player.assertTeleported(player.getLocation(), 1);
    }
    @Test
    public void doesItChangeGamemode(){
        player.setGameMode(GameMode.CREATIVE);
        player.performCommand("apk add play2");
        player.performCommand("apk play");
        assertEquals(GameMode.ADVENTURE, player.getGameMode());
    }
}
