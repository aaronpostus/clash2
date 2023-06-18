package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Currency.Elixir;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

// Author: Aaron Post
public class BarracksMenu extends InventoryGUI {
    private final ItemStack barbarianHead = Globals.BARBARIAN_HEAD.clone();
    private final ItemStack archerHead = Globals.ARCHER_HEAD.clone();
    private Map<String,Integer> amountToTrain = new HashMap<>();
    private Elixir elixir;
    private Barracks barracks;
    public BarracksMenu(Barracks barracks, Player player) {
        amountToTrain.put("Barbarian",0);
        amountToTrain.put("Archer",0);
        this.elixir = (Elixir) Sessions.s.getSession(player).getCurrency("elixir");
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3 * 9, "Barracks");
    }


    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        this.getInventory().setItem(1,barbarianHead);
        this.getInventory().setItem(2,archerHead);

        this.addButton(10, createTroopButton("Barbarian", 10));
        this.addButton(11, createTroopButton("Archer", 11));

        super.decorate(player);
    }

//    private InventoryButton createTrainButton(int index) {
//
//    }
    private InventoryButton createTroopButton(String key, int index) {
        return new InventoryButton()
                .creator(player -> getTroopItemStack(key))
                .consumer(event -> {
                   Player player = (Player) event.getWhoClicked();
                   int amount = amountToTrain.get(key);
                   switch (event.getClick()) {
                       case LEFT:
                           if(amount < 64) {
                               amountToTrain.put(key,++amount);
                           }
                           break;
                       case RIGHT:
                           if(amount > 0) {
                               amountToTrain.put(key,--amount);
                           }
                           break;
                   }
                   this.getInventory().setItem(index,getTroopItemStack(key));
                   player.sendMessage("barbarian");
                });
    }
    private ItemStack getTroopItemStack(String key) {
        int amountToTrain = this.amountToTrain.get(key);
        if(amountToTrain == 0) {
            return Globals.NOT_TRAINING.clone();
        }
        ItemStack stack = Globals.TRAINING.clone();
        stack.setAmount(amountToTrain);
        return stack;
    }
//    private ItemStack getTrainItemStack(String key) {
//
//    }
}
