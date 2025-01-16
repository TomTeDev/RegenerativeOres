package more.mucho.regenerativeores;

import more.mucho.regenerativeores.commands.BasicMessagesHandler;
import more.mucho.regenerativeores.commands.CustomCommand;
import more.mucho.regenerativeores.commands.OresCommand;
import more.mucho.regenerativeores.data.OresCacheImpl;
import more.mucho.regenerativeores.items.LoreNodeRegistry;
import more.mucho.regenerativeores.listeners.BlockBreakListener;
import more.mucho.regenerativeores.listeners.GuiListener;
import more.mucho.regenerativeores.listeners.WandListener;
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
    private OresImpl ores = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadOres();
        startWorkload();
        setupOresCache();
        registerListeners(
                new GuiListener(),
                new BlockBreakListener(OresCacheImpl.i()),
                new TestListener(),
                new WandListener(this)
        );
        registerCommands(
                new OresCommand(new BasicMessagesHandler(), "ores")
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
        OresCacheImpl.i().clear();
        ores = null;
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

    private void setupOresCache() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                try {
                    OresCacheImpl.i().loadChunkOresAsync(world, chunk.getX(), chunk.getZ());
                }catch (Exception exception){
                    Bukkit.getLogger().severe("Unable to load ores cache for chunk at X: "+chunk.getX()+" Z: "+chunk.getZ()+" in world "+chunk.getWorld().getName());
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

    public Ores getOres() {
        return ores;
    }

    private void loadOres() {
        try {
            ores = new OresImpl();
            ores.load();
            //TODO change this
        }catch (Exception e){
            Bukkit.getLogger().severe("Unable to load ores!");
            e.printStackTrace();
        }
    }


}
