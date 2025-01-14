package more.mucho.regenerativeores.events;

import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OreMineEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player miner;
    private final Ore ore;
    private final Location location;

    private boolean isCancelled = false;
    public OreMineEvent(Player miner, Ore ore, Location location) {
        this.miner = miner;
        this.ore = ore;
        this.location = location.clone();
    }

    public Player getMiner() {
        return miner;
    }

    public Ore getOre() {
        return ore;
    }

    public Location getLocation() {
        return location;
    }



    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean arg) {
        isCancelled = arg;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
