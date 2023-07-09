package aaronpost.clashcraft.Globals;

import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class GUIHelper {
    private static final ItemStack emptyItem;
    static {
        emptyItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = emptyItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.BLACK + " ");
        }
        emptyItem.setItemMeta(meta);
    }
    public static void fillEmptyGUISpots(Inventory inventory,int inventorysize) {
        fillEmptyGUISpots(inventory,inventorysize,emptyItem);
    }
    public static void fillEmptyGUISpots(Inventory inventory,int inventorysize, ItemStack emptyItem) {
        for(int i = 0; i < inventorysize; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i,emptyItem);
            }
        }
    }
    public static ItemStack attachComingSoonLore(ItemStack stack,String name) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setLore(List.of(ChatColor.RED + "  Coming soon."));
            meta.setDisplayName(name);
        }
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack attachComingSoonLore(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setLore(List.of(ChatColor.RED + "  Coming soon."));
        }
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack attachLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack attachNameAndLore(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
            meta.setDisplayName(name);
        }
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack attachNameAndData(ItemStack item, String name, NamespacedKey data, String dataKey)
    {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            PersistentDataContainer buildingData = meta.getPersistentDataContainer();
            buildingData.set(data, PersistentDataType.STRING,dataKey);
        }
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack attachData(ItemStack item, NamespacedKey data, String dataKey) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer buildingData = meta.getPersistentDataContainer();
            buildingData.set(data, PersistentDataType.STRING,dataKey);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack attachName(ItemStack item, String displayName) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
        }
        item.setItemMeta(meta);
        return item;
    }
}
