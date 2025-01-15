package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.Ores;
import more.mucho.regenerativeores.OresImpl;
import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.items.ItemTags;
import more.mucho.regenerativeores.ores.Ore;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreatedOresGui extends ModernBaseGui {
    private int page = 0;
    private int[]oresSlots = new int[]{
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
    };
    public CreatedOresGui(){
        super("Created ores",27);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void decorate(Player player){
        List<Ore> ores = RegenerativeOres.getPlugin(RegenerativeOres.class).getOres().getOres();
        insertOres(ores);
        removeButton(getSize()-1);
        removeButton(getSize()-9);

        if(page>0){
            addButton(getSize()-9, new InventoryButton()
                    .creator(who -> {
                        return new ItemBuilder(Material.ARROW)
                                .setDisplayName(Colors.CREAMY + "Previous page")
                                .build();
                    })
                    .consumer(event -> {
                        Player whoClicked = (Player) event.getWhoClicked();
                        page--;
                        open(whoClicked);
                    }));
        }
        if((page+1)*oresSlots.length<ores.size()){
            addButton(getSize()-1, new InventoryButton()
                    .creator(who -> {
                        return new ItemBuilder(Material.ARROW)
                                .setDisplayName(Colors.CREAMY + "Next page")
                                .build();
                    })
                    .consumer(event -> {
                        Player whoClicked = (Player) event.getWhoClicked();
                        page++;
                        open(whoClicked);
                    }));
        }

        super.decorate(player);
    }

    private void insertOres(List<Ore>oresList){
        for(int i = 0;i<oresSlots.length;i++){
            if(page*oresSlots.length+i>=oresList.size())break;
            int finalIndex = i;
            addButton(oresSlots[i], new InventoryButton()
                    .creator(who -> {
                        Ore ore = oresList.get(page*oresSlots.length+finalIndex);
                        return new ItemBuilder(ore.getMaterial().asItem())
                                .setDisplayName(Colors.SALMON+"Ore ID: "+ore.getID())
                                .setLore(
                                        "",
                                        Colors.DARK_AQUA+ "LEFT CLICK"+Colors.LIGHT_PINK_GRAY+" to edit",
                                        Colors.DARK_AQUA+ "RIGHT CLICK + SHIFT"+Colors.LIGHT_PINK_GRAY+" to remove"
                                )
                                .addTag(ItemTags.ID,ore.getID())
                                .build();
                    })
                    .consumer(event -> {
                        Player whoClicked = (Player) event.getWhoClicked();
                        ItemStack currentItem = event.getCurrentItem();
                        if(currentItem == null||currentItem.getType().isAir())return;
                        int oreID = new ItemBuilder(currentItem).getTagOrDefault(ItemTags.ID, PersistentDataType.INTEGER,-1);
                        if(oreID<0)return;
                        Ores ores = RegenerativeOres.getPlugin(RegenerativeOres.class).getOres();
                        Optional<Ore> ore = ores.getOre(oreID);
                        if(ore.isEmpty()){
                            whoClicked.sendMessage(Colors.RED_INFO+"Unable to find ore with ID: "+oreID);
                            closeInventory(whoClicked);
                            return;
                        }
                        if(event.getClick().isLeftClick()){
                            openOreEditor(whoClicked,ore.get());
                            return;
                        }
                        if(event.getClick().isRightClick()&&event.getClick().isShiftClick()){
                            boolean deleted = ores.deleteOre(oreID);
                            if(!deleted){
                                whoClicked.sendMessage(Colors.RED_INFO+"Something went wrong");
                                return;
                            }
                            whoClicked.sendMessage(Colors.GREEN_INFO+"Deleted ore with ID: "+oreID);
                            open(whoClicked);
                        }
                    })
            );
        }
    }
    @Override
    protected void design() {
        addDesignItem(ItemBuilder.background(Material.YELLOW_STAINED_GLASS_PANE),0,getSize()-1);
    }

    private void openOreEditor(Player player,Ore ore){

    }

}
