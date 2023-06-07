package aaronpost.clashcraft.Currency;

import aaronpost.clashcraft.Interfaces.IDisplayable;
import aaronpost.clashcraft.Session;

import java.io.Serializable;

public abstract class Currency implements Serializable, IDisplayable {
    private int amount;
    private int maxAmount;
    public int getAmount() {
        return amount;
    }
    public int getMaxAmount() {
        return maxAmount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setMaxAmount(int maxAmount) { this.amount = amount; }
    public boolean canAfford(int cost) {
        return cost >= amount;
    }
    public Currency(int amount, int maxAmount) {
        this.amount = amount;
        this.maxAmount = maxAmount;
    }
    public boolean withdraw(int cost) {
        if(canAfford(cost)) {
            amount -= cost;
            // successful withdrawal
            return true;
        }
        // can't afford. unsuccessful withdrawal
        return false;
    }
    /** deposits currency and returns false if we have exceeded the maximum storage of currency (loss of currency) **/
    public boolean deposit(int currencyToDeposit) {
        amount += currencyToDeposit;
        if(amount > maxAmount) {
            amount = maxAmount;
            return false;
        }
        return true;
    }
}
