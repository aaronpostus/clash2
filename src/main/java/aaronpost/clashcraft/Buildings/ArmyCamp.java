package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ArmyCamp extends Building {
    public ArmyCamp(Arena arena) {
        super(arena);
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.ARMY_CAMP_ITEM_STACK.clone(), Arrays.asList(ChatColor.GRAY +
                " An army camp houses " + ChatColor.RED + "troops", ChatColor.GRAY + " on your island before battle."));
    }
    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.ARMY_CAMP_ITEM_STACK.clone();
    }
    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.ARMY_CAMP_DISPLAY_NAME;
    }
    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.GRAY;
    }
    @Override
    public int getGridLengthX() {
        return 8;
    }
    @Override
    public int getGridLengthZ() {
        return 8;
    }
    @Override
    public long getTimeToBuild(int level) {
        return 10;
    }
    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic("ArmyCamp1");
    }
    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
}
