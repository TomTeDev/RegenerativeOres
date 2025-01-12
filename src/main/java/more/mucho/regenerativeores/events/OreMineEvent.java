package more.mucho.regenerativeores.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OreMineEvent extends Event implements Cancellable {
    //TODO finish;
    @Override
    public HandlerList getHandlers() {
        return ;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
