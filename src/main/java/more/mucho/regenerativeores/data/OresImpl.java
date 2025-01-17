package more.mucho.regenerativeores.data;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.RegenerativeOres;
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

public class OresImpl implements Ores {
    private final ConfigHandler configHandler;
    private final FileConfiguration cfg;
    private final Map<Integer, Ore> ores = new HashMap<>(); //<id, ore>
    private int incrementalID = 0;


    public OresImpl() throws Exception {
        configHandler = new ConfigHandler(RegenerativeOres.getPlugin(RegenerativeOres.class), "ores.yml");
        cfg = configHandler.getConfig();
    }

    public void registerOre(Ore ore) {
        Preconditions.checkArgument(!ores.containsKey(ore.getID()),
                "Ore with ID " + ore.getID() + " already registered. Use #getNextID() to obtain the next ID.");
        ores.put(ore.getID(), ore);
    }

    public List<Ore> getOres() {
        return new ArrayList<>(ores.values());
    }

    public int getNextID() {
        return incrementalID++;
    }

    @Override
    public boolean deleteOre(int oreID) throws Exception {
        deleteOreFromFile(oreID);
        ores.remove(oreID);
        // TODO: Remove from cache (if applicable)
        return true;
    }

    private void deleteOreFromFile(int id) throws Exception {
        cfg.set("ores." + id, null);
        configHandler.saveConfig(cfg);
    }

    public Optional<Ore> getOre(int id) {
        return Optional.ofNullable(ores.get(id));
    }

    public void load() throws Exception {

        incrementalID = cfg.getInt("incremental_id", 0);
        ConfigurationSection oresSection = cfg.getConfigurationSection("ores");

        if (oresSection == null || oresSection.getKeys(false).isEmpty()) {
            return;
        }

        int oresLoaded = 0;
        for (String key : oresSection.getKeys(false)) {
            ConfigurationSection oreSection = oresSection.getConfigurationSection(key);

            if (oreSection != null) {
                try {
                    int id = oreSection.getInt("id");
                    int delay = oreSection.getInt("delay");
                    MiningBlock material = MiningBlockFactory.fromConfig(oreSection.getConfigurationSection("material"));
                    MiningBlock replacement = MiningBlockFactory.fromConfig(oreSection.getConfigurationSection("replacement"));

                    OreBuilder builder = new OreBuilder(id, delay, material, replacement);
                    if(oreSection.getConfigurationSection("permissionTest") != null) {
                        builder.withPermissionTest(PlayerTestsFactory.fromConfig(oreSection.getConfigurationSection("permissionTest")));
                    }
                    if(oreSection.getConfigurationSection("toolTest") != null) {
                        builder.withToolTest(PlayerTestsFactory.fromConfig(oreSection.getConfigurationSection("toolTest")));
                    }
                    if(oreSection.getConfigurationSection("drops") != null) {
                        builder.withMiningDrops(DropsFactory.dropsFromConfig(oreSection.getConfigurationSection("drops")));
                    }
                    if(oreSection.getConfigurationSection("commands") != null) {
                        builder.withCommands(CommandsFactory.commandsFromConfig(oreSection.getConfigurationSection("commands")));
                    }
                    if(oreSection.getConfigurationSection("message") != null) {
                        builder.withMessage(MessagesFactory.deserialize(oreSection.getConfigurationSection("message")));
                    }
                    Ore ore = builder.build();

                    registerOre(ore);
                    oresLoaded++;
                } catch (Exception e) {
                    Bukkit.getLogger().warning("Failed to load ore: " + key);
                    e.printStackTrace();
                }
            }
        }

        Bukkit.getLogger().info(oresLoaded + " ores loaded.");
    }

    public void saveOre(Ore ore) throws Exception {
        ConfigurationSection section = cfg.createSection("ores." + ore.getID());
        ore.serialize(section);
        if(cfg.getInt("incremental_id") <incrementalID) {
            cfg.set("incremental_id", incrementalID);
        }
        configHandler.saveConfig(cfg);
    }
}
