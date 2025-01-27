package more.mucho.regenerativeores.ores.drops.builders;

import more.mucho.regenerativeores.ores.MiningMessage;
import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.drops.BaseDrop;
import org.bukkit.Sound;

public class DropBuilder {
    private int chance, minAmount, maxAmount;
    private boolean isDirect;
    private MiningMessage message;
    private Sound sound;

    public DropBuilder setChance(int chance){
        this.chance = chance;
        return this;
    }
    public int getChance(){
        return this.chance;
    }

    public DropBuilder setMinAmount(int minAmount){
        this.minAmount = minAmount;
        return this;
    }

    public DropBuilder setMaxAmount(int maxAmount){
        this.maxAmount = maxAmount;
        return this;
    }

    public DropBuilder setDirect(boolean isDirect){
        this.isDirect = isDirect;
        return this;
    }

    public DropBuilder setMessage(MiningMessage message){
        this.message = message;
        return this;
    }

    public DropBuilder setSound(Sound sound){
        this.sound = sound;
        return this;
    }

    public Sound getSound(){
        return sound;
    }


}
