package more.mucho.regenerativeores.ores.variants;

import more.mucho.regenerativeores.ores.drops.MiningDrop;

import java.util.List;

public interface Droppable {
    List<MiningDrop> getMiningDrops();
}