package aaronpost.clashcraft.Buildings.BuildingMenus;

import aaronpost.clashcraft.Buildings.Collector;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Singletons.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CollectorMenu extends InventoryGUI implements IFixedUpdatable {
    private final Collector building;
    private final Currency currency;
    public CollectorMenu(Collector building) {
        this.building = building;
        this.currency = building.getCurrency();
        GameManager.getInstance().addFixedUpdatable(this);
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GRAY + "- " + "Gold Mine" +
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
        this.getInventory().setItem(16, building.getUpgradeItem());

        this.addButton(10, collect());
        this.addButton(16, upgrade());
    }
    private ItemStack getUpgradeItem() {
        return building.getUpgradeItem();
    }

    private InventoryButton upgrade() {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            building.upgrade();
            player.closeInventory();
        });
    }
    private InventoryButton collect() {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            building.collect();
            player.closeInventory();
        });
    }
    //private ItemStack getStatsItem() {

    //}
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
