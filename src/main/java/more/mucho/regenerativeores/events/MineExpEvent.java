package more.mucho.regenerativeores.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class MineExpEvent extends Event implements Cancellable {
    private final Player player;
    private int exp;

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
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
