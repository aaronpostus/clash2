package aaronpost.clashcraft.Buildings.BuildingMenus;

import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Currency.Elixir;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Author: Aaron Post
public class BarracksTrainMenu extends InventoryGUI {
    private final ItemStack barbarianHead = Globals.BARBARIAN_HEAD.clone();
    private final ItemStack archerHead = Globals.ARCHER_HEAD.clone();
    private Map<String,Integer> amountToTrain = new HashMap<>();
    private Elixir elixir;
    private Barracks barracks;
    public BarracksTrainMenu(Barracks barracks, Player player) {
        super("Barracks");
        amountToTrain.put("Barbarian",0);
        amountToTrain.put("Archer",0);
        this.elixir = (Elixir) Sessions.s.getSession(player).getCurrency("elixir");
    }
    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 3 * 9, name);
    }


    @Override
    public void decorate(Player player) {
        int inventorySize = this.getInventory().getSize();
        this.getInventory().setItem(1,barbarianHead);
        this.getInventory().setItem(2,archerHead);

        this.addButton(10, createTroopButton("Barbarian", 10));
        this.addButton(11, createTroopButton("Archer", 11));

        updateTrainItemStack();

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
                   Location loc = player.getEyeLocation();
                   int amount = amountToTrain.get(key);
                   switch (event.getClick()) {
                       case LEFT:
                           if(amount < 64) {
                               amountToTrain.put(key,++amount);
                               if(amount == 1) {
                                   player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_PLING, 0.25f,1f);
                               }
                               else {
                                   player.playSound(loc, Sound.BLOCK_DISPENSER_DISPENSE, 0.25f,1f);
                               }

                           }
                           break;
                       case RIGHT:
                           if(amount > 0) {
                               amountToTrain.put(key,--amount);
                               if(amount == 0) {
                                   player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_PLING, 0.25f,-1f);
                               }
                               else {
                                   player.playSound(loc, Sound.BLOCK_DISPENSER_DISPENSE, 0.25f,1f);
                               }

                           }
                           break;
                   }
                   this.getInventory().setItem(index,getTroopItemStack(key));
                   updateTrainItemStack();
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
    private int getHousingSpace() {
        //todo
        return 0;
    }
    private int getCost() {
        return 0;
    }
    private void updateTrainItemStack() {
        //if(island.canAccommodateHousing())
        ItemStack trainItem = Globals.CAN_TRAIN_ITEM.clone();
        ItemMeta meta = trainItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "Train");
        }
        List<String> lore = new ArrayList<>();
        lore.add("Troops");
        if (meta != null) {
            meta.setLore(lore);
        }
        trainItem.setItemMeta(meta);
        this.getInventory().setItem(26,trainItem);
    }
}
