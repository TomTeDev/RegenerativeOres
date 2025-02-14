package more.mucho.regenerativeores.guis.framework;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public interface GUI extends InventoryHolder {

    String getTitle();
    int getSize();
    void open(Player player);
    void open(HumanEntity entity);
    void onClick(InventoryClickEvent event);
    void onOpen(InventoryOpenEvent event);
    void onDrag(InventoryDragEvent event);
    void onClose(InventoryCloseEvent event);

}
