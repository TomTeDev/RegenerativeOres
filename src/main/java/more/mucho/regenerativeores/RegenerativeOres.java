package more.mucho.regenerativeores;

import more.mucho.regenerativeores.commands.BasicConfigMessages;
import more.mucho.regenerativeores.commands.CustomCommand;
import more.mucho.regenerativeores.commands.OresCommand;
import more.mucho.regenerativeores.data.OresCacheImpl;
import more.mucho.regenerativeores.data.OresImpl;
import more.mucho.regenerativeores.data.OresService;
import more.mucho.regenerativeores.items.LoreNodeRegistry;
import more.mucho.regenerativeores.listeners.BlockBreakListener;
import more.mucho.regenerativeores.listeners.ChunksListener;
import more.mucho.regenerativeores.listeners.GuiListener;
import more.mucho.regenerativeores.listeners.ItemsListener;
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
    private OresService oresService;
    private OresImpl ores = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadOres();
        oresService = new OresService(this, ores, new OresCacheImpl());
        startWorkload();
        loadExistingChunksToCache();

        registerListeners(
                new GuiListener(),
                new BlockBreakListener(oresService),
                new TestListener(this),
                new ItemsListener(oresService),
                new ChunksListener(oresService)
        );
        registerCommands(
                new OresCommand()
        );
        regenerator = new RegeneratorImpl();
        regenerator.enable();
        LoreNodeRegistry.initialize();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        regenerator.disable();
        regenerator = null;
        stopWorkload();
        oresService.cache.clear();
        ores = null;
        oresService = null;
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

        stopWorkload();
        workloadThread = new WorkloadThread();
        workloadTask = Bukkit.getScheduler().runTaskTimer(this, workloadThread, 0, 1);
    }

    @CheckForNull
    public WorkloadThread getWorkloadThread() {
        return workloadThread;
    }

    private void loadExistingChunksToCache() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                try {
                    oresService.addChunkToCache(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
                } catch (Exception exception) {
                    Bukkit.getLogger().severe("Unable to load ores cache for chunk at X: " + chunk.getX() + " Z: " + chunk.getZ() + " in world " + chunk.getWorld().getName());
                    exception.printStackTrace();
                }
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

    public Regenerator getRegenerator() {
        return regenerator;
    }


    private void loadOres() {
        try {
            ores = new OresImpl();
            ores.load();
            //TODO change this
        } catch (Exception e) {
            Bukkit.getLogger().severe("Unable to load ores!");
            e.printStackTrace();
        }
    }

    public OresService getOresService() {
        return oresService;
    }


}
