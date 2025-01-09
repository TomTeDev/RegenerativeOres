package more.mucho.regenerativeores.items;

import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder extends ItemBuilderBase {

    public static ItemStack AIR = new ItemStack(Material.AIR);
    private boolean isGlowing = false;
    private boolean showAttributes = false;

    public ItemBuilder(Material material) {
        super(new ItemStack(material));
    }

    public ItemBuilder(ItemStack item) {
        super(item);
        unwrap(item);
    }

    public ItemBuilder(Material material, String displayName, String... lore) {
        super(new ItemStack(material));
        this.displayName = displayName;
        this.lore = Arrays.stream(lore).toList();
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setDisplayName(String _displayName) {
        this.displayName = _displayName;
        return this;
    }

    public ItemBuilder addTag(String tagName, Object tagValue) {
        //TODO implement
/*        NbtOps instance = NbtOps.INSTANCE;
        if (val instanceof String) {
            return instance.createString((String) val);
        } else if (val instanceof Integer) {
            return instance.createInt((int) val);
        } else if (val instanceof Double) {
            return instance.createDouble((double) val);
        } else if (val instanceof Boolean) {
            return instance.createBoolean((boolean) val);
        } else if (val instanceof Long) {
            return instance.createLong((long) val);
        } else if (val instanceof Byte) {
            return instance.createByte((byte) val);
        } else if (val instanceof Short) {
            return instance.createShort((short) val);
        } else if (val instanceof Float) {
            return instance.createFloat((float) val);
        } else if (val instanceof List) {
            // You need to handle the list elements and convert them to Tag types
            ListTag listTag = new ListTag();
            for (Object element : (List<?>) val) {
                listTag.add(convertToTag(element));
            }
            return listTag;
        } else if (val instanceof Map) {
            // You need to handle the map entries and convert them to Tag types
            CompoundTag compoundTag = new CompoundTag();
            Map<?, ?> map = (Map<?, ?>) val;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                compoundTag.put(entry.getKey().toString(), convertToTag(entry.getValue()));
            }
            return compoundTag;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + val.getClass().getSimpleName());
        }*/
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.lore = new ArrayList<>(List.of(lore));
        return this;
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
        this.isGlowing = true;
        return this;
    }

    public ItemBuilder showAttributes() {
        this.showAttributes = true;
        return this;
    }

    public ItemBuilder parsePlaceholdersDisplayName(String placeholder, String value) {
        if (this.displayName != null) {
            this.displayName = this.displayName.replace(placeholder, value);
        }
        return this;
    }


    public ItemStack build() {
        ItemStack item = getItem();
        if (item.getItemMeta() == null) return item;
        ItemMeta meta = item.getItemMeta();
        if (this.displayName != null) {
            if (colorDisplayName) {
                displayName = Colors.addColor(displayName);
            }
            meta.setDisplayName(this.displayName);
        }
        if (this.lore != null) {
            if (colorLore) {
                List<String> cLore = new ArrayList<>();
                for (String s : this.lore) {
                    cLore.add(Colors.addColor(s));
                }
                this.lore = cLore;
            }
            meta.setLore(this.lore);
        }
        if (this.isGlowing) {
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (!this.showAttributes) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
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
                this.lore = meta.getLore();
            }
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


}
