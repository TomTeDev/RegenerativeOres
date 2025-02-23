package more.mucho.regenerativeores.utils;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.ores.OreBuilder;
import more.mucho.regenerativeores.ores.drops.builders.ExpDropBuilder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class Builders {
    private static HashMap<String, OreBuilder> oreBuilders = new HashMap<>();
    private static HashMap<String, ExpDropBuilder> getOrCreateExpDropBuilders = new HashMap<>();


    public static Optional<OreBuilder> getOreBuilder(Player player) {
        return Optional.ofNullable(oreBuilders.get(player.getName().toLowerCase()));

    }

    public static OreBuilder createOreBuilder(Player player) {
        OreBuilder oreBuilder = new OreBuilder(RegenerativeOres.getPlugin(RegenerativeOres.class).getOresService().getOres().getNextID());
        oreBuilders.put(player.getName().toLowerCase(), oreBuilder);
        return oreBuilder;
    }
    public static void clearOreBuilder(Player player){
        oreBuilders.remove(player.getName().toLowerCase());
    }


    public static Optional<ExpDropBuilder> getExpDropBuilder(Player player) {
        return Optional.ofNullable(getOrCreateExpDropBuilders.get(player.getName().toLowerCase()));
    }
    public static ExpDropBuilder createExpDropBuilder(Player player) {
        ExpDropBuilder expDropBuilder = new ExpDropBuilder();
        getOrCreateExpDropBuilders.put(player.getName().toLowerCase(), expDropBuilder);
        return expDropBuilder;
    }
    public static void clearExpDropBuilder(Player player){
        getOrCreateExpDropBuilders.remove(player.getName().toLowerCase());
    }
    public static ExpDropBuilder getOrCreateExpDropBuilder(Player player) {
        return getOrCreateExpDropBuilders.computeIfAbsent(player.getName().toLowerCase(), k -> new ExpDropBuilder());
    }
}
