package more.mucho.regenerativeores.ores.mining_blocks;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.profile.PlayerProfile;

public class PlayerHeadMiningBlock implements MiningBlock {

    private final PlayerProfile headProfile;
    public PlayerHeadMiningBlock (PlayerProfile headProfile) {
        this.headProfile = headProfile;
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
                            block.getState().update();
                            //TODO figure out a way to place that block in one tick;
                            Bukkit.getScheduler().runTaskLater(RegenerativeOres.getPlugin(RegenerativeOres.class), () -> {
                                BlockState state = location.getBlock().getState();
                                Skull skull = (Skull) state;
                                skull.setOwnerProfile(headProfile);
                                skull.update();
                            },1);
                        }
                );
    }
}
