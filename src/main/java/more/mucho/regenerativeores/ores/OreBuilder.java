package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.ores.commands.MiningCommand;
import more.mucho.regenerativeores.ores.drops.MiningDrop;

import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.player_test.PlayerTest;

import java.util.ArrayList;
import java.util.List;

public class OreBuilder {
    private int id;
    private final int delay;
    private final MiningBlock material;
    private final MiningBlock replacement;
    private PlayerTest permissionTest = null;
    private PlayerTest toolTest = null;
    private List<MiningDrop> drops = new ArrayList<>();
    private List<MiningCommand> commands = new ArrayList<>();
    private MiningMessage message = null;


    // Required fields
    public OreBuilder(int id, int delay, MiningBlock material, MiningBlock replacement) {
        this.id = id;
        this.delay = delay;
        this.material = material;
        this.replacement = replacement;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Optional fields
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



    // Build method
    public Ore build() {
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
