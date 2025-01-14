package more.mucho.regenerativeores.ores.drops;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.messages.MessagesFactory;
import more.mucho.regenerativeores.utils.Random;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public abstract class BaseDrop implements MiningDrop {
    protected final Range<Integer, Integer> range;
    protected final int dropChance;
    protected final boolean isDirect;
    @Nullable
    protected final MiningMessage message;

    public BaseDrop(Range<Integer, Integer> range, int dropChance, boolean isDirect, MiningMessage message) {
        Preconditions.checkArgument(range.min <= range.max, "minAmount must be less than maxAmount");
        Preconditions.checkArgument(range.min > 0, "minAmount must be greater than 0");

        this.range = range;
        this.dropChance = dropChance;
        this.isDirect = isDirect;
        this.message = message;
    }

    public boolean testDrop() {
        return Random.randomInt(100) < dropChance;
    }
    public void sendMessage(Player target) {
        if (message == null) return;
        message.send(target);
    }
    public int getRandomDropAmount() {
        return Random.randomInt(range.min, range.max);
    }
    public void serialize(ConfigurationSection section){
        if (section == null) throw new IllegalArgumentException("Section is null");
        section.set("rangeMin", range.min);
        section.set("rangeMax", range.max);
        section.set("dropChance", dropChance);
        section.set("isDirect", isDirect);
        if (message != null){
            ConfigurationSection messageSection = section.createSection("message");
            message.serialize(messageSection);
        }

    }
    public static BaseDrop deserializeBaseDrop(ConfigurationSection section) {
        int rangeMin = section.getInt("rangeMin");
        int rangeMax = section.getInt("rangeMax");
        Range<Integer, Integer> range = new Range<>(rangeMin, rangeMax);

        int dropChance = section.getInt("dropChance");
        boolean isDirect = section.getBoolean("isDirect");

        MiningMessage message = null;
        if (section.isConfigurationSection("message")) {
            ConfigurationSection messageSection = section.getConfigurationSection("message");
            if (messageSection != null) {
                message = MessagesFactory.deserialize(messageSection);
            }
        }

        return new BaseDrop(range, dropChance, isDirect, message) {

            @Override
            public boolean testDrop() {
                return false;
            }

            @Override
            public void drop(Player player, Location dropLocation) {

            }
        };
    }
}
