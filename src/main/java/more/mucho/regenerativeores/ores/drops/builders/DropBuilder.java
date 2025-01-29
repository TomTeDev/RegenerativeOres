package more.mucho.regenerativeores.ores.drops.builders;

import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.drops.BaseDrop;
import org.bukkit.Sound;

public abstract class DropBuilder<T extends BaseDrop> {
    private int chance = 100;
    private int minAmount = 1;
    private int maxAmount = 1;
    private boolean isDirect = false;
    private MiningMessage message = null;
    private Sound sound = null;

    public DropBuilder<T> setChance(int chance) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100");
        }
        this.chance = chance;
        return this;
    }

    public int getChance() {
        return this.chance;
    }

    public DropBuilder<T> setMinAmount(int minAmount) {
        if (minAmount < 0) {
            throw new IllegalArgumentException("Min amount must be non-negative");
        }
        this.minAmount = minAmount;
        return this;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public DropBuilder<T> setMaxAmount(int maxAmount) {
        if (maxAmount < 0 || maxAmount < this.minAmount) {
            throw new IllegalArgumentException("Max amount must be non-negative and greater than or equal to min amount");
        }
        this.maxAmount = maxAmount;
        return this;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public DropBuilder<T> setDirect(boolean isDirect) {
        this.isDirect = isDirect;
        return this;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public DropBuilder<T> setMessage(MiningMessage message) {
        this.message = message;
        return this;
    }

    public MiningMessage getMessage() {
        return this.message;
    }

    public DropBuilder<T> setSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public Sound getSound() {
        return sound;
    }


    public abstract T build();

}
