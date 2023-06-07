package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopMenu extends InventoryGUI {

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, "");
    }

    @Override
    public void decorate(Player player) {

    }
    //private
}
