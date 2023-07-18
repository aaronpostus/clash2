package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.*;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResourceMenu extends InventoryGUI {
    private Player p;
    private Arena arena;
    public ResourceMenu(Arena arena) {
        super(ChatColor.GOLD + "+ Shop +");
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 36, name);
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, GoldMine.getShopItem());
        this.getInventory().setItem(20, GoldStorage.getShopItem());
        this.getInventory().setItem(12, ElixirCollector.getShopItem());
        this.getInventory().setItem(22, ElixirStorage.getShopItem());
        this.getInventory().setItem(14, DarkElixirDrill.getShopItem());
        this.getInventory().setItem(24, DarkElixirStorage.getShopItem());
        this.getInventory().setItem(16, BuilderHut.getShopItem());
        this.addButton(10,purchaseBuilding("goldmine"));
        this.addButton(20,purchaseBuilding("goldstorage"));
        this.addButton(12,purchaseBuilding("elixircollector"));
        this.addButton(22,purchaseBuilding("elixirstorage"));
        this.addButton(16, purchaseBuilding("builderhut"));
        GUIHelper.fillEmptyGUISpots(getInventory(),36);
    }
    private InventoryButton purchaseBuilding(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            switch(key) {
                case "goldmine":
                    arena.purchaseNewBuilding(new GoldMine(arena));
                    p.closeInventory();
                    //ClashCraft.guiManager.openGUI(new IslandMenuRefactor(),arena.getPlayer());
                    break;
                case "goldstorage":
                    arena.purchaseNewBuilding(new GoldStorage(arena));
                    p.closeInventory();
                    //ClashCraft.guiManager.openGUI(new IslandMenuRefactor(),arena.getPlayer());
                    break;
                case "elixircollector":
                    arena.purchaseNewBuilding(new ElixirCollector(arena));
                    p.closeInventory();
                    break;
                case "elixirstorage":
                    arena.purchaseNewBuilding(new ElixirStorage(arena));
                    p.closeInventory();
                    break;
                case "builderhut":
                    arena.purchaseNewBuilding(new BuilderHut(arena));
                    p.closeInventory();
                    break;
            }
        });
    }
}
