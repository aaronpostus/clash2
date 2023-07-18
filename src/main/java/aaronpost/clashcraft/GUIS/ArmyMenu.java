package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.ArmyCamp;
import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Buildings.DarkBarracks;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArmyMenu extends InventoryGUI {
    private Player p;
    private Arena arena;
    public ArmyMenu(Arena arena) {
        super(ChatColor.GOLD + "+ Shop +");
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 27, name);
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, Barracks.getShopItem());
        this.addButton(10,purchaseBuilding("barracks"));
        this.getInventory().setItem(12, ArmyCamp.getShopItem());
        this.getInventory().setItem(14, DarkBarracks.getShopItem());
        this.addButton(12,purchaseBuilding("armycamp"));
        GUIHelper.fillEmptyGUISpots(getInventory(),27);
    }
    private InventoryButton purchaseBuilding(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            switch(key) {
                case "barracks":
                    arena.purchaseNewBuilding(new Barracks(arena));
                    p.closeInventory();
                    //ClashCraft.guiManager.openGUI(new IslandMenuRefactor(),arena.getPlayer());
                    break;
                case "armycamp":
                    arena.purchaseNewBuilding(new ArmyCamp(arena));
                    p.closeInventory();
                    break;
            }
        });
    }
}
