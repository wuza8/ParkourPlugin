import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkourPluginTest {
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
    public void addParkour(){
        Location parkour1 = new Location(world, 10, 10, 10);
        Location parkour2 = new Location(world, 20, 20, 20);

        player.simulatePlayerMove(parkour1);
        player.performCommand("apk addpk abc");
        assertEquals("Parkour with name \"abc\" added!", player.nextMessage());

        player.simulatePlayerMove(parkour2);
        player.performCommand("apk addpk cde");
        assertEquals("Parkour with name \"cde\" added!", player.nextMessage());

        player.performCommand("pk abc");
        player.assertTeleported(parkour1, 1);

        player.performCommand("pk cde");
        player.assertTeleported(parkour2, 1);
    }

    @Test
    public void addParkour_nameAlreadyUsed(){
        player.performCommand("apk addpk abc");
        player.nextMessage();
        player.performCommand("apk addpk abc");
        assertEquals("Parkour with name \"abc\" already exists!", player.nextMessage());
    }

    @Test
    public void teleportToParkour_parkourDoesntExist(){
        player.performCommand("pk abc");
        assertEquals("Parkour with name \"abc\" doesn't exist!", player.nextMessage());
    }

    @Test
    public void addParkour_nameCantStartFromDigit(){
        player.performCommand("apk addpk 1abc");
        assertEquals("Parkour name can't start from a digit!", player.nextMessage());
    }


}
