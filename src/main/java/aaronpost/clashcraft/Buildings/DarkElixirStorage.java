package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DarkElixirStorage {
    public static ItemStack getShopItem() {
        ItemStack stack = new ItemStack(Material.GRAY_SHULKER_BOX);
        return GUIHelper.attachComingSoonLore(stack,ChatColor.GRAY + "Dark Elixir Storage");
    }
}
