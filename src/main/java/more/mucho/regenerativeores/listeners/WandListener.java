package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.data.OresService;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.utils.OreItems;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class WandListener implements Listener {
    private final OresService oresService;

    public WandListener(OresService oresService) {
        this.oresService = oresService;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack usedItem = player.getInventory().getItemInMainHand();
        if (!OreItems.isOreWand(usedItem)) return;
        event.setCancelled(true);
        int oreID = OreItems.getWandOreId(usedItem).orElse(-1);
        Optional<Ore> ore = oresService.getOres().getOre(oreID);
        if (ore.isEmpty()) {
            player.sendMessage("This ore does not exist anymore.");
            return;
        }
        Location oreLocation = event.getBlockPlaced().getLocation();
        oresService.addOre(oreLocation, oreID);
        ore.get().regen(oreLocation);
        player.playSound(player, Sound.BLOCK_BAMBOO_PLACE, 1, 1);
    }
}
