package more.mucho.regenerativeores.ores;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface MiningDrop {
    boolean testDrop();
    int getDropChance();
    boolean isDirectInventoryDrop();
    ItemStack getDropType();
    Optional<Sound> getSound();
    Optional<OreParticle> getParticle();
    Optional<MiningMessage> getDropMessage();
    void drop(Player player, Location dropLocation);
}
