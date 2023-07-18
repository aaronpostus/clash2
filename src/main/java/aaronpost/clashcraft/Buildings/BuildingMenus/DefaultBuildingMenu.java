package aaronpost.clashcraft.Buildings.BuildingMenus;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Commands.PickBuildingUp;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.Globals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DefaultBuildingMenu extends InventoryGUI {
    private Building building;
    private int upgradeIndex = 15, statsIndex = 13, pickupIndex = 11;
    public DefaultBuildingMenu(Building building) {
        super(building.getDisplayName());
        this.building = building;
    }
    public DefaultBuildingMenu(Building building, int spaceToAdd) {
        super(building.getDisplayName());
        this.building = building;
        upgradeIndex += spaceToAdd;
        statsIndex += spaceToAdd;
        pickupIndex += spaceToAdd;
    }

    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 27, ChatColor.GRAY + "- " + name +
                ChatColor.GRAY + " -");
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(upgradeIndex, building.getUpgradeItem());
        this.getInventory().setItem(pickupIndex, Globals.PICK_UP_ITEM);
        this.getInventory().setItem(statsIndex, building.getStatsItem());
        this.addButton(pickupIndex, pickup());
        this.addButton(upgradeIndex, upgrade());
    }
    private InventoryButton pickup() {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            new PickBuildingUp(building).execute(building.getArena());
            player.closeInventory();
        });
    }
    private InventoryButton upgrade() {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            if(building.isMaxLevel()) {
                building.sendMessage("Already maxed out.");
            }
            else {
                building.upgrade();
            }
            player.closeInventory();
        });
    }
}
