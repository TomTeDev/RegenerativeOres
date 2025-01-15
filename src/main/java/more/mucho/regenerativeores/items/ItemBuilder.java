package more.mucho.regenerativeores.items;

import com.google.common.base.Preconditions;
import more.mucho.regenerativeores.RegenerativeOres;
import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ItemBuilder extends ItemBuilderBase {

    public static ItemStack AIR = new ItemStack(Material.AIR);

    private Set<ItemFlag> flags = new HashSet<>();
    private Map<Enchantment, Integer> enchants = new HashMap<>();
    private ItemStack ogItem = null;
    //private final List<LoreNode> loreNodes = new ArrayList<>();
    public ItemBuilder(@NonNull Material material) {
        super(material);
    }

    public ItemBuilder(@NonNull ItemStack item) {
        super(item);
        this.ogItem = item;
        unwrap(item);
    }

    @Override
    public Plugin getPlugin() {
        return RegenerativeOres.getPlugin(RegenerativeOres.class);
    }

    public ItemBuilder(@NonNull Material material, @NonNull String displayName, @NonNull String... lore) {
        super(new ItemStack(material));
        this.displayName = displayName;
        this.lore = Arrays.stream(lore).toList();
    }

    public ItemBuilder setAmount(int amount) {
        Preconditions.checkArgument(amount >= 0, "Amount must be greater or equal 0");
        this.amount = amount;
        return this;
    }

    public ItemBuilder setDisplayName(@NonNull String _displayName) {
        this.displayName = _displayName;
        return this;
    }

    public ItemBuilder addTag(@NonNull String tagName, Object tagValue) {
        setTag(tagName, tagValue);
        return this;
    }

    public ItemBuilder addLoreNode(String nodeId, LoreNode.Priority priority) {
        Map<String, LoreNode.Priority> nodes = getLoreNodes();
        nodes.put(nodeId, priority);
        setLoreNodes(nodes);
        return this;
    }

    public ItemBuilder removeLoreNode(String nodeId) {
        Map<String, LoreNode.Priority> nodes = getLoreNodes();
        if (nodes.containsKey(nodeId)) {
            nodes.remove(nodeId); // Remove the node if it exists
            setLoreNodes(nodes); // Save the updated map
        }
        return this;
    }


    protected List<LoreNode> getSortedLoreNodes() {
        Map<String, LoreNode.Priority> nodes = getLoreNodes();
        return nodes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()) // Sort by priority
                .map(entry -> new LoreNode(entry.getValue(), entry.getKey(),
                        LoreNodeRegistry.getFunctionById(entry.getKey()))) // Convert to LoreNode
                .toList();
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder addLoreLine(String... lore) {
        if (this.lore == null) this.lore = new ArrayList<>();
        Collections.addAll(this.lore, lore);
        return this;
    }

    public ItemBuilder colorLore() {
        this.colorLore = true;
        return this;
    }

    public ItemBuilder colorDisplayName() {
        this.colorDisplayName = true;
        return this;
    }

    public ItemBuilder addGlowEffect() {
        this.addFlag(ItemFlag.HIDE_ENCHANTS);
        if (material == Material.BOW) {
            return this.addEnchant(Enchantment.LURE, 1);
        } else {
            return this.addEnchant(Enchantment.ARROW_INFINITE, 1);
        }
    }

    public ItemBuilder showAttributes() {
        this.flags.remove(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder removeFlag(ItemFlag flag) {
        this.flags.remove(flag);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        enchants.put(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        enchants.remove(enchantment);
        return this;
    }


    public ItemBuilder parsePlaceholdersDisplayName(String placeholder, String value) {
        if (this.displayName != null) {
            this.displayName = this.displayName.replace(placeholder, value);
        }
        return this;
    }

    private List<String> getLoreFromTag(){
        if(!hasTag(ItemTags.LORE))return new ArrayList<>();
        return Arrays.stream(getTagOrDefault(ItemTags.LORE, PersistentDataType.STRING,"").split("\n")).toList();

    }
    private void saveLoreToTag(List<String> lore){
        addTag(ItemTags.LORE,String.join("\n",lore));
    }

    public ItemStack build() {
        ItemStack item = this.ogItem ==null?new ItemStack(material) : this.ogItem;
        if (item.getItemMeta() == null) return item;
        ItemMeta meta = item.getItemMeta();
        if (this.displayName != null) {
            if (colorDisplayName) {
                displayName = Colors.addColor(displayName);
            }
            meta.setDisplayName(this.displayName);
        }
        // Generate and sort lore nodes
        List<String> finalLore = new ArrayList<>();
        if (this.lore != null) {
            finalLore.addAll(this.lore); // Add existing lore lines
            saveLoreToTag(this.lore);
        }
        copyTags(meta.getPersistentDataContainer());

        getSortedLoreNodes().stream().map(node -> node.generateLorePart(meta))
                .forEach(finalLore::add);
        // Apply color and set lore
        if (!finalLore.isEmpty()) {
            if (colorLore) {
                finalLore = finalLore.stream().map(Colors::addColor).toList();
            }
            meta.setLore(finalLore);
        }
        meta.addItemFlags(flags.toArray(new ItemFlag[0]));

        meta.removeEnchantments();
        enchants.forEach((enchant, level) -> {
            meta.addEnchant(enchant, level, true);
        });
        item.setAmount(this.amount);

        item.setItemMeta(meta);
        return item;
    }

    protected void unwrap(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName()) {
                this.displayName = meta.getDisplayName();
            }
            if (meta.hasLore()) {
                if(hasTag(ItemTags.LORE)){
                    this.lore = getLoreFromTag();

                }else{
                    this.lore = meta.getLore();
                }
            }
            this.flags = meta.getItemFlags();
            this.enchants = new HashMap<>(meta.getEnchants());
        }
        this.amount = itemStack.getAmount();
    }

    public static ItemStack background(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(null);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack craftInfoItem(String... infos) {
        List<String> lore = new ArrayList<>();
        for (String s : infos) {
            lore.add(Colors.SILVER + s);
        }
        return new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .setDisplayName(Colors.INDIGO + "Informacja (?)")
                .setLore(lore)
                .build();
    }
}
