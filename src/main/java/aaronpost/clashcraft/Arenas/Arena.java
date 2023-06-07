package aaronpost.clashcraft.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
        p.getInventory().clear();
        this.player = p;
        this.session = Sessions.s.getSession(p);
        this.island = session.getIsland();
        this.island.refreshReferences(this);
        Location tpLoc = loc.clone();

        tpLoc.setX(tpLoc.getX() - 2);
        tpLoc.setZ(tpLoc.getZ());
        tpLoc.setY(tpLoc.getY());

        ItemStack shop = new ItemStack(Material.BOOK);
        ItemMeta meta = shop.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Shop");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_SHOP_ITEM, PersistentDataType.STRING, "shop");
        shop.setItemMeta(meta);

        ItemStack returnToSpawn = new ItemStack(Material.ENDER_PEARL);
        meta = returnToSpawn.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Return to Spawn");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_SPAWN_ITEM, PersistentDataType.STRING, "spawn");
        returnToSpawn.setItemMeta(meta);

        ItemStack pickUpTool = new ItemStack(Material.LEAD);
        meta = pickUpTool.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Pick Building Up");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_BLDNG_PICK_UP_ITEM, PersistentDataType.STRING, "pickup");
        pickUpTool.setItemMeta(meta);

        ItemStack openBuildingMenu = new ItemStack(Material.FLOWER_BANNER_PATTERN);
        meta = openBuildingMenu.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Open Building Menu");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_BLDNG_MENU_ITEM, PersistentDataType.STRING, "buildingMenu");
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

                Building buildingInHand = island.getBuildingInBuildingInHand();

                // If player has a NEW building that they haven't placed down, this will give it back to them.
                if(buildingInHand != null) {
                    p.getInventory().addItem(buildingInHand.getItemStack());
                }
                island.startUpdates();
                GameManager.getInstance().addFixedUpdatable(island);
                GameManager.getInstance().addUpdatable(island);
                session.refreshScoreboard();
                float hoursOffline = session.retrieveHoursOffline();
                if(hoursOffline > 0) {
                    island.catchUpRequest(hoursOffline);
                }
                p.setAllowFlight(true);
            }
        },  10);

    }
    public void purchaseNewBuilding(Building building) {
        island.putBuildingInHand(building);
        // Gets building ItemStack with formatted lore, and gives it to player
        player.getInventory().addItem(building.getItemStack());
    }
    public void sendActionBar(String str) {
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(str));
    }
    public boolean isValidGridLocation(Location loc) {
        Pair<Integer,Integer> gridPos = getGridLocFromAbsLoc(loc);
        return 0 <= gridPos.first && gridPos.first < Arenas.GRID_X_LENGTH &&
                0 <= gridPos.second && gridPos.second < Arenas.GRID_Z_LENGTH;
    }
    public boolean isValidGridLocation(int x, int z) {
        return x < Arenas.GRID_X_LENGTH && z < Arenas.GRID_Z_LENGTH && x >= 0 && z >= 0;
    }
    public Pair<Integer, Integer> getGridLocFromAbsLoc(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        return new Pair<Integer,Integer>((int)(loc.getX() - loc2.getX()), (int)(loc.getZ() - loc2.getZ()));
    }
    public void unassign() {
        GameManager.getInstance().removeFixedUpdatable(island);
        GameManager.getInstance().removeUpdatable(island);
        Arenas.a.sendToSpawn(player);
        player.getInventory().clear();
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
