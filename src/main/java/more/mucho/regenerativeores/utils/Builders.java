package more.mucho.regenerativeores.utils;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.ores.OreBuilder;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Builders {
    public static HashMap<String, OreBuilder> oreBuilders = new HashMap<>();

    public static OreBuilder getOreBuilder(Player player) {
        return oreBuilders.containsKey(player.getName().toLowerCase()) ? oreBuilders.get(player.getName().toLowerCase()) : createOreBuilder(player);
    }

    private static OreBuilder createOreBuilder(Player player) {
        OreBuilder oreBuilder = new OreBuilder(RegenerativeOres.getPlugin(RegenerativeOres.class).getOresService().getOres().getNextID());
        oreBuilders.put(player.getName().toLowerCase(), oreBuilder);
        return oreBuilder;
    }
    public static void clearOreBuilder(Player player){
        oreBuilders.remove(player.getName().toLowerCase());
    }
}
