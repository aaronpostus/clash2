package aaronpost.clashcraft.Currency;

import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Session;
import org.bukkit.inventory.ItemStack;

public class Elixir extends Currency {
    public Elixir(Session s) {
        super(10, 1000);
    }
    @Override
    public ItemStack getItemStack() {
        return Globals.ELIXIR_ITEM_STACK.clone();
    }

    @Override
    public String getDisplayName() {
            return Globals.ELIXIR_DISPLAY_NAME;
    }
}
