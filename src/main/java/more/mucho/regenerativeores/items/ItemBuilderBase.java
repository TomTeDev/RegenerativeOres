package more.mucho.regenerativeores.items;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ItemBuilderBase {
    protected Material material;
    protected int amount = 1;
    protected String displayName = null;
    protected List<String> lore = null;
    protected boolean colorDisplayName = false;
    protected boolean colorLore = false;
    private final PersistentDataContainer pdc;

    public ItemBuilderBase(Material material) {
        Preconditions.checkArgument(material != null, "Material cannot be null");
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        Preconditions.checkArgument(meta != null, "Meta cannot be null");
        this.material = material;
        this.pdc = meta.getPersistentDataContainer();
    }

    public ItemBuilderBase(Material material, PersistentDataContainer pdc) {
        this.material = material;
        this.pdc = pdc;
    }

    public ItemBuilderBase(ItemStack item) {
        this.material = item.getType();
        ItemMeta meta = item.getItemMeta();
        Preconditions.checkArgument(meta != null, "Meta cannot be null");
        this.pdc = item.getItemMeta().getPersistentDataContainer();
    }

    protected void copyTags(PersistentDataContainer dataContainer) {
        if (pdc.equals(dataContainer)) return;
        pdc.copyTo(dataContainer, true);
    }

    public abstract Plugin getPlugin();

    public void setTag(@NonNull String key, Object value) {
        Preconditions.checkArgument(value != null, "Value cannot be null");
        pdc.set(new NamespacedKey(getPlugin(), key), convertToTag(value), value);
    }

    public boolean hasTag(String key) {
        return pdc.has(new NamespacedKey(getPlugin(), key));
    }

    public void setLoreNodes(Map<String, LoreNode.Priority> nodes) {
        NamespacedKey key = new NamespacedKey(getPlugin(), "lore_nodes");
        Gson gson = new Gson();
        // Convert priority to string map for compact storage
        Map<String, String> serializedNodes = nodes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().name()));
        String json = gson.toJson(serializedNodes);
        pdc.set(key, PersistentDataType.STRING, json);
    }

    public Map<String, LoreNode.Priority> getLoreNodes() {
        NamespacedKey key = new NamespacedKey(getPlugin(), "lore_nodes");
        if (!pdc.has(key, PersistentDataType.STRING)) return new HashMap<>();
        String json = pdc.get(key, PersistentDataType.STRING);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> serializedNodes = gson.fromJson(json, type);
        // Convert string map back to enum map
        return serializedNodes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> LoreNode.Priority.valueOf(entry.getValue())));
    }

    public void removeTags(String... keys) {
        for (String key : keys) {
            pdc.remove(new NamespacedKey(getPlugin(), key));
        }
    }

    public <T> T getTagOrDefault(String key, PersistentDataType<?, T> type, T defaultValue) {
        NamespacedKey namespacedKey = new NamespacedKey(getPlugin(), key);
        return pdc.has(namespacedKey, type) ? pdc.get(namespacedKey, type) : defaultValue;
    }


    private <T, Z> PersistentDataType<T, Z> convertToTag(Object val) {
        if (val instanceof String) {
            return (PersistentDataType<T, Z>) PersistentDataType.STRING;
        } else if (val instanceof Integer) {
            return (PersistentDataType<T, Z>) PersistentDataType.INTEGER;
        } else if (val instanceof Double) {
            return (PersistentDataType<T, Z>) PersistentDataType.DOUBLE;
        } else if (val instanceof Boolean) {
            return (PersistentDataType<T, Z>) PersistentDataType.BOOLEAN;
        } else if (val instanceof Long) {
            return (PersistentDataType<T, Z>) PersistentDataType.LONG;
        } else if (val instanceof Byte) {
            return (PersistentDataType<T, Z>) PersistentDataType.BYTE;
        } else if (val instanceof Short) {
            return (PersistentDataType<T, Z>) PersistentDataType.SHORT;
        } else if (val instanceof Float) {
            return (PersistentDataType<T, Z>) PersistentDataType.FLOAT;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + val.getClass().getSimpleName());
        }
    }
}
