package more.mucho.regenerativeores.ores.commands;

import more.mucho.regenerativeores.utils.Random;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BaseMiningCommand implements MiningCommand{

    private final int chance;
    private String command;
    public BaseMiningCommand(int chance,String command){
        this.chance = chance;
        this.command = command;
    }

    @Override
    public boolean test() {
        return Random.randomInt(100)<chance;
    }

    @Override
    public void execute(Player target) {
        //TODO parseplaceholders;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
    }
    public void serialize(ConfigurationSection section){
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }
        section.set("type","basic");
        section.set("chance",chance);
        section.set("command",command);
    }

    public static BaseMiningCommand deserialize(ConfigurationSection section){
        if(section == null){
            throw new IllegalArgumentException("Section is null");
        }
        String type = section.getString("type");
        if(type==null||!type.equals("basic")){
            throw new IllegalArgumentException("Unknown command type: " + type);
        }
        int chance = section.getInt("chance");
        String command = section.getString("command");
        return new BaseMiningCommand(chance,command);
    }
}
