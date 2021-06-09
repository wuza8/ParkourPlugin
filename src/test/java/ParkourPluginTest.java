import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.After;
import org.junit.Before;

public class ParkourPluginTest {
    private ServerMock server;
    private ParkourPlugin plugin;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        plugin = (ParkourPlugin) MockBukkit.load(ParkourPlugin.class);
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }
}
