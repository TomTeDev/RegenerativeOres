package more.mucho.regenerativeores.ores.mining_blocks;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Location;
import org.bukkit.Material;


public class MaterialMiningBlock implements MiningBlock {

    private final Material material;

    public MaterialMiningBlock(Material material) {
        this.material = material;
    }

    @Override
    public void place(Location location) {
        WorkloadThread workloadThread = RegenerativeOres.getPlugin(RegenerativeOres.class).getWorkloadThread();
        assert workloadThread != null;
        workloadThread
                .addWorkload(
                        () -> location.getBlock().setType(material)
                );
    }
}
