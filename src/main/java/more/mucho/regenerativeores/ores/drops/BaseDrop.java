package more.mucho.regenerativeores.ores.drops;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.messages.MessagesFactory;
import more.mucho.regenerativeores.utils.Random;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public abstract class BaseDrop implements MiningDrop {
    protected final Range<Integer, Integer> range;
    protected final int dropChance;
    protected final boolean isDirect;
    protected final MiningMessage message;
    protected final Sound sound;


    public BaseDrop(Range<Integer, Integer> range, int dropChance, boolean isDirect, @Nullable MiningMessage message, @Nullable Sound sound) {
        Preconditions.checkArgument(range.min <= range.max, "minAmount must be less than maxAmount");
        Preconditions.checkArgument(range.min > 0, "minAmount must be greater than 0");

        this.range = range;
        this.dropChance = dropChance;
        this.isDirect = isDirect;
        this.sound = sound;
        this.message = message;
    }

    public boolean testDrop() {
        return Random.randomInt(100) < dropChance;
    }

    @Override
    public void drop(Player target, Location dropLocation) {
        if (message != null) {
            message.send(target);
        }
    }

    public void playSound(Player player) {
        if (sound == null) return;
        player.playSound(player, sound, 1, 1);
    }

    public void sendMessage(Player target) {
        if (message == null) return;
        message.send(target);
    }

    public int getRandomDropAmount() {
        return Random.randomInt(range.min, range.max);
    }

    public Range<Integer, Integer> getRange() {
        return range;
    }

    public int getDropChance() {
        return dropChance;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public MiningMessage getMessage() {
        return message;
    }

    public void serialize(ConfigurationSection section) {
        if (section == null) throw new IllegalArgumentException("Section is null");
        section.set("rangeMin", range.min);
        section.set("rangeMax", range.max);
        section.set("dropChance", dropChance);
        section.set("isDirect", isDirect);
        if (sound != null) {
            section.set("sound", sound.name());
        }
        if (message != null) {
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

        Sound sound = null;
        if (section.getString("sound") != null) {
            sound = Sound.valueOf(section.getString("sound"));
        }
        return new BaseDrop(range, dropChance, isDirect, message,sound) {

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
