package aaronpost.clashcraft;
import org.bukkit.inventory.ItemStack;

public class Displayable {
    ItemStack itemStack;
    String name;
    /** Retreive an itemstack with a nicely formatted name and icon **/
    public ItemStack getItem() {
        return itemStack.clone();
    }
    /** Retreive a string with a nicely formatted name **/
    public String getName() {
        return name;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public void setDisplayName(String name) {
        this.name = name;
    }
}
