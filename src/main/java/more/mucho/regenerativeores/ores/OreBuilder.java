package more.mucho.regenerativeores.ores;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.ores.commands.MiningCommand;
import more.mucho.regenerativeores.ores.drops.MiningDrop;

import more.mucho.regenerativeores.ores.mining_blocks.MaterialMiningBlock;
import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.player_test.PlayerTest;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class OreBuilder {
    private final int id;
    private int delay = -1;
    private MiningBlock material = null;
    private MiningBlock replacement = new MaterialMiningBlock(Material.AIR);
    private PlayerTest permissionTest = null;
    private PlayerTest toolTest = null;
    private List<MiningDrop> drops = new ArrayList<>();
    private List<MiningCommand> commands = new ArrayList<>();
    private MiningMessage message = null;

    public OreBuilder(int id) {
        this.id = id;

    }
    public OreBuilder setDelay(int ticksDelay){
        this.delay = ticksDelay;
        return this;
    }
    public OreBuilder setMaterial(Material material){
        this.material = new MaterialMiningBlock(material);
        return this;
    }
    public OreBuilder setMaterial(MiningBlock material){
        this.material = material;
        return this;
    }
    public OreBuilder setReplacement(Material material){
        this.replacement = new MaterialMiningBlock(material);
        return this;
    }
    public OreBuilder setReplacement(MiningBlock material){
        this.replacement = material;
        return this;
    }

    public OreBuilder withPermissionTest(PlayerTest permissionTest) {
        this.permissionTest = permissionTest;
        return this;
    }

    public OreBuilder withToolTest(PlayerTest toolTest) {
        this.toolTest = toolTest;
        return this;
    }

    public OreBuilder withMiningDrops(List<MiningDrop> drops) {
        this.drops = new ArrayList<>(drops);
        return this;
    }

    public OreBuilder addMiningDrop(MiningDrop drop) {
        this.drops.add(drop);
        return this;
    }

    public OreBuilder withCommands(List<MiningCommand> commands) {
        this.commands = new ArrayList<>(commands);
        return this;
    }

    public OreBuilder addCommand(MiningCommand command) {
        this.commands.add(command);
        return this;
    }

    public OreBuilder withMessage(MiningMessage message) {
        this.message = message;
        return this;
    }




    public List<MiningDrop> getDrops(){
        return this.drops;
    }


    // Build method
    public Ore build() {
        Preconditions.checkArgument(delay>0,"Delay has to be greater than 0");
        Preconditions.checkArgument(material != null,"You have to specify material to mine");


        return new BasicOre(
                id,
                delay,
                material,
                replacement,
                permissionTest,
                toolTest,
                drops,
                message,
                commands
        );
    }
}
