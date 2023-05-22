package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Currency.Currency;
import org.bukkit.inventory.ItemStack;

public abstract class Collector extends Building {
    // reference to player's currency that we will add to
    public Currency currency;
    // collection rate. child should change this.
    public float collectionRatePerHour = 1;
    public int maxStorage = 10;
    private float amount = 0f;
    public void collect() {
        boolean storageFull = currency.deposit(getAmountStored());
        if(storageFull) {
            // add a message that tells the player that their storages are full
        }
        amount = 0f;
    }
    public int getAmountStored() {
        return (int) Math.ceil(amount);
    }
    public void fixedUpdate() {
        amount += (collectionRatePerHour / 60 / 60);
        if(amount > maxStorage) {
            amount = maxStorage;
        }
        else {
            // update some visual indicator that shows gold collected
            System.out.println("Gold Mine Collection: " + getAmountStored() + "/" + maxStorage);
        }
    }
    public void setCollectionRate(float collectionRate) {
        this.collectionRatePerHour = collectionRate;
    }
}
