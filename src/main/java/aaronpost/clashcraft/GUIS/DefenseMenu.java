package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DefenseMenu extends InventoryGUI {
    private Player p;
    private Arena arena;
    public DefenseMenu(Arena arena) {
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GOLD + "+ Shop +");
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, BuildingGlobals.WALL_ITEM_STACK.clone());
        this.addButton(10,purchaseBuilding("wall"));
        GUIHelper.fillEmptyGUISpots(getInventory(),27);
    }
    private InventoryButton purchaseBuilding(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            switch(key) {
                case "wall":
                    arena.purchaseNewBuilding(new Wall(arena));
                    p.closeInventory();
                    //ClashCraft.guiManager.openGUI(new IslandMenuRefactor(),arena.getPlayer());
                    break;
            }
        });
    }
}
