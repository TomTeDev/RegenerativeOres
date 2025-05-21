package more.mucho.regenerativeores.ores_2.actions;

import org.bukkit.event.Event;

/**
 * Represents an action that can be triggered by a Bukkit event
 */
public interface Action {
    /**
     * Called when the action should be executed
     * @param event The triggering event
     */
    void execute(Event event);

    /**
     * Gets the event class this action handles
     */
    Class<? extends Event> getEventType();

    /**
     * Checks if this action should trigger for the given event
     */
    default boolean shouldTrigger(Event event) {
        return getEventType().isInstance(event);
    }
}