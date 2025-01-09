package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.guis.framework.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI gui) {
            event.setCancelled(true);
            gui.onClick(event);
        }
    }
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI gui) {
            event.setCancelled(true);
            gui.onDrag(event);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getInventory().getHolder()!=null&&event.getInventory().getHolder() instanceof GUI gui){
            gui.onOpen(event);
        }

    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory().getHolder()!=null&&event.getInventory().getHolder() instanceof GUI gui){
            gui.onClose(event);
        }
    }
}
