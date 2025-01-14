package more.mucho.regenerativeores;

import more.mucho.regenerativeores.ores.Ore;

import java.util.Optional;

public interface Ores {
    void registerOre(Ore ore);

    Optional<Ore> getOre(int ID);
}
