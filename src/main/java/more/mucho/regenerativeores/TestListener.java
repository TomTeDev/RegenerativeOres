package more.mucho.regenerativeores;

import more.mucho.regenerativeores.data.Ores;
import more.mucho.regenerativeores.data.OresService;
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
import more.mucho.regenerativeores.utils.PlayerProfiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;

import java.util.Arrays;

public class TestListener implements Listener {

    public RegenerativeOres plugin;
    public TestListener(RegenerativeOres plugin){
        this.plugin = plugin;
    }


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
        OresService oresService = plugin.getOresService();

        Ores ores = oresService.getOres();
        int incrementalID = ores.getNextID();
        Bukkit.broadcastMessage("Next ID: " + incrementalID);
        Ore ore = new BasicOre(incrementalID, 5,
                new MaterialMiningBlock(Material.GRASS_BLOCK),
                new PlayerHeadMiningBlock(profile),
                null,
                new BasicToolTest(new ItemStack(Material.DIAMOND_PICKAXE), "Musisz miec diamond pikaks"),
                Arrays.asList(
                        new BaseItemDrop(Range.of(1, 5), 20, false, new BaseMiningMessage(DISPLAY_ACTION.ACTION_BAR, "Dropło!"), Sound.BLOCK_NOTE_BLOCK_PLING, new ItemStack(Material.APPLE)),
                        new BaseExpDrop(Range.of(1, 5), 20, false, new BaseMiningMessage(DISPLAY_ACTION.TITLE_SMALL, "Expisko!"),Sound.BLOCK_NOTE_BLOCK_PLING)

                ), new BaseMiningMessage(DISPLAY_ACTION.CHAT, "Wykopano!"), Arrays.asList(new BaseMiningCommand(5, "give Muchomore APPLE 1"))
        );
        Location location = event.getBlockPlaced().getLocation().clone();
        ore.regen(location);

        try {
            ores.registerOre(ore);
            ores.saveOre(ore);
            oresService.addOre(location,ore.getID());

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


}
