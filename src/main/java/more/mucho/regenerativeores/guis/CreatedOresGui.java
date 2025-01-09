package more.mucho.regenerativeores.guis;

import more.mucho.regenerativeores.Ore;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

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
        List<Ore> ores = new ArrayList<>();
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

    private void insertOres(List<Ore>ores){
        for(int i = 0;i<oresSlots.length;i++){
            if(page*oresSlots.length+i>=ores.size())break;
            int finalIndex = i;
            addButton(oresSlots[i], new InventoryButton()
                    .creator(who -> {
                        Ore ore = ores.get(page*oresSlots.length+finalIndex);
                        //TODO finish;
                        return new ItemBuilder(Material.DIRT).build();
                    }));

        }

    }
    @Override
    protected void design() {
        addDesignItem(ItemBuilder.background(Material.CYAN_STAINED_GLASS),0,getSize()-1);
    }
    private void open(Player player){
        openInventory(player, getInventory());
    }
}
