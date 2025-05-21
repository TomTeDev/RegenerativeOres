package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.UUID;

public class PotionAction extends BaseBreakAction {

    private final PotionEffect effect;

    public PotionAction(UUID uuid, int chance, Collection<Requirement> requirements, PotionEffect effect) {
        super(uuid, chance,requirements);
        this.effect = effect;
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;
        Player miner = event.getPlayer();
        miner.addPotionEffect(effect);
    }
}
