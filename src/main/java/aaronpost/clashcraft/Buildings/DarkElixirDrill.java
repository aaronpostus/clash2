package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DarkElixirDrill {
    public static ItemStack getShopItem() {
        ItemStack stack = new ItemStack(Material.GRAY_CONCRETE);
        return GUIHelper.attachComingSoonLore(stack,ChatColor.GRAY + "Dark Elixir Drill");
    }
}
