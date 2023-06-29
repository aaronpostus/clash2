package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Commands.UpdateStorageCapacity;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class GoldStorage extends Building {
    public GoldStorage(Arena arena) {
        super(arena);
        new UpdateStorageCapacity().execute(arena);
    }
    public static ItemStack getShopItem() {
        ItemStack itemStack = BuildingGlobals.GOLD_STORAGE_ITEM_STACK.clone();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GRAY + "  A gold storage allows you to",
                ChatColor.GRAY + "  stow reserves of " + ChatColor.GOLD + "gold" + ChatColor.GRAY + "."));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    @Override
    public boolean storesCurrency() { return true; }
    @Override
    public int getStorageCapacity(String currencyType) {
        if(isNewBuilding()) {
            return 0;
        }
        return BuildingGlobals.GOLD_STORAGE_CAPACITY[getLevel() - 1];
    }
    @Override
    public List<String> storageCurrencies() { return BuildingGlobals.GOLD_STORAGE_CURRENCIES; }

    @Override
    public void click() {
        openMenu();
    }

    @Override
    public void openMenu() {
        ClashCraft.guiManager.openGUI(new DefaultBuildingMenu(this),getArena().getPlayer());
    }

    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.GOLD_STORAGE_ITEM_STACK.clone();
    }

    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.GOLD_STORAGE_DISPLAY_NAME;
    }

    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.GOLD;
    }

    @Override
    public int getGridLengthX() {
        return 6;
    }
    @Override
    public int getGridLengthZ() {
        return 6;
    }

    @Override
    public long getTimeToBuild(int level) {
        return BuildingGlobals.GOLD_STORAGE_BUILD_TIME[level - 1];
    }

    @Override
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.GOLD_STORAGE_SCHEMATICS[getLevel() - 1]);
    }

    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
