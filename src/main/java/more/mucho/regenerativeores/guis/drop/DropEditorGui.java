package more.mucho.regenerativeores.guis.drop;

import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.ores.drops.builders.ExpDropBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class DropEditorGui extends ModernBaseGui {
    private final ExpDropBuilder dropBuilder;

    public DropEditorGui(String title, int size, ExpDropBuilder dropBuilder) {
        super(title, size);
        this.dropBuilder = dropBuilder;
    }

    public void decorate(Player player) {

        addButton(1, new InventoryButton().creator(who -> {
                    return new ItemBuilder(Material.TARGET).setDisplayName("Set chance")
                            .setLore(
                                    "",
                                    "Current chance: " + dropBuilder.getChance()
                            )
                            .build();
                })
                .consumer(event -> {
                    event.getWhoClicked().sendMessage("Click");
                }));
        addButton(2, new InventoryButton().creator(who -> {
            String currentSound = "NONE";
            if (dropBuilder.getSound() != null) {
                currentSound = dropBuilder.getSound().name();
            }
            return new ItemBuilder(Material.MUSIC_DISC_5).setDisplayName("Set sound")
                    .setLore(
                            "",
                            "Current sound: " + currentSound,
                            "Click to edit"
                    )
                    .build();
        })
                        .consumer(event->{

                        })
        );
        addButton(3,new InventoryButton().creator(who->{
            return new ItemBuilder(Material.REDSTONE,"Clear")
                    .setLore(
                            "",
                            "Click to start over"
                    ).build();

        })
                        .consumer(event -> {
                            event.getWhoClicked().sendMessage("Click");
                        })
        );
        addButton(4,
                new InventoryButton().creator(who -> {
                    return new ItemBuilder(Material.EMERALD).setDisplayName("Set min value")
                            .setLore(
                                    "",
                                    "Current min value: " + dropBuilder.getMinValue()
                            )
                            .build();
                })
                        .consumer(event->{
                            event.getWhoClicked().sendMessage("Click");
                        })
                );


        super.decorate(player);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    protected void design() {

    }
}
