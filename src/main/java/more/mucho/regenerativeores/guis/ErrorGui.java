package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.guis.framework.GUI;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ErrorGui extends ModernBaseGui {
    private final GUI goBackGui;
    private final String message;
    public ErrorGui(GUI goBackGui,String message) {
        super("Something went wrong...",27);
        this.goBackGui = goBackGui;
        this.message = message;
    }

    public void decorate(Player player){

        addButton(13,new InventoryButton().creator(
                who->{
                    return new ItemBuilder(Material.RED_WOOL,"Error")
                            .setLore(
                                    "",
                                    message,
                                    "Click to go back"
                            )
                            .build();
                }
        ).consumer(event->{
            goBackGui.open(event.getWhoClicked());
        }));
        super.decorate(player);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        goBackGui.open(event.getPlayer());
    }

    @Override
    protected void design() {

    }
}
