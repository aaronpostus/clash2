package aaronpost.clashcraft.Buildings.BuildingMenus;

import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Buildings.Collector;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Commands.PickBuildingUp;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Singletons.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BarracksMenu extends DefaultBuildingMenu implements IFixedUpdatable {
    private final Barracks building;
    public BarracksMenu(Barracks building) {
        super(building,2);
        this.building = building;
        GameManager.getInstance().addFixedUpdatable(this);
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GRAY + "- " + ChatColor.BLACK + "Barracks" +
                ChatColor.GRAY + " -");
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(9, new ItemStack(Material.IRON_SWORD));
        this.getInventory().setItem(11, new ItemStack(Material.BOOK));
        this.addButton(9, openSubmenu("train"));

        //this.addButton(10, collect());
        super.decorate(p);
    }
    private InventoryButton openSubmenu(String submenu) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
            switch (submenu) {
                case "train":
                    ClashCraft.guiManager.openGUI(new BarracksTrainMenu(building,player),player);
            }
        });
    }
    /**private ItemStack getTrainItem() {

    }**/
    @Override
    public void onClose(InventoryCloseEvent event) {
        GameManager.getInstance().removeFixedUpdatable(this);
    }
    @Override
    public void fixedUpdateRequest() {
        //this.getInventory().setItem(10, getCollectItem());
    }

    @Override
    public void catchUpRequest(float hours) {

    }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
