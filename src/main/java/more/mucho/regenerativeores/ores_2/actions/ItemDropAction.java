package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import more.mucho.regenerativeores.utils.NumberRange;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public class ItemDropAction extends BaseBreakAction {
    private final ItemStack itemTemplate;
    private final NumberRange amountRange;
    private final boolean directToInventory;

    public ItemDropAction(UUID uuid, int chance, Collection<Requirement> requirements,
                          ItemStack item, NumberRange amountRange, boolean directToInventory) {
        super(uuid, chance, requirements);
        this.itemTemplate = item.clone();
        this.amountRange = amountRange;
        this.directToInventory = directToInventory;
    }

    @Override
    protected Player extractPlayer(BlockBreakEvent event) {
        return event.getPlayer();
    }

    @Override
    protected void onAction(BlockBreakEvent event) {
        createItem().ifPresent(item -> {
            if (directToInventory) {
                addToInventory(event.getPlayer(), item);
            } else {
                event.getBlock().getDrops().add(item);
            }
        });
    }

    private Optional<ItemStack> createItem() {
        int amount = (int) amountRange.roll();
        if (amount <= 0) return Optional.empty();

        ItemStack result = itemTemplate.clone();
        result.setAmount(Math.min(amount, result.getMaxStackSize()));
        return Optional.of(result);
    }

    private void addToInventory(Player player, ItemStack item) {
        Map<Integer, ItemStack> leftover = player.getInventory().addItem(item);
        leftover.values().forEach(remaining ->
                player.getWorld().dropItem(player.getLocation(), remaining)
        );
    }


}

