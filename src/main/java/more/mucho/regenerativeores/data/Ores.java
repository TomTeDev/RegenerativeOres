package more.mucho.regenerativeores.data;

import more.mucho.regenerativeores.ores.Ore;

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
