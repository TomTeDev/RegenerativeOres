package more.mucho.regenerativeores.guis.framework;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class InventoryButton {

    private Function<Player, ItemStack> creator;
    private Consumer<InventoryClickEvent> eventConsumer;

    public InventoryButton consumer(Consumer<InventoryClickEvent> consumer) {
        this.eventConsumer = consumer;
        return this;
    }

    public InventoryButton creator(Function<Player, ItemStack> creator) {
        this.creator = creator;
        return this;
    }
    public Consumer<InventoryClickEvent> getEventConsumer() {
        return this.eventConsumer;
    }

    public Function<Player, ItemStack> getCreator() {
        return this.creator;
    }
}
