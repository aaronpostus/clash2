package us.aaronpost.clash.Schematics;


import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import us.aaronpost.clash.Clash;

import java.io.Serializable;
import java.time.LocalDate;

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
        Location loc = new Location(Clash.getPlugin().getServer().getWorld(world), x, y, z);
        return loc;
    }
    public Block getBlock() {
        return getLoc().getBlock();
    }
}
