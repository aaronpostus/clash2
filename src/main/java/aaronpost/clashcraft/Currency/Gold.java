package aaronpost.clashcraft.Currency;

import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Session;
import org.bukkit.inventory.ItemStack;

public class Gold extends Currency {
    public Gold(Session s) {
        super(10, 1000);
    }

    public String getDisplayName() {
        return Globals.GOLD_DISPLAY_NAME;
    }
    public ItemStack getItemStack() {
        return Globals.GOLD_ITEM_STACK.clone();
    }
}
