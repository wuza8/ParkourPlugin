package AyBiCi.ParkourPlugin.blockabovereader;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UnderPlayerBlockReader {
    public HashMap<UUID, List<OnNewBlockPlayerStandObserver>> playerBlockObservers = new HashMap<>();
}
