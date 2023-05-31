package aaronpost.clashcraft.Interfaces;
import org.bukkit.inventory.ItemStack;

public interface IDisplayable {
    /** Retreive an itemstack with a nicely formatted name and icon **/
    public ItemStack getItemStack();
    /** Retreive a string with a nicely formatted name **/
    public String getDisplayName();
}
