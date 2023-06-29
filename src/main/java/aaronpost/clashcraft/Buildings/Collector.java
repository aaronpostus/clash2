package aaronpost.clashcraft.Buildings;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Aesthetics.ItemFountain;
import aaronpost.clashcraft.Buildings.BuildingMenus.CollectorMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Author: Aaron Post
public abstract class Collector extends Building {
    // reference to player's currency that we will add to
    private transient Currency currency;
    private float amount = 0f;
    private String currencyName;

    public Collector(Arena arena, Currency currency) {
        super(arena);
        this.currency = currency;
    }
    public Collector(Currency currency, int x, int z) {
        super(x,z);
        this.currency = currency;
    }
    @Override
    public ItemStack getStatsItem() {
        ItemStack stack = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(getDisplayName() + " Stats");
        List<String> lore = new ArrayList<>();
        int level = getLevel();
        String currency = " " + getCurrency().getDisplayName().toLowerCase() + " ";
        lore.add(ChatColor.GRAY + " " + (int) getCollectionRate() + currency + ChatColor.GRAY + "per hour");
        lore.add(ChatColor.GRAY + " " + (int) getCapacity() + currency + ChatColor.GRAY + "storage capacity");
        lore.add(ChatColor.GRAY + " " + ChatColor.GRAY + getMaxHitpoints() + " hitpoints");
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
    abstract float getCollectionRate();
    abstract float getCapacity();
    @Override
    public void click() {
        collect();
    }
    public void collect() {
        if(amount < 1f) {
            super.sendMessage("Nothing to collect.");
            return;
        }
        super.sendMessage("Collecting " + getAmountStored() + " " + currency.getDisplayName() + ChatColor.GRAY + ".");
        boolean storageFull = !currency.deposit(getAmountStored());
        getSession().refreshScoreboard();
        if(storageFull) {
            super.sendMessage("Storages full.");
            getArena().playSound(Sound.BLOCK_NOTE_BLOCK_BASS,1f,1f);
        }
        else {
            float intensity = 5*(amount/getCapacity());
            getArena().playSound(Sound.BLOCK_AMETHYST_BLOCK_BREAK,1f,1f);
            Location loc = getArena().getIsland().getCenterBuildingLoc(this,5);
            ItemFountain fountain = new ItemFountain(loc,getItemFountainMaterials(),intensity,3,3);
        }

        amount = 0f;
    }
    public List<Material> getItemFountainMaterials() {
        return Arrays.asList(Material.GOLD_INGOT, Material.GOLD_ORE, Material.GOLD_NUGGET, Material.COBBLESTONE);
    }
    @Override
    public void openMenu() {
        ClashCraft.guiManager.openGUI(new CollectorMenu(this), getArena().getPlayer());
    }
    public Currency getCurrency() {
        return currency;
    }
    public void catchUp(float hoursPassed) {
        tryToFill(hoursPassed * getCollectionRate());
    }
    public void setCurrencyName(String name) {
        this.currencyName = name;
    }
    @Override
    public void startUpdates() {
        this.currency = super.getSession().getCurrency(currencyName);
    }
    public int getAmountStored() {
        return (int) Math.floor(amount);
    }
    @Override
    public void update() {
        tryToFill(getCollectionRate() / 3600);
    }
    private void tryToFill(float amountToFill) {
        amount += amountToFill;
        if(amount > getCapacity()) {
            amount = getCapacity();
        }
    }
}
