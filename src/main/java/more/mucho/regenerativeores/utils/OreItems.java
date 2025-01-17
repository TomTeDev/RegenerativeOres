package more.mucho.regenerativeores.utils;

import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.items.ItemTags;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class OreItems {


    public static ItemStack getOreWand(int oreID) {
        return new ItemBuilder(Material.BARREL).setDisplayName(Colors.TIFFANY_BLUE+"Magical Ore Wand")
                .setLore(
                        "",
                        Colors.GREEN_INFO + "[!]Used to create ores",
                        Colors.WHITE + "Refers id: " + oreID,
                        Colors.RANK_GRAY + "" + Colors.ITALIC + "\"Just place it\""
                )
                .addTag(ItemTags.ORE_WAND, oreID)
                .build();
    }

    public static boolean isOreWand(ItemStack item) {
        return ItemUtils.hasKey(item, ItemTags.ORE_WAND);
    }

    public static Optional<Integer> getWandOreId(ItemStack item) {
        if (item == null || item.getType().isAir()) return Optional.empty();
        int oreID = ItemUtils.getInt(item, ItemTags.ORE_WAND);
        return oreID < 0 ? Optional.empty() : Optional.of(oreID);
    }
}
