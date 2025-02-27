package more.mucho.regenerativeores.ores.drops;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.Range;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BaseItemDrop extends BaseDrop {
    private final ItemStack item;

    public BaseItemDrop(Range<Integer, Integer> range, int dropChance, boolean isDirect, MiningMessage message, Sound sound, ItemStack item) {
        super(range, dropChance, isDirect, message, sound);
        Preconditions.checkArgument(range.max <= item.getMaxStackSize(), "maxAmount must be less than or equal to item max stack size");
        this.item = item.clone();
    }

    public ItemStack getItem() {
        return item.clone();
    }

    private ItemStack getDrop() {
        ItemStack clone = item.clone();
        clone.setAmount(getRandomDropAmount());
        return clone;
    }

    @Override
    public void drop(Player player, Location dropLocation) {
        ItemStack drop = getDrop();
        if (isDirect) {
            HashMap<Integer, ItemStack> leftOvers = player.getInventory().addItem(drop);
            if (!leftOvers.isEmpty()) {
                for (ItemStack i : leftOvers.values()) {
                    player.getWorld().dropItemNaturally(dropLocation, i);
                }
            }
        } else {
            player.getWorld().dropItemNaturally(dropLocation, drop);
        }


        sendMessage(player);

        if (sound != null) {
            player.getWorld().playSound(player, sound, 1, 1);
        }
    }

    @Override
    public void serialize(ConfigurationSection section) {
        if (section == null) throw new IllegalArgumentException("Section is null");
        super.serialize(section);
        section.set("type", "itemDrop");
        section.set("item", item);
        if (sound != null) {
            section.set("sound", sound.name());
        }

    }

    public static BaseItemDrop deserialize(ConfigurationSection section) {
        if (section == null) {
            throw new IllegalArgumentException("Section is null");
        }
        BaseDrop baseDrop = BaseDrop.deserializeBaseDrop(section);  // Deserialize common fields
        // Deserialize the subclass-specific fields
        ItemStack item = section.getItemStack("item");
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        String soundName = section.getString("sound");
        Sound sound = null;
        if (soundName != null) {
            sound = Sound.valueOf(soundName);
        }

        return new BaseItemDrop(baseDrop.range, baseDrop.dropChance, baseDrop.isDirect, baseDrop.message, sound, item);
    }

}
