package more.mucho.regenerativeores;

import more.mucho.regenerativeores.ores.Ore;

import java.util.ArrayList;
import java.util.Optional;

public interface Ores {
    void registerOre(Ore ore);
    Optional<Integer>getNextID();
    Optional<Ore> getOre(int ID);
    ArrayList<Ore> getOres();
    boolean deleteOre(int oreID) throws Exception;
}
