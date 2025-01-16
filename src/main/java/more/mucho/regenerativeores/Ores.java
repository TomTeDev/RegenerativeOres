package more.mucho.regenerativeores;

import more.mucho.regenerativeores.ores.Ore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Ores {
    void registerOre(Ore ore);
    int getNextID();
    Optional<Ore> getOre(int ID);
    List<Ore> getOres();
    void saveOre(Ore ore)throws Exception;
    boolean deleteOre(int oreID) throws Exception;
}
