package aaronpost.clashcraft.Buildings;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldMine extends Collector {
    public static ItemStack ITEM_STACK = new ItemStack(Material.CHEST);
    public static String DISPLAY_NAME = Color.YELLOW + "Gold Mine";
    static {
        ItemMeta itemMeta = ITEM_STACK.getItemMeta();
        itemMeta.setDisplayName(DISPLAY_NAME);
    }

    public GoldMine() {
        super.setDisplayName(DISPLAY_NAME);
        super.setItemStack(ITEM_STACK);
        super.setCollectionRate(3600);
        // super.currency = Player's gold currency;
    }
}
