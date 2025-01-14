package more.mucho.regenerativeores.ores.mining_blocks;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.utils.PlayerProfiles;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.profile.PlayerProfile;

public class PlayerHeadMiningBlock implements MiningBlock {

    private final PlayerProfile headProfile;

    public PlayerHeadMiningBlock(PlayerProfile headProfile) {
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

                            BlockState blockState = block.getState();
                            Skull skull = (Skull) blockState;
                            skull.setOwnerProfile(headProfile);


                            Bukkit.getScheduler().runTaskLater(RegenerativeOres.getPlugin(RegenerativeOres.class), () -> {
                                skull.update(true, false);
                            }, 1);
                            //TODO figure out a way to place that block in one tick;

                        }
                );
    }

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("type", "player_head");
        section.set("profile", getTextureLink());
    }


    public static MiningBlock deserialize(ConfigurationSection section) {
        String textureLink = section.getString("profile");
        if (textureLink == null) {
            throw new IllegalArgumentException("Head texture link is missing.");
        }
        PlayerProfile profile = null;
        try {
            profile = PlayerProfiles.get(textureLink);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (profile == null) {
            throw new IllegalArgumentException("Invalid head texture link: " + textureLink);
        }
        return new PlayerHeadMiningBlock(profile);
    }

    private String getTextureLink() {
        if (headProfile.getTextures().getSkin() == null) {
            return "http://textures.minecraft.net/texture/51a78c8f382d3941f45a824300adb87b6f3e8ed3f3a46da2a989066c1b7059e0";
        }
        return headProfile.getTextures().getSkin().toString();
    }
}
