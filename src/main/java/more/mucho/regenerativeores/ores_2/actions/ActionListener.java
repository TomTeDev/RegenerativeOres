package more.mucho.regenerativeores.ores_2.actions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;

import java.util.List;

public class ActionListener implements Listener {
    //TODO register
    private final List<Action> actions;

    public ActionListener(List<Action> actions) {
        this.actions = actions;
    }

    @EventHandler
    public void onEvent(Event event) {
        actions.stream()
                .filter(action -> action.shouldTrigger(event))
                .forEach(action -> action.execute(event));
    }
}