package more.mucho.regenerativeores.ores.drops;

import more.mucho.regenerativeores.events.MineExpEvent;
import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.Range;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

public class BaseExpDrop extends BaseDrop {

    public BaseExpDrop(Range<Integer, Integer> range, int dropChance, boolean isDirect, MiningMessage message) {
        super(range, dropChance, isDirect, message);
    }

    @Override
    public void drop(Player target, Location dropLocation) {
        int exp = getRandomDropAmount();
        MineExpEvent mineExpEvent = new MineExpEvent(target, exp);
        Bukkit.getPluginManager().callEvent(mineExpEvent);
        if (mineExpEvent.isCancelled()) return;
        super.drop(target,dropLocation);
        int finalExp = mineExpEvent.getExp();
        if (isDirect) {
            target.giveExp(finalExp);
        } else {
            //TODO change it to be player specific (no other player can pick it up)
            ExperienceOrb experienceOrb = target.getWorld().spawn(dropLocation, ExperienceOrb.class);
            experienceOrb.setExperience(finalExp);
        }
    }
    public void serialize(ConfigurationSection section){
        if (section == null) throw new IllegalArgumentException("Section is null");
        super.serialize(section);
        section.set("type","expDrop");
    }


    public static BaseExpDrop deserialize(ConfigurationSection section) {
        BaseDrop baseDrop = BaseDrop.deserializeBaseDrop(section);  // Deserialize common fields

        return new BaseExpDrop(baseDrop.range, baseDrop.dropChance, baseDrop.isDirect, baseDrop.message);
    }


}
