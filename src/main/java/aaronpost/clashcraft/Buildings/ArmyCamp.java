package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.Buildings.NPC.WanderNPC;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ArmyCamp extends Building {
    private transient Building BuildingToBuild;
    private transient WanderNPC npc;
    public ArmyCamp(Arena arena) {
        super(arena);
    }
    public ArmyCamp(int x, int z) {
        super(x,z);
    }
    public static ItemStack getShopItem() {
        ItemStack stack = BuildingGlobals.ARMY_CAMP_ITEM_STACK.clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GRAY + " An army camp houses " + ChatColor.RED + "troops", ChatColor.GRAY + " on your island before battle."));
        stack.setItemMeta(meta);
        return stack;
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
    public Schematic getSchematic() {
        return Schematics.s.getSchematic("ArmyCamp1");
    }
    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
}
