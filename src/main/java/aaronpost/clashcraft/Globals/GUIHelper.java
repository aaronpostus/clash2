package aaronpost.clashcraft.Globals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIHelper {
    private static final ItemStack emptyItem;
    static {
        emptyItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = emptyItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + " ");
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
        meta.setLore(Arrays.asList(ChatColor.RED + "  Coming soon."));
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack attachComingSoonLore(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.RED + "  Coming soon."));
        stack.setItemMeta(meta);
        return stack;
    }
}
