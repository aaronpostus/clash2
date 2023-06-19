package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuilderHut;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
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
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GOLD + "+ Shop +");
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, GoldMine.getShopItem());
        this.getInventory().setItem(16, BuilderHut.getShopItem());
        this.addButton(10,purchaseBuilding("goldmine"));
        this.addButton(16, purchaseBuilding("builderhut"));
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
                case "builderhut":
                    arena.purchaseNewBuilding(new BuilderHut(arena));
                    p.closeInventory();
                    break;
            }
        });
    }
}
