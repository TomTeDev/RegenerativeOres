package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.events.OreMineEvent;
import more.mucho.regenerativeores.ores.commands.MiningCommand;
import more.mucho.regenerativeores.ores.drops.MiningDrop;
import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores.player_test.PlayerTest;
import more.mucho.regenerativeores.ores.variants.PermissionTestable;
import more.mucho.regenerativeores.ores.variants.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicOre implements Ore, PermissionTestable, ToolTestable, Droppable, MessageSupport,CommandExecutable {
    private final int id;
    private final int delay;
    private final MiningBlock material;
    private final MiningBlock replacement;
    private final PlayerTest permissionTest;
    private final PlayerTest toolTest;

    private final List<MiningDrop> drops = new ArrayList<>();
    private final MiningMessage message;
    private final List<MiningCommand> commands = new ArrayList<>();


    public BasicOre(int id, int delay, MiningBlock material, MiningBlock replacement,
                    PlayerTest permissionTest, PlayerTest toolTest,
                    List<MiningDrop> drops, MiningMessage message,List<MiningCommand> commands) {
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
    public boolean mine(Player miner,Location location) {
        if(!material.matchesType(location.getBlock()))return false;
        OreMineEvent event = new OreMineEvent(miner,this,location);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled())return false;
        // Mining logic
        if(permissionTest!=null&&!permissionTest.test(miner)){
            miner.sendMessage(permissionTest.getDenyMessage());
            return false;
        }
        if(toolTest!=null&&!toolTest.test(miner)){
            miner.sendMessage(toolTest.getDenyMessage());
            return false;
        }
        Location dropLocation = location.clone().add(0.5,0.5,0.5);
        for(MiningDrop miningDrop : drops){
            if(!miningDrop.testDrop())continue;
            miningDrop.drop(miner,dropLocation);
        }
        for(MiningCommand miningCommand : commands){
            if(!miningCommand.test())continue;
            miningCommand.execute(miner);
        }
        if(message!=null){
            message.send(miner);
        }

        replace(location);

        RegenerativeOres.getPlugin(RegenerativeOres.class).getRegenerator().scheduleRegen(this,location);
        return true;
    }

    @Override
    public void regen(Location location) {
        material.place(location);
    }
    @Override
    public void replace(Location location){
        replacement.place(location);
    }
    @Override
    public boolean serialize(ConfigurationSection section) {
        if (section == null) {
            return false;
        }

        try {
            // Basic properties
            section.set("id", id);
            section.set("delay", delay);

            // Serialize MiningBlock material and replacement
            ConfigurationSection materialSection = section.createSection("material");
            material.serialize(materialSection);

            ConfigurationSection replacementSection = section.createSection("replacement");
            replacement.serialize(replacementSection);

            // Serialize optional permissionTest and toolTest
            if (permissionTest != null) {
                ConfigurationSection permissionSection = section.createSection("permissionTest");
                permissionTest.serialize(permissionSection);
            }

            if (toolTest != null) {
                ConfigurationSection toolSection = section.createSection("toolTest");
                toolTest.serialize(toolSection);
            }

            // Serialize drops
            if (!drops.isEmpty()) {
                ConfigurationSection dropsSection = section.createSection("drops");
                for (int i = 0; i < drops.size(); i++) {
                    MiningDrop drop = drops.get(i);
                    ConfigurationSection dropSection = dropsSection.createSection("drop" + i);
                    drop.serialize(dropSection);
                }
            }

            // Serialize commands
            if (!commands.isEmpty()) {
                ConfigurationSection commandsSection = section.createSection("commands");
                for (int i = 0; i < commands.size(); i++) {
                    MiningCommand command = commands.get(i);
                    ConfigurationSection commandSection = commandsSection.createSection("command" + i);
                    command.serialize(commandSection);
                }
            }

            // Serialize message
            if (message != null) {
                ConfigurationSection messageSection = section.createSection("message");
                message.serialize(messageSection);
            }

            return true;
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to serialize BasicOre: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
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

}
