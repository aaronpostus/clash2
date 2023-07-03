package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Building implements IDefenseBuilding {
    public static ItemStack getShopItem() {
        List<String> lore = new ArrayList<>();
        lore.add("  " + ChatColor.GRAY + "A cannon will defend your island from");
        lore.add("  " + ChatColor.RED + "dangerous troops" + ChatColor.GRAY + " when you are offline.");
        return GUIHelper.attachNameAndLore(BuildingGlobals.CANNON_ITEM_STACK.clone(),
                BuildingGlobals.CANNON_DISPLAY_NAME,lore);

    }
    public Cannon(Arena arena) {
        super(arena);
    }
    public Schematic getBrokenSchematic() {
        return null;
    }
    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.CANNON_ITEM_STACK.clone();
    }
    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.CANNON_DISPLAY_NAME;
    }
    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.GRAY;
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
        return BuildingGlobals.CANNON_BUILD_TIME[level - 1];
    }
    @Override
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.CANNON_SCHEMATICS[getLevel() -1]);
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void addTroopToDamage(Troop troopFromPlayer) {
        System.out.println("new troop in range");
    }

    @Override
    public float getAttackSpeed() {
        return 0.8f;
    }
    @Override
    public float getAttackRange() {
        return 9 * 2;
    }
    @Override
    public float getDamagePerShot() {
        return 7.2f;
    }
    @Override
    public DefenseType getDamageType() {
        return DefenseType.SINGLE_TARGET;
    }
}
