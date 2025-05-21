package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Base class for actions with chance and requirement support
 * @param <E> The specific event type this action handles
 */
public abstract class BaseAction<E extends Event> implements Action {
    private final UUID uuid;
    private final int chance;
    private final List<Requirement> requirements;
    private final Class<E> eventType;

    protected BaseAction(Class<E> eventType, UUID uuid, int chance) {
        this(eventType, uuid, chance, Collections.emptyList());
    }

    protected BaseAction(Class<E> eventType, UUID uuid, int chance,
                         Collection<Requirement> requirements) {
        this.eventType = Objects.requireNonNull(eventType);
        this.uuid = Objects.requireNonNull(uuid);
        this.chance = Math.max(0, Math.min(100, chance));
        this.requirements = List.copyOf(requirements);
    }

    @Override
    public final Class<E> getEventType() {
        return eventType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void execute(Event event) {
        if (shouldTrigger(event) && shouldExecute((E) event)) {
            onAction((E) event);
        }
    }

    protected boolean shouldExecute(E event) {
        Player player = extractPlayer(event);
        return player != null && testChance() && testRequirements(player);
    }

    protected boolean testChance() {
        return chance >= ThreadLocalRandom.current().nextInt(100);
    }

    protected boolean testRequirements(Player player) {
        return requirements.stream().allMatch(r -> r.test(player));
    }

    /**
     * Implementations must provide how to extract player from their event
     */
    protected abstract Player extractPlayer(E event);

    /**
     * Implementations define their specific behavior here
     */
    protected abstract void onAction(E event);

    // Getters
    public UUID getUuid() { return uuid; }
    public int getChance() { return chance; }
    public List<Requirement> getRequirements() { return requirements; }
}