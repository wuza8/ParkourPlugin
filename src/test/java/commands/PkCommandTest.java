package commands;

import AyBiCi.ParkourPlugin.ParkourPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PkCommandTest {
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
    public void parkourDoesntExist(){
        player.performCommand("pk kasdjflaskdj");
        assertEquals("Parkour with name \"kasdjflaskdj\" doesn't exist!", player.nextMessage());
    }
}
