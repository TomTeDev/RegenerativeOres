package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.events.OreMineEvent;
import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.variants.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicOre implements Ore, PermissionTestable, ToolTestable, Droppable, MessageSupport,CommandExecutable, ExperienceProvider {
    private final int id;
    private final int delay;
    private final MiningBlock material;
    private final MiningBlock replacement;
    private final PlayerTest permissionTest;
    private final PlayerTest toolTest;

    private final List<MiningDrop> drops = new ArrayList<>();
    private final MiningMessage message;
    private final List<MiningCommand> commands = new ArrayList<>();
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
        if(drops!=null){
            this.drops.addAll(drops);
        }
        this.message = message;
        if (commands!=null){
            this.commands.addAll(commands);
        }

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
    public void mine(Player miner,Location location) {
        Bukkit.broadcastMessage("Mine");
        OreMineEvent event = new OreMineEvent(miner,this,location);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled())return;

        Bukkit.broadcastMessage("Mined");
        // Mining logic
        if(permissionTest!=null&&permissionTest.test(miner)){
            miner.sendMessage(permissionTest.getDenyMessage());
        }
        if(toolTest!=null&&toolTest.test(miner)){
            miner.sendMessage(toolTest.getDenyMessage());
        }
        for(MiningDrop miningDrop : drops){
            if(!miningDrop.testDrop())continue;
            miningDrop.drop(miner,location);
        }
        for(MiningCommand miningCommand : commands){
            if(!miningCommand.test())continue;
            miningCommand.execute(miner);
        }
        if(message!=null){
            message.send(miner);
        }
        if(miningExp!=null&&miningExp.testDrop()){
            miningExp.addExp(miner,location);
        }

        replace(location);

        RegenerativeOres.getPlugin(RegenerativeOres.class).getRegenerator().scheduleRegen(this,location);
    }

    @Override
    public void regen(Location location) {
        material.place(location);
    }
    @Override
    public void replace(Location location){
        Bukkit.broadcastMessage("Replace");
        replacement.place(location);
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
