package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IInstantBuild;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BuilderHut extends Building implements IInstantBuild {
    private transient Building BuildingToBuild;
    public BuilderHut(Arena arena) {
        super(arena);
    }
    public BuilderHut(int x, int z) {
        super(x,z);
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.BUILDER_HUT_ITEM_STACK.clone(), Arrays.asList(ChatColor.GRAY +
                        " A builder hut houses an NPC", ChatColor.GRAY + " who builds/upgrades buildings!"));
    }
    @Override
    public ItemStack getStatsItem() {
        ItemStack stack = new ItemStack(Material.OAK_SIGN);
        return GUIHelper.attachNameAndLore(stack,getDisplayName() + " Stats", Arrays.asList(
                ChatColor.GRAY + "  Houses one builder who can work on",
                ChatColor.GRAY + "  one building at a time."
        ));
    }
    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.BUILDER_HUT_ITEM_STACK.clone();
    }
    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.BUILDER_HUT_DISPLAY_NAME;
    }
    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.GRAY;
    }
    @Override
    public int getGridLengthX() {
        return 4;
    }
    @Override
    public int getGridLengthZ() {
        return 4;
    }
    @Override
    public long getTimeToBuild(int level) {
        return 0;
    }
    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic("BuilderHut1");
    }
    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
}
