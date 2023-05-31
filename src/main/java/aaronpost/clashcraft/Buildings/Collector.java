package aaronpost.clashcraft.Buildings;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;

import java.time.Duration;

// Author: Aaron Post
public abstract class Collector extends Building {
    // reference to player's currency that we will add to
    private Currency currency;
    private float amount = 0f;
    public Collector(int x, int z) {
        super(x,z);
    }
    abstract float getCollectionRate();
    abstract float getCapacity();
    public void collect() {
        boolean storageFull = currency.deposit(getAmountStored());
        if(storageFull) {
            // add a message that tells the player that their storages are full
        }
        amount = 0f;
    }
    public void catchUp(Duration time) {
        long secondsPassed = time.toSeconds();
        double hoursPassed = secondsPassed * BuildingGlobals.COLLECTION_RATE_DIVISOR;
        tryToFill((float) hoursPassed * getCollectionRate());
    }
    public void startUpdates() {

    }
    public void stopUpdates() {

    }
    public int getAmountStored() {
        return (int) Math.ceil(amount);
    }
    public void fixedUpdate() {
        tryToFill(getCollectionRate() * BuildingGlobals.COLLECTION_RATE_DIVISOR);
    }
    private void tryToFill(float amountToFill) {
        amount += amountToFill;
        if(amount > getCapacity()) {
            amount = getCapacity();
        }
    }
}
