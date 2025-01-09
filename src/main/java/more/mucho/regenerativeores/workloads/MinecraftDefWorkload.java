package more.mucho.regenerativeores.workloads;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

public record MinecraftDefWorkload(Material material, Location location) implements Workload{

    @Override
    public void compute() {
        BlockState state = location.getWorld().getBlockState(location);
        state.setType(material);
        state.update();
    }
}
