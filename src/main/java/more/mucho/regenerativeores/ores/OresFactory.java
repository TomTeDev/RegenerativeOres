package more.mucho.regenerativeores.ores;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.events.MineExpEvent;
import more.mucho.regenerativeores.utils.Random;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class OresFactory {

    public static MiningExp craftMiningExp(Range<Integer, Integer> range, int dropChance, boolean isDirect) {
        return new MiningExpImpl(range, dropChance, isDirect);
    }

    public MiningDrop craftMiningDrop(Range<Integer, Integer> amountRange, ItemStack drop, int chance, @Nullable Sound sound, @Nullable MiningMessage message) {
        return new MiningDropImpl(amountRange, drop, chance, sound, message,null);
    }



    private static class MiningExpImpl implements MiningExp {
        private final Range<Integer, Integer> rage;
        private final int dropChance;
        private final boolean isDirectInventoryDrop;

        public MiningExpImpl(Range<Integer, Integer> range, int dropChance, boolean isDirectInventoryDrop) {
            this.rage = range;
            this.dropChance = dropChance;
            this.isDirectInventoryDrop = isDirectInventoryDrop;
        }

        @Override
        public Range<Integer, Integer> getRage() {
            return rage;
        }

        @Override
        public int getDropChance() {
            return dropChance;
        }

        @Override
        public boolean testDrop() {
            return Random.randomInt(100) < dropChance;
        }

        @Override
        public boolean isDirectInventoryDrop() {
            return isDirectInventoryDrop;
        }

        private int getExp() {
            return Random.randomInt(rage.getMin(), rage.getMax());
        }

        @Override
        public void addExp(Player target, Location location) {
            int exp = getExp();
            MineExpEvent mineExpEvent = new MineExpEvent(target, exp);
            Bukkit.getPluginManager().callEvent(mineExpEvent);
            if (mineExpEvent.isCancelled()) return;
            int finalExp = mineExpEvent.getExp();
            if (isDirectInventoryDrop) {
                target.giveExp(finalExp);
            } else {
                //TODO change it to be player specific (no other player can pick it up)
                ExperienceOrb experienceOrb = target.getWorld().spawn(location, ExperienceOrb.class);
                experienceOrb.setExperience(finalExp);
            }
        }
    }

    private static class MiningDropImpl implements MiningDrop {
        private final Range<Integer, Integer> amountRange;
        private final int chance;
        private final ItemStack item;
        private final Sound dropSound;
        private final MiningMessage miningMessage;
        private final OreParticle particle;

        public MiningDropImpl(Range<Integer, Integer> amountRange, ItemStack item, int chance, Sound dropSound, MiningMessage miningMessage,OreParticle particle) {
            Preconditions.checkArgument(amountRange.min <= amountRange.max, "minAmount must be less than maxAmount");
            Preconditions.checkArgument(amountRange.min > 0, "minAmount must be greater than 0");
            Preconditions.checkArgument(amountRange.max <= item.getMaxStackSize(), "maxAmount must be less than or equal to item max stack size");
            this.amountRange = amountRange;
            this.item = item.clone();
            this.chance = chance;
            this.dropSound = dropSound;
            this.miningMessage = miningMessage;
            this.particle = particle;
        }

        public boolean testDrop() {
            return Random.randomInt(100) < chance;
        }

        @Override
        public int getDropChance() {
            return chance;
        }

        @Override
        public boolean isDirectInventoryDrop() {
            return false;
        }

        @Override
        public ItemStack getDropType() {
            return this.item.clone();
        }

        @Override
        public Optional<Sound> getSound() {
            return Optional.ofNullable(dropSound);
        }
        @Override
        public Optional<OreParticle>getParticle(){
            return Optional.ofNullable(particle);
        }

        @Override
        public Optional<MiningMessage> getDropMessage() {
            return Optional.ofNullable(miningMessage);
        }

        @Override
        public void drop(Player player, Location dropLocation) {

        }
    }

}
