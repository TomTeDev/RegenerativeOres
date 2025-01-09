package more.mucho.regenerativeores.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public ItemStack getWand() {
        //TODO finish
      return new ItemStack(Material.STICK);
    }

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
}
