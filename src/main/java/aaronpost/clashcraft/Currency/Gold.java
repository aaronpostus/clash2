package aaronpost.clashcraft.Currency;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Gold extends Currency {

    public static ItemStack ITEM_STACK = new ItemStack(Material.GOLD_INGOT);
    public static String DISPLAY_NAME = Color.YELLOW + "Gold";
    static {
        ItemMeta itemMeta = ITEM_STACK.getItemMeta();
        itemMeta.setDisplayName(DISPLAY_NAME);
    }
    public Gold(Player player) {
        super.setDisplayName(DISPLAY_NAME);
        super.setItemStack(ITEM_STACK);
    }
}
