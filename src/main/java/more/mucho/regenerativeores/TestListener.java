package more.mucho.regenerativeores;

import more.mucho.regenerativeores.data.OresCache;
import more.mucho.regenerativeores.ores.BasicOre;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.commands.BaseMiningCommand;
import more.mucho.regenerativeores.ores.drops.BaseExpDrop;
import more.mucho.regenerativeores.ores.drops.BaseItemDrop;
import more.mucho.regenerativeores.ores.messages.BaseMiningMessage;
import more.mucho.regenerativeores.ores.messages.DISPLAY_ACTION;
import more.mucho.regenerativeores.ores.mining_blocks.MaterialMiningBlock;
import more.mucho.regenerativeores.ores.mining_blocks.PlayerHeadMiningBlock;
import more.mucho.regenerativeores.ores.player_test.BasicToolTest;
import more.mucho.regenerativeores.utils.ConfigHandler;
import more.mucho.regenerativeores.utils.PlayerProfiles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;

import java.util.Arrays;

public class TestListener implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.GRANITE) return;
        event.setCancelled(true);
        PlayerProfile profile = null;
        try {
            profile = PlayerProfiles.get("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFhNzhjOGYzODJkMzk0MWY0NWE4MjQzMDBhZGI4N2I2ZjNlOGVkM2YzYTQ2ZGEyYTk4OTA2NmMxYjcwNTllMCJ9fX0=");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        assert profile != null;


        Ore ore = new BasicOre(1, 5, new MaterialMiningBlock(Material.GRASS_BLOCK), new PlayerHeadMiningBlock(profile),
                null, new BasicToolTest(new ItemStack(Material.DIAMOND_PICKAXE), "Musisz miec diamond pikaks"),
                Arrays.asList(
                        new BaseItemDrop(Range.of(1, 5), 50, true, new BaseMiningMessage(DISPLAY_ACTION.ACTION_BAR, "Dropło!"), new ItemStack(Material.APPLE), Sound.AMBIENT_CAVE, null),
                        new BaseExpDrop(Range.of(1, 5), 50, true, new BaseMiningMessage(DISPLAY_ACTION.ACTION_BAR, "Expisko!"))

                ), new BaseMiningMessage(DISPLAY_ACTION.ACTION_BAR, "Wykopano!"), Arrays.asList(new BaseMiningCommand(50, "give Muchomore APPLE 1"))
        );
        saveOre(ore);
        Location location = event.getBlockPlaced().getLocation().clone();
        ore.regen(location);
        OresCache.i().addOre(location, ore);
        RegenerativeOres.getPlugin(RegenerativeOres.class).getOres().registerOre(ore);
    }

    private void saveOre(Ore ore){
        try {
            ConfigHandler configHandler = new ConfigHandler(RegenerativeOres.getPlugin(RegenerativeOres.class),"ores.yml");
            FileConfiguration cfg = configHandler.getConfig();
            ConfigurationSection section = cfg.createSection("ores."+ore.getID());
            ore.serialize(section);
            configHandler.saveConfig(cfg);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
