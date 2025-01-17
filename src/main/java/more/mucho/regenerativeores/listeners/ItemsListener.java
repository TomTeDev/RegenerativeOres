package more.mucho.regenerativeores.listeners;

import more.mucho.regenerativeores.data.OresService;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.utils.Colors;
import more.mucho.regenerativeores.utils.OreItems;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemsListener implements Listener {
    private final OresService oresService;

    public ItemsListener(OresService oresService) {
        this.oresService = oresService;
    }

    @EventHandler
    public void onWandUse(BlockPlaceEvent event) {
        //TODO add permissions
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack usedItem = player.getInventory().getItemInMainHand();
        if (!OreItems.isOreWand(usedItem)) return;
        event.setCancelled(true);
        int oreID = OreItems.getOreIdOfWand(usedItem).orElse(-1);
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


    @EventHandler
    public void onEditorUse(PlayerInteractEvent event) {
        //TODO add permissions
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        if (!OreItems.isOresEditor(player.getInventory().getItemInMainHand())) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            removeOre(player, clickedBlock);
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            editOre(player, clickedBlock);
            return;
        }

    }

    private void removeOre(Player player, Block clickedBlock) {
        oresService.cache.getOreAt(clickedBlock.getLocation()).ifPresentOrElse(ore -> {
                    oresService.removeOre(clickedBlock.getLocation(), ore);
                    player.sendMessage(Colors.YELLOW_INFO + "Removed ore with id: " + ore + " at " + clickedBlock.getX() + "," + clickedBlock.getY() + "," + clickedBlock.getZ());
                },
                () -> player.sendMessage(Colors.RED_INFO + "Thats not ore"));
    }

    private void editOre(Player player, Block clickedBlock) {
        oresService.cache.getOreAt(clickedBlock.getLocation()).ifPresentOrElse(ore -> {
                    //TODO open editor;
                },
                () -> player.sendMessage(Colors.RED_INFO + "Thats not ore"));
    }


}
