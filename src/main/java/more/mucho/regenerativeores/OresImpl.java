package more.mucho.regenerativeores;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.data.OresCache;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.ores.OreBuilder;
import more.mucho.regenerativeores.ores.commands.CommandsFactory;
import more.mucho.regenerativeores.ores.drops.DropsFactory;
import more.mucho.regenerativeores.ores.messages.MessagesFactory;
import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.mining_blocks.MiningBlockFactory;
import more.mucho.regenerativeores.ores.player_test.PlayerTestsFactory;
import more.mucho.regenerativeores.utils.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class OresImpl implements Ores{
    private final HashMap<Integer, Ore> ores = new HashMap<>(); //<id,ore>
    private int incrementalID = 0;
    public void registerOre(Ore ore){
        Preconditions.checkArgument(!ores.containsKey(ore.getID()),"Ore with ID "+ore.getID()+" already registered, use #Ores.getNextID() to obtain next ID");
        ores.put(ore.getID(),ore);
    }
    public ArrayList<Ore> getOres(){
        return new ArrayList<>(ores.values());
    }
    public Optional<Integer> getNextID(){
        incrementalID++;
        try {
            updateIDInConfig();
        }catch (Exception exception){
            incrementalID--;
            exception.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(incrementalID);
    }
    private void updateIDInConfig()throws  Exception{
        ConfigHandler configHandler = new ConfigHandler(RegenerativeOres.getPlugin(RegenerativeOres.class),"ores.yml");
        FileConfiguration cfg = configHandler.getConfig();
        cfg.set("incremental_id",incrementalID);
        configHandler.saveConfig(cfg);
    }

    @Override
    public boolean deleteOre(int oreID) throws Exception {
        //remove from config;
        deleteOreFromFile(oreID);
        //remove from map
        ores.remove(oreID);
        //remove from cache
        OresCache.i().jakusunacoreidztychwszystkichtychtakich?
        remove from cache;
        remove from map;
        return false;
    }
    private void deleteOreFromFile(int id)throws Exception{
        ConfigHandler configHandler = new ConfigHandler(RegenerativeOres.getPlugin(RegenerativeOres.class),"ores.yml");
        FileConfiguration cfg = configHandler.getConfig();
        cfg.set("ores."+id,null);
        configHandler.saveConfig(cfg);
    }

    public Optional<Ore> getOre(int id){
        return Optional.ofNullable(ores.get(id));
    }
    public void load()throws Exception{
        FileConfiguration cfg = new ConfigHandler(RegenerativeOres.getPlugin(RegenerativeOres.class),"ores.yml").getConfig();
        incrementalID = cfg.getInt("incremental_id");
        ConfigurationSection oresSection = cfg.getConfigurationSection("ores");
        if(oresSection == null||oresSection.getKeys(false).isEmpty())return;
        int oresLoaded = 0;
        for (String key : oresSection.getKeys(false)) {
            ConfigurationSection oreSection = oresSection.getConfigurationSection(key);
            if (oreSection != null) {
                try {
                    // Extract properties from the configuration
                    int id = oreSection.getInt("id");
                    int delay = oreSection.getInt("delay");
                    MiningBlock material = MiningBlockFactory.fromConfig(oreSection.getConfigurationSection("material"));
                    MiningBlock replacement = MiningBlockFactory.fromConfig(oreSection.getConfigurationSection("replacement"));

                    Ore ore = new OreBuilder(id, delay, material, replacement)
                            .withPermissionTest(PlayerTestsFactory.fromConfig(oreSection.getConfigurationSection("permissionTest")))
                            .withToolTest(PlayerTestsFactory.fromConfig(oreSection.getConfigurationSection("toolTest")))
                            .withMiningDrops( DropsFactory.dropsFromConfig(oreSection.getConfigurationSection("drops")))
                            .withCommands( CommandsFactory.commandsFromConfig(oreSection.getConfigurationSection("commands")))
                            .withMessage(MessagesFactory.deserialize(oreSection.getConfigurationSection("message")))
                            .build();

                    registerOre(ore);
                    oresLoaded++;
                } catch (Exception e) {
                    Bukkit.getLogger().warning("Failed to load ore: " + key);
                }
            }
        }

        Bukkit.getLogger().info(oresLoaded + " ores loaded.");
    }

    /**
     * Saves ore to config
     */
    public void saveOre(Ore ore) throws Exception{
ewq
    }
}
