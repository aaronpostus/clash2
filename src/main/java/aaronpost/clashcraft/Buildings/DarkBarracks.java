package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.inventory.ItemStack;

public class DarkBarracks {
    public static ItemStack getShopItem() {
        ItemStack stack = BuildingGlobals.DARK_BARRACKS_ITEM_STACK.clone();
        return GUIHelper.attachComingSoonLore(stack);
    }
}
