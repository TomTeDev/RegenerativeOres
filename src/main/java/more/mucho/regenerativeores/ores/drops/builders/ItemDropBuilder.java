package more.mucho.regenerativeores.ores.drops.builders;

import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.drops.BaseItemDrop;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemDropBuilder extends DropBuilder<BaseItemDrop> {
    private ItemStack item;

    /**
     * Constructs a new ItemDropBuilder with default values.
     */
    public ItemDropBuilder() {
        this.item = new ItemStack(Material.AIR); // Default to an "air" item
    }

    /**
     * Sets the item to be dropped.
     *
     * @param item the item to drop
     * @return this builder for chaining
     * @throws IllegalArgumentException if item is null
     */
    public ItemDropBuilder setItem(ItemStack item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.item = item.clone();
        return this;
    }

    /**
     * Gets the item to be dropped.
     *
     * @return a clone of the item
     */
    public ItemStack getItem() {
        return this.item.clone();
    }

    /**
     * Builds the BaseItemDrop object.
     *
     * @return the constructed BaseItemDrop object
     * @throws IllegalStateException if the item is not set or minAmount > maxAmount
     */
    @Override
    public BaseItemDrop build() {
        if (this.item == null) {
            throw new IllegalStateException("Item must be set before building");
        }
        if (this.getMinAmount() > this.getMaxAmount()) {
            throw new IllegalStateException("minAmount cannot be greater than maxAmount");
        }
        return new BaseItemDrop(
                new Range<>(this.getMinAmount(), this.getMaxAmount()),
                this.getChance(),
                this.isDirect(),
                this.getMessage(),
                this.getSound(),
                this.item.clone()
        );
    }
}