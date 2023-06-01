package aaronpost.clashcraft.Schematics;

import aaronpost.clashcraft.ClashCraft;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schematic implements Serializable {
    private final String name;
    private final int xLength, yLength, zLength;
    private int yOffset;
    private final List<LocationWrapper> blockLocs = new ArrayList<>();
    private List<LocationWrapper> eventBlockLocs = new ArrayList<>();
    public Schematic(Block b1, Block b2, String name) {
        this.yOffset = 0;
        this.name = name;

        World world = b1.getWorld();

        // Get coords of each cuboid selection point

        int b1x = (int) Math.ceil(b1.getX());
        int b1y = (int) Math.ceil(b1.getY());
        int b1z = (int) Math.ceil(b1.getZ());

        int b2x = (int) Math.ceil(b2.getX());
        int b2y = (int) Math.ceil(b2.getY());
        int b2z = (int) Math.ceil(b2.getZ());

        // Get extrema coordinates in every direction

        int westExt = Math.min(b1x, b2x);
        int eastExt = Math.max(b1x, b2x);

        int northExt = Math.max(b1z, b2z);
        int southExt = Math.min(b1z, b2z);

        int upExt = Math.max(b1y, b2y);
        int downExt = Math.min(b1y, b2y);

        xLength = Math.abs(eastExt - westExt) + 1;
        yLength = Math.abs(upExt - downExt) + 1;
        zLength = Math.abs(northExt - southExt) + 1;

        for(int y = downExt; y <= upExt; y++) {
            for (int x = westExt; x <= eastExt; x++) {
                for(int z = southExt; z <= northExt; z++) {
                    blockLocs.add(new LocationWrapper(new Location(world, x,y,z)));
                }
            }
        }
    }
    public int getXLength() {
       return xLength;
    }
    public int getZLength() {
        return zLength;
    }
    public int getYLength() {
        return yLength;
    }
    public int getYOffset() { return yOffset; }
    public void setEventBlockLoc() {
        // Do something
    }

    public Block getEventBlockLoc(int index) {
        return this.eventBlockLocs.get(index).getBlock();
    }

    public List<LocationWrapper> getLocs() {
        return this.blockLocs;
    }

    public String getName() {
        return name;
    }

    public void setyOffset(int yOffset) {

        this.yOffset = yOffset;
    }

    public void pasteSchematic(Location origLoc) {
        Location loc = origLoc.clone();
        loc.setY(loc.getY() + yOffset);
        double initZ = loc.getZ();
        double initX = loc.getX();
        int counter = 0;
        TextComponent text = new TextComponent();
        //Clash.getPlugin().getServer().getPlayer("Aaronn").spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Pasting schmeatic " + name));
        for(int y = 0; y < yLength; y++) {
            loc.setX(initX);
            for (int x = 0; x < xLength; x++) {
                loc.setZ(initZ);
                for(int z = 0; z < zLength; z++) {
                    Block referenceBlock = blockLocs.get(counter).getBlock();
                    Block newBlock = loc.getBlock();
                    newBlock.setType(referenceBlock.getType());
                    newBlock.setBlockData(referenceBlock.getBlockData());
                    loc.setZ(loc.getZ() + 1);
                    counter++;
                }
                loc.setX(loc.getX() + 1);
            }
            loc.setY(loc.getY() + 1);
        }
    }

    public void resetToGrassLand(Location origLoc) {
        Location loc = origLoc.clone();
        loc.setY(loc.getY() + yOffset);
        double initZ = loc.getZ();
        double initX = loc.getX();
        for(int y = 0; y < yLength; y++) {
            loc.setX(initX);
            for (int x = 0; x < xLength; x++) {
                loc.setZ(initZ);
                for(int z = 0; z < zLength; z++) {
                    Block newBlock = loc.getBlock();
                    //change made was yOffset-1
                    if(y==Math.abs(yOffset)-1) {
                        newBlock.setType(Material.GRASS_BLOCK);
                    } else {
                        newBlock.setType(Material.AIR);
                    }
                    loc.setZ(loc.getZ() + 1);
                }
                loc.setX(loc.getX() + 1);
            }
            loc.setY(loc.getY() + 1);
        }
    }

    public void pasteSchematicConstruction(Location origLoc, int layersBuilt) {
        Location loc = origLoc.clone();
        loc.setY(loc.getY() + yOffset);
        double initZ = loc.getZ();
        double initX = loc.getX();
        int counter = 0;
        ClashCraft.plugin.getServer().getPlayer("Aaronn").spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Pasting schmeatic " + name));
        for(int y = 0; y < yLength; y++) {
            loc.setX(initX);
            for (int x = 0; x < xLength; x++) {
                loc.setZ(initZ);
                for(int z = 0; z < zLength; z++) {
                    Block referenceBlock = blockLocs.get(counter).getBlock();
                    Block newBlock = loc.getBlock();
                    if((y) >= layersBuilt) {
                        newBlock.setType(Material.SCAFFOLDING);
                    } else {
                        newBlock.setType(referenceBlock.getType());
                        newBlock.setBlockData(referenceBlock.getBlockData());
                    }
                    loc.setZ(loc.getZ() + 1);
                    counter++;
                }
                loc.setX(loc.getX() + 1);
            }
            loc.setY(loc.getY() + 1);
        }
    }

}
