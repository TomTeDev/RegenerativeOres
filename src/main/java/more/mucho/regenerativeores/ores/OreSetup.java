package more.mucho.regenerativeores.ores;

import more.mucho.regenerativeores.events.OreMineEvent;
import more.mucho.regenerativeores.utils.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OreSetup {


    private Sound sound;
    private ExpDrop expDrop;
    private List<OreCommand> commands;
    private List<MiningDrop>drops;
    private MiningPermission miningPermission;
    private MiningTool miningTool;


    private record OreCommand(int id, String command, int procChance) {
        public boolean testProc() {
            return procChance < 0 || Random.randomInt(100) < procChance;
        }
    }

    private record ExpDrop(int min, int max, int chance) {
        public boolean testProc() {
            return Random.randomInt(100) < chance;
        }
    }

    private record MiningPermission(String permission, String denyMessage) {
        public boolean testPermission(Player target) {
            return permission == null || target.hasPermission(permission);
        }
    }

    private record MiningTool(ItemStack requiredTool, String message) {
        public boolean testTool(Player target) {
            if(requiredTool == null)return true;
            ItemStack itemInUse = target.getItemInUse();
            return itemInUse != null && itemInUse.isSimilar(requiredTool);
        }
    }

    public boolean canMine(Player player,Location location){
        if (!miningPermission.testPermission(player)) {
            player.sendMessage(miningPermission.denyMessage);
            return false;
        }
        if(!miningTool.testTool(player)){
            player.sendMessage(miningTool.message);
            return false;
        }
        return true;
    }

    public void mine(Player player,Location location) {
        if(!canMine(player,location))return;
        OreMineEvent event = new OreMineEvent();
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled())return;

        if(this.sound !=null){
            player.playSound(player,sound,1,1);
        }

        for (MiningDrop drop : drops) {
            if (drop.testDrop()) {
                drop.drop(player,location);
                return;
            }
        }

    }


}
