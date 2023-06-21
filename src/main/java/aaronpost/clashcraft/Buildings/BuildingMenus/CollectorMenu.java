package aaronpost.clashcraft.Buildings.BuildingMenus;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Collector;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Commands.PickBuildingUp;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorMenu extends DefaultBuildingMenu implements IFixedUpdatable {
    private final Collector building;
    private final Currency currency;
    public CollectorMenu(Collector building) {
        super(building,1);
        this.building = building;
        this.currency = building.getCurrency();
        GameManager.getInstance().addFixedUpdatable(this);
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GRAY + "- " + ChatColor.BLACK + "Collector" +
                ChatColor.GRAY + " -");
    }
    @Override
    public void onClose(InventoryCloseEvent event) {
        GameManager.getInstance().removeFixedUpdatable(this);
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, getCollectItem());
        //this.getInventory().setItem(12, getStatsItem());

        this.addButton(10, collect());
        super.decorate(p);
    }
    private InventoryButton collect() {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            building.collect();
            player.closeInventory();
        });
    }
    private ItemStack getCollectItem() {
        ItemStack item = currency.getItemStack().clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Collect: ");
        meta.setLore(Arrays.asList(ChatColor.GRAY + " " + building.getAmountStored() + " " + currency.getDisplayName()));
        item.setItemMeta(meta);
        return item;
    }
    @Override
    public void fixedUpdateRequest() {
        this.getInventory().setItem(10, getCollectItem());
    }

    @Override
    public void catchUpRequest(float hours) {
        // not necessary
    }

    @Override
    public void startUpdates() {
        // not necessary
    }

    @Override
    public void stopUpdates() {
        // not necessary
    }
}
