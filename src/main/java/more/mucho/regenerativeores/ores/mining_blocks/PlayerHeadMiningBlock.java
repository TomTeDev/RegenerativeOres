package more.mucho.regenerativeores.ores.mining_blocks;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class PlayerHeadMiningBlock implements MiningBlock {

    private String texture;
    public PlayerHeadMiningBlock (String texture) {
        this.texture = texture;
    }
    @Override
    public void place(Location location) {
        WorkloadThread workloadThread = RegenerativeOres.getPlugin(RegenerativeOres.class).getWorkloadThread();
        assert workloadThread != null;
        workloadThread
                .addWorkload(
                        () -> {
                            Block block = location.getBlock();
                            block.setType(Material.PLAYER_HEAD);
                            BlockState state = block.getState();
                            state.getBlockData()
                            block.setSkullType(texture);
                        }
                );
    }}
}
