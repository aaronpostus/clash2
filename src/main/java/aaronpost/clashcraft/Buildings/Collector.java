package aaronpost.clashcraft.Buildings;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import org.bukkit.ChatColor;

// Author: Aaron Post
public abstract class Collector extends Building {
    // reference to player's currency that we will add to
    private transient Currency currency;
    private float amount = 0f;
    public Collector(Currency currency) {
        super();
        this.currency = currency;
    }
    public Collector(Currency currency, int x, int z) {
        super(x,z);
        this.currency = currency;
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
            // add a message that tells the player that their storages are full
        }

        amount = 0f;
    }
    public void catchUp(float hoursPassed) {
        tryToFill((float) hoursPassed * getCollectionRate());
    }
    public void startUpdates() {
        this.currency = super.getSession().getGold();
    }
    public void stopUpdates() {

    }
    public int getAmountStored() {
        return (int) Math.ceil(amount);
    }
    public void islandModeUpdate() {
        tryToFill(getCollectionRate() * BuildingGlobals.SECONDS_TO_HOURS);
    }
    private void tryToFill(float amountToFill) {
        amount += amountToFill;
        if(amount > getCapacity()) {
            amount = getCapacity();
        }
    }
}
