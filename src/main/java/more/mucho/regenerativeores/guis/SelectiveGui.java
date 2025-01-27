package more.mucho.regenerativeores.guis;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import more.mucho.regenerativeores.guis.framework.GUI;
import more.mucho.regenerativeores.guis.framework.InventoryButton;
import more.mucho.regenerativeores.guis.framework.ModernBaseGui;
import more.mucho.regenerativeores.items.ItemBuilder;
import more.mucho.regenerativeores.utils.Colors;
import more.mucho.regenerativeores.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class SelectiveGui extends ModernBaseGui {
    private final int[] blockSlots = new int[]
            {
                    10, 11, 12, 13, 14, 15, 16,
                    19, 20, 21, 22, 23, 24, 25,
                    28, 29, 30, 31, 32, 33, 34,
                    37, 38, 39, 40, 41, 42, 43
            };

    private final Consumer<Material> consumer;
    private final GUI goBackGui;
    private boolean allowClose = false;
    private String queriedBlockType = null;
    private int page = 0;

    public SelectiveGui(Consumer<Material> consumer, GUI goBackGui) {
        super("Select block", 54);
        this.consumer = consumer;
        this.goBackGui = goBackGui;
    }

    @Override
    public void decorate(Player player) {
        addButton(4, new InventoryButton()
                .creator(who -> {
                    return new ItemBuilder(Material.ANVIL)
                            .setDisplayName(Colors.CREAMY + "Search for block")
                            .build();
                })
                .consumer(event -> {
                    Player whoClicked = (Player) event.getWhoClicked();
                    try {
                        SignGUI signGUI = SignGUI.builder()
                                .setLines(null, "Block", "to search for",  "================")
                                .setHandler((p, result) -> {
                                    String blockType = result.getLineWithoutColor(0);
                                    if (blockType.isEmpty()) {
                                        // The user has not entered anything on line 2, so we open the sign again
                                        return List.of(SignGUIAction.displayNewLines(null, "Block", "to search for",  "================"));
                                    }
                                    queriedBlockType = blockType;
                                    page = 0;
                                    allowClose = true;
                                    open(whoClicked);
                                    // Just close the sign by not returning any actions
                                    return Collections.emptyList();
                                })
                                .build();
                        allowClose = true;
                        signGUI.open(player);
                    } catch (Exception ignoredForNow) {
                        //TODO handle exception
                    }
                })
        );
        createQueryButtons();
        super.decorate(player);
    }

    private void createQueryButtons() {
        if (queriedBlockType == null) return;
        int startIndex = page * blockSlots.length;
        int index = 0;
        List<Material> matchingMaterials = ItemUtils.matchBlocks(queriedBlockType);
        for (Material material : matchingMaterials) {
            if (startIndex > 0) {
                startIndex--;
                continue;
            }
            addButton(blockSlots[index], new InventoryButton()
                    .creator(who -> {
                        return new ItemBuilder(material)
                                .setDisplayName(Colors.LAVENDER + material.name())
                                .build();
                    })
                    .consumer(event -> {
                        if(event.getCurrentItem() == null)return;
                        consumer.accept(event.getCurrentItem().getType());
                    })
            );
            index++;
            if (index >= blockSlots.length) break;
        }
        if (((page + 1) * blockSlots.length) < matchingMaterials.size()) {
            addButton(53, new InventoryButton()
                    .creator(who -> {
                        return new ItemBuilder(Material.ARROW)
                                .setDisplayName(Colors.CREAMY + "Next page")
                                .build();
                    })
                    .consumer(event -> {
                        allowClose = true;
                        page++;
                        open((Player) event.getWhoClicked());
                    })
            );
        }else{
            removeButton(53);
        }
        if (page > 0) {
            addButton(45, new InventoryButton()
                    .creator(who -> {
                        return new ItemBuilder(Material.ARROW)
                                .setDisplayName(Colors.CREAMY + "Previous page")
                                .build();
                    })
                    .consumer(event -> {
                        allowClose = true;
                        page--;
                        open((Player) event.getWhoClicked());
                    })
            );
        }else{
            removeButton(45);
        }
    }

    public void open(Player player) {
        openInventory(player, getInventory());

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        super.onOpen(event);
        allowClose = false;
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Bukkit.broadcastMessage("Allow close: " + allowClose);
        if (allowClose) return;
        goBackGui.open((Player) event.getPlayer());
    }

    @Override
    protected void design() {
        addDesignItem(ItemBuilder.background(Material.GRAY_STAINED_GLASS_PANE), 0, getSize() - 1);
    }

/*
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        super.onClick(event);
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (event.getCurrentItem() == null) return;
        if (isBlockSlot(event.getSlot())) {
            if (!event.getCurrentItem().getType().isAir()) return;
            consumer.accept(event.getCurrentItem().getType());
        }
    }
*/

    private boolean isBlockSlot(int slot) {
        for (int i : blockSlots) {
            if (i == slot) return true;
        }
        return false;
    }
}
