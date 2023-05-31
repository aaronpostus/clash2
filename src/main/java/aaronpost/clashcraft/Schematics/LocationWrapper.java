package aaronpost.clashcraft.Schematics;


import aaronpost.clashcraft.ClashCraft;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.Serializable;

public class LocationWrapper implements Serializable {
    private double x, y, z;
    private String world;
    public LocationWrapper(Location loc) {
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        world = loc.getWorld().getName();
    }
    public Location getLoc() {
        Location loc = new Location(ClashCraft.plugin.getServer().getWorld(world), x, y, z);
        return loc;
    }
    public Block getBlock() {
        return getLoc().getBlock();
    }
}
