package commands.apk;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveBackBlockCommandTest {
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
        player.performCommand("apk removebb dirt");
        assertEquals("You need to join parkour to use this command!", player.nextMessage());
    }

    @Test
    public void playerRemovesNotExistingBackBlock(){
        player.performCommand("apk add rembb1");
        player.nextMessage();
        player.performCommand("apk removebb dirt");
        assertEquals("DIRT is not a backblock!", player.nextMessage());
    }

    @Test
    public void playerRemovesBackBlock(){
        player.performCommand("apk add rembb2");
        player.nextMessage();
        player.performCommand("apk addbb dirt");
        player.nextMessage();
        player.performCommand("apk removebb dirt");
        assertEquals("Removed backblock DIRT", player.nextMessage());
    }

    @Test
    public void playerWantsToRemoveNotExistingMaterial(){
        player.performCommand("apk add rembb3");
        player.nextMessage();
        player.performCommand("apk removebb asdjklfhlas");
        assertEquals("\"asdjklfhlas\" is not a material!", player.nextMessage());
    }
}