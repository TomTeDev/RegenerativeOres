package more.mucho.regenerativeores.items;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LoreNode {
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    private final Priority displayPriority;
    private final String functionIdentifier; // Identifier for deserialization
    private transient final Function<ItemMeta, String> craftLorePart;

    public LoreNode(Priority displayPriority, String functionIdentifier, Function<ItemMeta, String> craftLorePart) {
        this.displayPriority = displayPriority;
        this.functionIdentifier = functionIdentifier;
        this.craftLorePart = craftLorePart;
    }

    public Priority getDisplayPriority() {
        return displayPriority;
    }

    public String getFunctionIdentifier() {
        return functionIdentifier;
    }

    public String generateLorePart(ItemMeta meta) {
        return craftLorePart.apply(meta);
    }

    public static LoreNode fromSerializedData(Priority priority, String functionIdentifier) {
        // Map identifiers back to functions during deserialization
        Function<ItemMeta, String> function = LoreNodeRegistry.getFunctionById(functionIdentifier);
        return new LoreNode(priority, functionIdentifier, function);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("priority", displayPriority.name());
        data.put("functionIdentifier", functionIdentifier);
        return data;
    }

    public static LoreNode deserialize(Map<String, Object> data) {
        Priority priority = Priority.valueOf((String) data.get("priority"));
        String functionIdentifier = (String) data.get("functionIdentifier");
        return LoreNode.fromSerializedData(priority, functionIdentifier);
    }
}
