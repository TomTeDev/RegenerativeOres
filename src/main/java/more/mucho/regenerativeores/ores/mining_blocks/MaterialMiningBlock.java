package more.mucho.regenerativeores.ores.mining_blocks;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.workloads.WorkloadThread;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;


public class MaterialMiningBlock implements MiningBlock {

    private final Material material;

    public MaterialMiningBlock(Material material) {
        this.material = material;
    }

    @Override
    public ItemStack asItem() {
        return new ItemStack(material);
    }

    @Override
    public boolean matchesType(Block block) {
        return block.getType() == material;
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
    @Override
    public void serialize(ConfigurationSection section) {
        section.set("type", "material");
        section.set("value", material.name());
    }

    public static MiningBlock deserialize(ConfigurationSection section) {
        String materialName = section.getString("value");
        if (materialName == null) {
            throw new IllegalArgumentException("Material value is missing.");
        }
        Material material = Material.getMaterial(materialName.toUpperCase());
        if (material == null) {
            throw new IllegalArgumentException("Invalid material: " + materialName);
        }
        return new MaterialMiningBlock(material);
    }

}
