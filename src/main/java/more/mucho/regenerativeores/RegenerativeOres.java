package more.mucho.regenerativeores;

import more.mucho.regenerativeores.commands.CustomCommand;
import more.mucho.regenerativeores.data.OresCache;
import more.mucho.regenerativeores.listeners.BlockBreakListener;
import more.mucho.regenerativeores.listeners.GuiListener;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.CheckForNull;

public final class RegenerativeOres extends JavaPlugin {
    private WorkloadThread workloadThread = null;
    private BukkitTask workloadTask = null;
    private RegeneratorImpl regenerator = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        startWorkload();
        setupOresCache();
        registerListeners(
                new GuiListener(),
                new BlockBreakListener(OresCache.i())
        );
        registerCommands();
        regenerator = new RegeneratorImpl();
        regenerator.enable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        stopWorkload();
        OresCache.i().clear();
    }

    private void stopWorkload() {
        if (workloadTask != null) {
            workloadTask.cancel();
        }
        if (workloadThread != null) {
            workloadThread = null;
        }
    }

    private void startWorkload() {
        regenerator.disable();
        regenerator = null;

        stopWorkload();
        workloadThread = new WorkloadThread();
        workloadTask = Bukkit.getScheduler().runTaskTimer(this, workloadThread, 0, 1);
    }

    @CheckForNull
    public WorkloadThread getWorkloadThread() {
        return workloadThread;
    }

    private void setupOresCache() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                OresCache.i().loadChunkOresAsync(world, chunk.getX(), chunk.getZ());
            }
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands(CustomCommand... customCommands) {
        for (CustomCommand customCommand : customCommands) {
            for (String commandName : customCommand.getCommands()) {
                PluginCommand pluginCommand = getCommand(commandName);
                if (pluginCommand == null) {
                    getLogger().severe("Command " + commandName + " not found!");
                    continue;
                }
                pluginCommand.setExecutor(customCommand);
                pluginCommand.setTabCompleter(customCommand);
            }
        }
    }


}
