package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.UUID;

public class Arena {
    private final Location loc;
    private Player player = null;
    private Island island = null;

    public Arena(Location loc) {
        this.loc = loc;
    }

    public Player getPlayer() {
        return player;
    }

    public void assignPlayer(Player p) {

        player = p;
        island = Sessions.s.getSession(p).getIsland();
        Location tpLoc = loc.clone();

        tpLoc.setX(tpLoc.getX() - 2);
        tpLoc.setZ(tpLoc.getZ());
        tpLoc.setY(tpLoc.getY());

        ItemStack shop = new ItemStack(Material.BOOK);
        ItemMeta meta = shop.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Shop");
        shop.setItemMeta(meta);

        ItemStack returnToSpawn = new ItemStack(Material.ENDER_PEARL);
        meta = returnToSpawn.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Return to Spawn");
        returnToSpawn.setItemMeta(meta);

        ItemStack pickUpTool = new ItemStack(Material.LEAD);
        meta = pickUpTool.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Pick Building Up");
        pickUpTool.setItemMeta(meta);

        ItemStack openBuildingMenu = new ItemStack(Material.FLOWER_BANNER_PATTERN);
        meta = openBuildingMenu.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Open Building Menu");
        openBuildingMenu.setItemMeta(meta);

        Bukkit.getScheduler().runTaskLater(Clash.getPlugin(), new Runnable() {
            @Override
            public void run() {
                p.teleport(tpLoc);
                p.getInventory().setItem(0, shop);
                p.getInventory().setItem(6, openBuildingMenu);
                p.getInventory().setItem(7, pickUpTool);
                p.getInventory().setItem(8, returnToSpawn);
                p.setGameMode(GameMode.ADVENTURE);

                BuildingInHand buildingInHand = island.getBuildingInHand();

                // If player has a NEW building that they haven't placed down, this will give it back to them.
                if(buildingInHand != null) {
                    p.getInventory().addItem(buildingInHand.getBuilding().getItemStack());
                }

                loadBuildings();

                Sessions.s.getSession(p).initializeScoreboard(player);

                p.setAllowFlight(true);
            }
        },  10);
    }
    public Island getIsland() {
        return island;
    }
    public void loadBuildings() {
        for(Building building: island.getBuildings()) {
            player.sendMessage(ChatColor.GRAY + "Loaded " + building.getItemName() + ChatColor.GRAY
                    + " Level " + building.getLevel());
            building.paste(this);
        }
        if(island.getBuildingInHand() != null) {
            player.sendMessage(ChatColor.GRAY + "You have a building in your hand: " +
                    island.getBuildingInHand().getBuilding().getItemName());
        }
    }
    public boolean isValidGridLocation(Location loc) {
        double xDifference = getRelativeX(loc);
        if(0 <= xDifference && xDifference < Arenas.GRID_X_LENGTH) {
            double zDifference = getRelativeZ(loc);
            if(0 <= zDifference && zDifference < Arenas.GRID_Z_LENGTH) {
                return true;
            }
        }
        return false;
    }
    // Needs to account for fitting within the 40x40 grid, not colliding with other buildings, but can "collide" with itself.
    public boolean fitSquareAtGridLoc(Location loc, int s, UUID buildingUUID) {
        String buildingUUIDasString = buildingUUID.toString();
        // Get relative x and z positions
        int x = (int) getRelativeX(loc);
        int z = (int) getRelativeZ(loc);
        //Clash.getPlugin().getServer().getPlayer("Aaronn").sendMessage(ChatColor.GRAY + "          Relative X: " + x + " Relative Z: " + z);
        if(x + s <= Arenas.GRID_X_LENGTH && z + s <= Arenas.GRID_Z_LENGTH && x >= 0 && z >= 0) {
            for (int i = x; i < (x + s); i++) {
                for (int j = z; j < (z + s); j++) {
                    UUID uuid = island.getGridPosUUID(i, j);
                    // Checks if that spot on the grid is already occupied
                    if(uuid != null) {
                        String uuidAsString = uuid.toString();
                        // Check if that spot is occupied by a building other than that of the provided UUID
                        if ( !(uuidAsString.equals(buildingUUIDasString)) ) {
                            return false;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    // Needs to account for fitting within the 40x40 grid, not colliding with other buildings, but can "collide" with itself.
    public boolean fitBuildingAtGridLoc(Building b) {
        // Get relative x and z positions
        int x = (int) Math.ceil(getRelativeX(loc));
        int z = (int) Math.ceil(getRelativeZ(loc));
        ClashCraft.plugin.getServer().getPlayer("Aaronn").sendMessage("relative x: " + x + " relative z: " + z);
        if(x + b.getGridLengthX() <= Arenas.GRID_X_LENGTH && z + b.getGridLengthZ() <= Arenas.GRID_Z_LENGTH && x >= 0 && z >= 0) {
            for (int i = x; i < (x + b.getGridLengthX()); i++) {
                for (int j = z; j < (z + b.getGridLengthZ()); j++) {
                    UUID uuid = island.getGridPosUUID(i, j);
                    if (uuid != null) {
                        if (uuid != b.getUUID()) {
                            return false;
                        }
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
    public Building findBuildingAtLocation(Location targetBlock) {
        if(isValidGridLocation(targetBlock)) {
            int x = (int) getRelativeX(targetBlock);
            int z = (int) getRelativeZ(targetBlock);
            UUID uuid = island.getGridPosUUID(x, z);
            if (uuid != null) {
                Building building = island.getBuildingFromUUID(uuid);
                if (building != null) {
                    return building;
                }
            }
        }
        return null;
    }

    public double getRelativeX(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        return loc.getX() - loc2.getX();
    }
    public double getRelativeZ(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        return loc.getZ() - loc2.getZ();
    }
    public void unassign() {
        player.setAllowFlight(false);
        for(Building building: island.getBuildings()) {
            building.resetToGrass(this);
        }
        BuildingInHand buildingInHand = island.getBuildingInHand();
        if(buildingInHand != null) {
            Building building = buildingInHand.getBuilding();
            if(building != null) {
                if(!building.isNewBuilding()) {
                    island.addBuilding(building);
                    island.removeBuildingInHand();
                }
            }
        }
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        player = null;
        island = null;
    }

    public Location getLoc() {
        return loc;
    }
}
