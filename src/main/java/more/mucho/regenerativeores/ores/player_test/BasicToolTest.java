package more.mucho.regenerativeores.ores.player_test;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BasicToolTest implements PlayerTest{
    private final ItemStack requiredTool;
    private final String denyMessage;
    public BasicToolTest(ItemStack requiredTool,String denyMessage) {
        this.requiredTool = requiredTool;
        this.denyMessage = denyMessage;
    }

    @Override
    public boolean test(Player player) {
        ItemStack itemInUse = player.getItemInUse();
        return itemInUse!=null&& !itemInUse.getType().isAir()&&isSimilarToRequiredTool(itemInUse);
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }

    private boolean isSimilarToRequiredTool(ItemStack itemInUse) {
        //TODO MAKE IT BETTER...
        return itemInUse.getType().equals(requiredTool.getType());
    }


    @Override
    public void serialize(ConfigurationSection section) {
        section.set("type", "tool");
        section.set("requiredTool", requiredTool);
        section.set("denyMessage", denyMessage);
    }

    public static PlayerTest deserialize(ConfigurationSection section) {
        if(section == null)throw new IllegalArgumentException("Section is null");
        String type = section.getString("type");
        if (type == null) {
            throw new IllegalArgumentException("PlayerTest type is not specified.");
        }

        if (!type.equalsIgnoreCase("tool")) {
            throw new IllegalArgumentException("Unknown PlayerTest type: " + type);
        }
        ItemStack requiredTool = section.getItemStack("requiredTool");
        String denyMessage = section.getString("denyMessage");
        return new BasicToolTest(requiredTool,denyMessage);
    }
}
