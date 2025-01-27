package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.guis.framework.GUI;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.ores.OreBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SelectMaterialGroupGui extends ModernBaseGui {
    private final GUI goBackGui;
    private final OreBuilder oreBuilder;

    public SelectMaterialGroupGui(GUI goBackGui, OreBuilder oreBuilder) {
        super("Select material group", 27);
        this.oreBuilder = oreBuilder;
        this.goBackGui = goBackGui;
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        goBackGui.open((Player)event.getPlayer());
    }

    public void decorate(Player player) {
        addButton(1, new InventoryButton().creator(who -> {
                            return new ItemBuilder(Material.COBBLESTONE).setDisplayName("Minecraft material").build();
                        })
                        .consumer(event -> {
                            new SelectiveGui(
                                    oreBuilder::setMaterial,
                                    this);
                        })
        );
        addGoBackButton(getSize(), goBackGui);
        super.decorate(player);
    }

    @Override
    protected void design() {

    }
}
