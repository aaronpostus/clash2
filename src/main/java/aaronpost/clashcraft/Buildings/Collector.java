package aaronpost.clashcraft.Buildings;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.CollectorMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

// Author: Aaron Post
public abstract class Collector extends Building {
    // reference to player's currency that we will add to
    private transient Currency currency;
    private float amount = 0f;
    public Collector(Arena arena, Currency currency) {
        super(arena);
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
            getArena().playSound(Sound.BLOCK_NOTE_BLOCK_BASS,1f,1f);
        }
        else {
            getArena().playSound(Sound.BLOCK_AMETHYST_BLOCK_BREAK,1f,1f);
        }

        amount = 0f;
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
    @Override
    public void startUpdates() {
        this.currency = super.getSession().getCurrency("gold");
    }
    public void stopUpdates() {

    }
    public int getAmountStored() {
        return (int) Math.floor(amount);
    }
    @Override
    public void update() {

        tryToFill(getCollectionRate() * BuildingGlobals.SECONDS_TO_HOURS);
    }
    private void tryToFill(float amountToFill) {
        amount += amountToFill;
        if(amount > getCapacity()) {
            amount = getCapacity();
        }
    }
}
