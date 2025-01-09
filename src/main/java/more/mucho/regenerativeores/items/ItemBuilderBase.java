package more.mucho.regenerativeores.items;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public abstract class ItemBuilderBase {
    private final ItemStack item;
    protected int amount = 1;
    protected String displayName = null;
    protected List<String> lore = null;
    protected boolean colorDisplayName = false;
    protected boolean colorLore = false;
    protected PersistentDataContainer dataContainer;
    public ItemBuilderBase(ItemStack item) {
        Preconditions.checkArgument(item != null, "Item can not be null");
        Preconditions.checkArgument(item.getItemMeta() != null, "Item meta can not be null");
        this.item = item;
        this.dataContainer = item.getItemMeta().getPersistentDataContainer();
    }
    protected ItemStack getItem(){
        return item;
    }
}
