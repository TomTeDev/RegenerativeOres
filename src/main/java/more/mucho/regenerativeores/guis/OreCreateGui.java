package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.ores.OreBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OreCreateGui extends ModernBaseGui {
    private final OreBuilder oreBuilder;

    public OreCreateGui(OreBuilder oreBuilder) {
        super("Ores setup", 27);
        this.oreBuilder = oreBuilder;
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void decorate(Player player) {
        addButton(1, new InventoryButton().creator(who -> {
                            return new ItemBuilder(Material.DIRT).setDisplayName("Select material").build();
                        })
                        .consumer(event -> {
                            Player whoClicked = (Player) event.getWhoClicked();
                            new SelectMaterialGroupGui(this, oreBuilder).open(whoClicked);
                        })
        );
        addButton(2,new InventoryButton().creator(who->{
            new ItemBuilder(Material.STICK,"Manage drops").build();
        }).consumer(event -> {
            new DropsManagmentGui(this,oreBuilder).open(event.getWhoClicked());
        }));

        super.decorate(player);
    }

    @Override
    protected void design() {
        addDesignItem(ItemBuilder.background(Material.GRAY_STAINED_GLASS_PANE), 0, getSize() - 1);
    }
}
