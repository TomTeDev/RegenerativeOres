package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.variants.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class BasicOre implements Ore, PermissionTestable, ToolTestable, Droppable, MessageSupport,CommandExecutable, ExperienceProvider {
    private final int id;
    private final int delay;
    private final MiningBlock material;
    private final MiningBlock replacement;
    private final PlayerTest permissionTest;
    private final PlayerTest toolTest;
    private final List<MiningDrop> drops;
    private final MiningMessage message;
    private final List<MiningCommand> commands;
    private final MiningExp miningExp;

    public BasicOre(int id, int delay, MiningBlock material, MiningBlock replacement,
                    PlayerTest permissionTest, PlayerTest toolTest,
                    List<MiningDrop> drops, MiningMessage message,List<MiningCommand> commands,MiningExp miningExp) {
        this.id = id;
        this.delay = delay;
        this.material = material;
        this.replacement = replacement;
        this.permissionTest = permissionTest;
        this.toolTest = toolTest;
        this.drops = drops;
        this.message = message;
        this.commands = commands;
        this.miningExp = miningExp;
    }

    @Override
    public int getID() { return id; }

    @Override
    public int getDelay() { return delay; }

    @Override
    public MiningBlock getMaterial() { return material; }

    @Override
    public MiningBlock getReplacement() { return replacement; }

    @Override
    public void mine(Player miner) {
        // Mining logic
    }

    @Override
    public void regen() {
        // Regeneration logic
    }

    @Override
    public boolean serialize(ConfigurationSection section) {
        // Serialization logic
        return true;
    }

    @Override
    public Optional<PlayerTest> getPermissionTest() { return Optional.ofNullable(permissionTest); }

    @Override
    public Optional<PlayerTest> getToolTest() { return Optional.ofNullable(toolTest); }

    @Override
    public List<MiningDrop> getMiningDrops() { return drops; }

    @Override
    public Optional<MiningMessage> getMessage() { return Optional.ofNullable(message); }


    @Override
    public List<MiningCommand> getCommands() {
        return commands;
    }

    @Override
    public Optional<MiningExp> getMiningExp() {
        return Optional.ofNullable(miningExp);
    }
}
