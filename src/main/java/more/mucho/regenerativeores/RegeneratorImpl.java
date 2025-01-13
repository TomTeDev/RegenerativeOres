package more.mucho.regenerativeores;
import more.mucho.regenerativeores.ores.Ore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayDeque;
import java.util.Iterator;

public class RegeneratorImpl implements Regenerator {
    private final ArrayDeque<RegenObject> deque = new ArrayDeque<>();
    private BukkitTask bukkitTask = null;

    @Override
    public void scheduleRegen(Ore ore, Location location) {
        long regenTime = System.currentTimeMillis() + ore.getDelay() * 1000L;
        synchronized (deque) {
            deque.add(new RegenObject(location, ore, regenTime));
        }
    }

    private record RegenObject(Location location, Ore ore, long regenTime) {
    }

    public void enable() {
        if (bukkitTask != null) {
            bukkitTask.cancel();
        }
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
                RegenerativeOres.getPlugin(RegenerativeOres.class),
                () -> {
                    long currentTime = System.currentTimeMillis();
                    synchronized (deque) {
                        Iterator<RegenObject> iterator = deque.iterator();
                        while (iterator.hasNext()) {
                            RegenObject regenObject = iterator.next();

                            // Check if the regen time has been reached
                            if (regenObject.regenTime <= currentTime) {
                                // Schedule ore regeneration on the main thread
                                Bukkit.getScheduler().runTask(
                                        RegenerativeOres.getPlugin(RegenerativeOres.class),
                                        () -> regenObject.ore.regen(regenObject.location)
                                );

                                // Remove the object from the deque
                                iterator.remove();
                            }
                        }
                    }
                },
                1, 1
        );
    }

    public void disable() {
        if (bukkitTask != null) {
            bukkitTask.cancel();
            bukkitTask = null;
        }
        saveUnprocessedTasks();
    }

    private void saveUnprocessedTasks() {
        synchronized (deque) {
            for (RegenObject regenObject : deque) {
                // TODO: Write each regenObject to a file for future restoration
            }
        }
    }
}
