package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OreGui extends ModernBaseGui {


    public OreGui() {
        super("Ores", 27);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void decorate(Player player) {
        addButton(11, new InventoryButton()
                .creator(who -> {
                    return new ItemBuilder(Material.DIRT)
                            .setDisplayName(Colors.CREAMY + "Setup ores")
                            .setLore(
                                    "",
                                    "&7Click to edit what ores",
                                    "&7should be generated"
                            )
                            .colorLore()
                            .build();
                })
                .consumer((event) -> {

                })
        );

        addButton(13, new InventoryButton()
                .creator(who -> {
                    return new ItemBuilder(Material.BEDROCK)
                            .setDisplayName(Colors.CREAMY + "Replacement block")
                            .setLore(
                                    "",
                                    "&7Click to select what mined block",
                                    "&7should turn into"
                            )
                            .colorLore()
                            .build();
                })
                .consumer((event) -> {
                    Player whoClicked = (Player) event.getWhoClicked();
                    new SelectiveGui(
                            material->{
                                Bukkit.broadcastMessage("Selected material: "+material.name());
                            },
                            getInventory()
                    ).open(whoClicked);
                })

        );

        super.decorate(player);
    }

    @Override
    protected void design() {
        addDesignItem(ItemBuilder.background(Material.GRAY_STAINED_GLASS_PANE), 0, getSize() - 1);
    }

    public void open(Player player){
        openInventory(player,getInventory());
    }
}
