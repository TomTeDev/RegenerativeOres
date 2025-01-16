package more.mucho.regenerativeores.utils;

import more.mucho.regenerativeores.RegenerativeOres;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    private static final Plugin plugin = RegenerativeOres.getProvidingPlugin(RegenerativeOres.class);

    public static List<Material> matchBlocks(String type) {
        List<Material> out = new ArrayList<>();
        type = type.toUpperCase();
        for (Material material : Material.values()) {
            if(!material.isBlock())continue;
            if(material.isAir())continue;
            if (!material.name().contains(type)) continue;
            out.add(material);
        }
        return out;
    }

    public static boolean hasKey(ItemStack item,String key){
        if(item == null||key==null||key.isEmpty()||item.getType().isAir())return false;
        ItemMeta meta = item.getItemMeta();
        if(meta == null)return false;
        return meta.getPersistentDataContainer().has(new NamespacedKey(plugin,key));
    }

    public static int getInt(ItemStack itemStack,String key){
        if(itemStack == null||key==null||key.isEmpty()||itemStack.getType().isAir())return -1;
        ItemMeta meta = itemStack.getItemMeta();
        return getInt(meta,key,-1);
    }
    public static int getInt(ItemStack itemStack,String key,int defaultValue){
        if(itemStack == null||key==null||key.isEmpty()||itemStack.getType().isAir())return defaultValue;
        ItemMeta meta = itemStack.getItemMeta();
        return getInt(meta,key,defaultValue);
    }
    public static int getInt(ItemMeta meta,String key,int defaultValue){
        if(meta == null)return defaultValue;
        return meta.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,key), PersistentDataType.INTEGER,defaultValue);
    }
}
