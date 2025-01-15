package more.mucho.regenerativeores.items;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LoreNodeRegistry {
    private static final Map<String, Function<ItemMeta, String>> functionMap = new HashMap<>();

    public static void registerFunction(String id, Function<ItemMeta, String> function) {
        functionMap.put(id, function);
    }

    public static Function<ItemMeta, String> getFunctionById(String id) {
        return functionMap.get(id);
    }


    public static void initialize() {
        functionMap.clear();
        // Register functions here
    }
}
