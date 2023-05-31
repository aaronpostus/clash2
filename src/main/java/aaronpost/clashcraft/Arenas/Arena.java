package aaronpost.clashcraft.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Arena {
    private final Location loc;
    private Player player = null;
    private Island island = null;
    private Session session = null;

    public Arena(Location loc) {
        this.loc = loc;
    }

    public Player getPlayer() {
        return player;
    }

    public void assignPlayer(Player p) {

        player = p;
        session = Sessions.s.getSession(p);
        island = session.getIsland();
        island.setArena(this);
        island.setPlayer(p);
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

        Bukkit.getScheduler().runTaskLater(ClashCraft.plugin, new Runnable() {
            @Override
            public void run() {
                p.teleport(tpLoc);
                p.getInventory().setItem(0, shop);
                p.getInventory().setItem(6, openBuildingMenu);
                p.getInventory().setItem(7, pickUpTool);
                p.getInventory().setItem(8, returnToSpawn);
                p.setGameMode(GameMode.ADVENTURE);

                Building buildingInHand = island.getBuildingInHand();

                // If player has a NEW building that they haven't placed down, this will give it back to them.
                if(buildingInHand != null) {
                    p.getInventory().addItem(buildingInHand.getPlainItemStack());
                }
                island.startUpdates();
                Sessions.s.getSession(p).initializeScoreboard(player);

                p.setAllowFlight(true);
            }
        },  10);
        GameManager.getInstance().addFixedUpdatable(island);
        GameManager.getInstance().addUpdatable(island);
    }
    public boolean isValidGridLocation(Location loc) {
        Pair<Double,Double> gridPos = getGridLocFromAbsLoc(loc);
        return 0 <= gridPos.first && gridPos.first < Arenas.GRID_X_LENGTH &&
                0 <= gridPos.second && gridPos.second < Arenas.GRID_Z_LENGTH;
    }
    public Pair<Double, Double> getGridLocFromAbsLoc(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        return new Pair<Double,Double>(loc.getX() - loc2.getX(), loc.getZ() - loc2.getZ());
    }
    public void unassign() {
        GameManager.getInstance().removeUpdatable(island);
        GameManager.getInstance().removeUpdatable(island);
        this.player.setAllowFlight(false);
        this.island.stopUpdates();
        this.player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.session.saveLogOffTime();
        this.player = null;
        this.island = null;
        this.session = null;
    }

    public Location getLoc() {
        return loc;
    }

    public Island getIsland() {
        return island;
    }
}
