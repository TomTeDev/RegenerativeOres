package more.mucho.regenerativeores.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MineExpEvent extends Event implements Cancellable {
    private final Player player;
    private int exp;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public MineExpEvent(Player player, int exp) {
        this.player = player;
        this.exp = exp;
    }

    public Player getPlayer() {
        return player;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int value) {
        this.exp = value;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean arg) {
        isCancelled = arg;
    }
}
