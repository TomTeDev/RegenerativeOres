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
    public static Optional<Integer> getOreIdOfWand(ItemStack item) {
        if (item == null || item.getType().isAir()) return Optional.empty();
        int oreID = ItemUtils.getInt(item, ItemTags.ORE_WAND);
        return oreID < 0 ? Optional.empty() : Optional.of(oreID);
    }

    public static ItemStack getOresEditor(){
        return new ItemBuilder(Material.FEATHER).setDisplayName(Colors.ADMIN_RED+"Ores editor")
                .setLore(
                        "",
                        Colors.GREEN_INFO + "[!]Used to delete and edit ores",
                        Colors.DARK_AQUA+"LEFT CLICK "+Colors.WHITE+"to delete ore",
                        Colors.DARK_AQUA+"RIGHT CLICK "+Colors.WHITE+"to edit ore",
                        Colors.RANK_GRAY + "" + Colors.ITALIC + "\"Just click it\""
                )
                .addTag(ItemTags.ORE_EDITOR, true)
                .build();
    }
    public static boolean isOresEditor(ItemStack item){
        return ItemUtils.hasKey(item, ItemTags.ORE_EDITOR);
    }
}
