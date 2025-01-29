package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.guis.drop.ExpDropEditGui;
import more.mucho.regenerativeores.guis.framework.GUI;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.ores.OreBuilder;
import more.mucho.regenerativeores.ores.drops.BaseExpDrop;
import more.mucho.regenerativeores.ores.drops.BaseItemDrop;
import more.mucho.regenerativeores.ores.drops.MiningDrop;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class DropsManagmentGui extends ModernBaseGui {
    private final GUI goBackGUI;;
    private final OreBuilder oreBuilder;
    private int page = 0;
    private final int[]dropSlots;
    public DropsManagmentGui(GUI goBackGUI, OreBuilder oreBuilder) {
        super("Manage drops", 36);
        this.goBackGUI = goBackGUI;
        this.oreBuilder = oreBuilder;
        this.page = 0;
        this.dropSlots = new int[]{
                10,11,12,13,14,15,16
        };
    }

    @Override
    public void decorate(Player player){
       clearButtons();

        int index = page*dropSlots.length;
        int smallIndex = 0;
        for(MiningDrop drop : oreBuilder.getDrops()){
            aha
            int slot = dropSlots[smallIndex];
            InventoryButton button = null;
            if(drop instanceof BaseExpDrop expDrop){
                button = new InventoryButton().creator(who->{
                    return new ItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayName("Exp drop")
                            .setLore(
                                    "",
                                    "Chance: " + expDrop.getDropChance() + "%",
                                    "Min value: " + expDrop.getRange().min,
                                    "Max value: " + expDrop.getRange().max,
                                    "Is direct: "+expDrop.isDirect(),
                                    "Left click to edit",
                                    "Shift click to remove"
                            )
                            .build();
                })
                        .consumer(event->{
                            if(event.getClick().isLeftClick()){
                                new ExpDropEditGui().open(event.getWhoClicked());
                                return;
                            }
                            if(event.getClick().isShiftClick()){
                                delete drop;
                            }

                        });
            }
            if(drop instanceof BaseItemDrop itemDrop){
               button = new InventoryButton().creator(who->{
                   return new ItemBuilder(itemDrop.getItem())
                           .setLore(
                                   "",
                                   "Chance: " + itemDrop.getDropChance() + "%",
                                   "Min value: " + itemDrop.getRange().min,
                                   "Max value: " + itemDrop.getRange().max,
                                   "Is direct: "+itemDrop.isDirect(),
                                   "Click to edit"
                           )
                           .build();
               })
                       .consumer(event->{
                           event.getWhoClicked().sendMessage("Click");
                       });
            }


            if(button != null){
                addButton(slot,button);
            }
            index++;
            smallIndex++;
        }

        addGoBackButton(getSize()-9,goBackGUI);

        addButton(getSize()-5,new InventoryButton()
                .creator(who->{
                    return new ItemBuilder(Material.BOOKSHELF,"Item drop")
                            .setLore("Click to create new item drop")
                            .build();
                })
                .consumer(event->{
                    open drop create gui;
                })
        );
        addButton(getSize()-3,new InventoryButton()
                .creator(who->{
                    return new ItemBuilder(Material.EXPERIENCE_BOTTLE,"Exp drop")
                            .setLore("Click to create new exp drop")
                            .build();
                })
                .consumer(event->{
                    open drop create gui;
                })
        );

        super.decorate(player);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        goBackGUI.open((Player)event.getPlayer());
    }

    @Override
    protected void design() {

    }
}
