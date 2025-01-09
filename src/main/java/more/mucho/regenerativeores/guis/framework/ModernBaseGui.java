package more.mucho.regenerativeores.guis.framework;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModernBaseGui implements GUI {
    private Plugin plugin;
    private final String title;
    private final int size;
    private final Inventory inventory;
    private Map<Integer, InventoryButton> buttonMap = new HashMap<>();
    private Map<Integer, ItemStack> designItems = new HashMap<>();

    public ModernBaseGui(String title, int size) {
        this.plugin = RegenerativeOres.getPlugin(RegenerativeOres.class);
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
        design();
    }

    public ModernBaseGui(String title, int size, Plugin plugin) {
        this(title, size);
        this.plugin = plugin;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public void addButton(int slot, InventoryButton button) {
        this.buttonMap.put(slot, button);
    }

    public void removeButton(int slot){
        this.buttonMap.remove(slot);
    }

    protected void addGoBackButton(int slot, Inventory inventory) {
        addButton(slot, new InventoryButton().creator((player1) -> new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayName(Colors.LAVENDER + "Go back").build()).consumer((event) -> {
            openInventory((Player) event.getWhoClicked(), inventory);
        }));
    }

    public void addDesignItem(ItemStack item, int slot) {
        this.designItems.put(slot, item);
    }

    public void addDesignItem(ItemStack item, int slotStart, int slotEnd) {
        for (int i = slotStart; i <= slotEnd; i++) {
            this.designItems.put(i, item);
        }
    }

    protected void openInventory(Player player, Inventory inventory) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.openInventory(inventory), 1);
    }

    protected void closeInventory(Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, player::closeInventory, 1);
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        InventoryButton button = this.buttonMap.get(slot);
        if (button != null) {
            button.getEventConsumer().accept(event);
        }
    }

    public void decorate(Player player) {
        this.buttonMap.forEach((slot, button) -> {
            ItemStack icon = button.getCreator().apply(player);
            this.inventory.setItem(slot, icon);
        });
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.designItems.forEach(this.inventory::setItem);
        this.decorate((Player) event.getPlayer());
    }

    @Override
    public abstract void onClose(InventoryCloseEvent event);

    protected abstract void design();

    @Override
    public void onDrag(InventoryDragEvent event) {
    }

    protected List<Integer> getMiddleSlots() {
        List<Integer> out = new ArrayList<>();
        for (int x = 0; (x * 9) < getSize(); x++) {
            out.add(getMiddleSlot(x));
        }
        return out;
    }

    private int getMiddleSlot(int row) {
        return switch (row) {
            case 0 -> 4;
            case 1 -> 13;
            case 2 -> 22;
            case 3 -> 31;
            case 4 -> 40;
            case 5 -> 49;
            default -> 0;
        };
    }
}
