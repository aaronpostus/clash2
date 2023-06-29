package aaronpost.clashcraft.Buildings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DarkElixirDrill {
    public static ItemStack getShopItem() {
        ItemStack stack = new ItemStack(Material.GRAY_CONCRETE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Dark Elixir Drill");
        meta.setLore(Arrays.asList(ChatColor.RED + "  Coming soon."));
        stack.setItemMeta(meta);
        return stack;
    }
}
