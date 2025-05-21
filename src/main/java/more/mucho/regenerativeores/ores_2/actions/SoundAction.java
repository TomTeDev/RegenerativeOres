package more.mucho.regenerativeores.ores_2.actions;

import more.mucho.regenerativeores.ores_2.requirements.Requirement;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;
import java.util.UUID;

public class SoundAction extends BaseBreakAction {
    private final Sound sound;

    public SoundAction(UUID uuid, int chance, Collection<Requirement> requirements, Sound sound) {
        super(uuid, chance,requirements);
        this.sound = sound;
    }

    @Override
    public void onAction(BlockBreakEvent event) {
        if (!shouldExecute(event)) return;
        Player player = event.getPlayer();
        player.playSound(player, sound, 1f, 1f);
    }
}
