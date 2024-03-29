package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.BarracksMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Buildings.BuildingMenus.BarracksTrainMenu;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Barracks extends Building {
    //private List<Troop> troops

    public Barracks(Arena arena) {
        super(arena);
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.BARRACKS_ITEM_STACK.clone(), Arrays.asList(ChatColor.GRAY +
                " Barracks train valiant " + ChatColor.RED + "troops", ChatColor.GRAY + " that can be used in battle."));
    }
    @Override
    public void openMenu() {
        Player player = getArena().getPlayer();
        ClashCraft.guiManager.openGUI(new BarracksMenu(this), player);
    }

    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.BARRACKS_ITEM_STACK.clone();
    }

    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.BARRACKS_DISPLAY_NAME;
    }

    @Override
    public ChatColor getPrimaryColor() {
        return BuildingGlobals.BARRACKS_COLOR;
    }

    @Override
    public int getGridLengthX() {
        return BuildingGlobals.BARRACKS_GRID_LENGTH;
    }

    @Override
    public int getGridLengthZ() {
        return BuildingGlobals.BARRACKS_GRID_LENGTH;
    }

    @Override
    public long getTimeToBuild(int level) {
        return BuildingGlobals.BARRACKS_BUILD_TIME[level - 1];
    }

    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic(BuildingGlobals.BARRACKS_SCHEMATICS[level-1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic("BarracksBrokenSchematic");
    }
    @Override
    public int getMaxLevel() {
        return BuildingGlobals.BARRACKS_MAX_LEVEL;
    }
}
