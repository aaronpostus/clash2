package aaronpost.clashcraft.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingStates.BuildingState;
import aaronpost.clashcraft.Buildings.BuildingStates.DefenseState;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Commands.UpdateStorageCapacity;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.Path;
import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static aaronpost.clashcraft.Arenas.Arena.ArenaState.ISLAND_STATE;
import static aaronpost.clashcraft.Arenas.Arena.ArenaState.RAID_STATE;

public class Arena {
    private final Location loc;
    private Player player = null;
    private Island island = null;
    private Session session = null;
    public enum ArenaState { ISLAND_STATE, RAID_STATE }
    public ArenaState currentState = ISLAND_STATE;
    private Raid currentRaid = null;
    private List<NPC> npcs = new ArrayList<>();
    public Arena(Location loc) {
        this.loc = loc;
    }

    public Player getPlayer() {
        return player;
    }

    public void assignPlayer(Player p) {
        this.currentState = ISLAND_STATE;
        p.getInventory().clear();
        this.player = p;
        this.session = Sessions.s.getSession(p);
        this.island = session.getIsland();
        this.island.refreshReferences(this);
        for(Building building: island.getBuildings()) {
            if(building.state instanceof DefenseState) {
                ((DefenseState) building.state).restoreState(building);
            }
        }
        Location tpLoc = loc.clone();

        tpLoc.setX(tpLoc.getX() - 2);
        tpLoc.setZ(tpLoc.getZ());
        tpLoc.setY(tpLoc.getY());

        ItemStack shop = Globals.SHOP_ITEM.clone();

        ItemStack returnToSpawn = Globals.RETURN_TO_SPAWN_ITEM.clone();

        ItemStack pickUpTool = Globals.PICK_UP_ITEM.clone();

        ItemStack openBuildingMenu = Globals.OPEN_BUILDING_MENU_ITEM.clone();

        ItemStack raidTool = Globals.RAID_ITEM.clone();
        Arena a = this;
        Bukkit.getScheduler().runTaskLater(ClashCraft.plugin, new Runnable() {
            @Override
            public void run() {
                player.sendMessage(Globals.prefix + ChatColor.GRAY + " Loaded your island.");
                p.teleport(tpLoc);
                p.getInventory().setItem(0, shop);

                p.getInventory().setItem(5, openBuildingMenu);
                p.getInventory().setItem(6, pickUpTool);
                p.getInventory().setItem(7, raidTool);
                p.getInventory().setItem(8, returnToSpawn);
                p.setGameMode(GameMode.ADVENTURE);
                playSound(Sound.ENTITY_BAT_TAKEOFF, 1f,1f);

                Building buildingInHand = island.getBuildingInBuildingInHand();

                // If player has a NEW building that they haven't placed down, this will give it back to them.
                if(buildingInHand != null) {
                    p.getInventory().addItem(buildingInHand.getItemStack());
                }
                island.startUpdates();
                GameManager.getInstance().addFixedUpdatable(island);
                GameManager.getInstance().addUpdatable(island);
                new UpdateStorageCapacity().execute(a);
                if(session.isDebugMode()) {
                    p.sendMessage(Globals.prefix + ChatColor.GRAY + " Filled resource reserves: debug mode.");
                    for(Currency currency : session.getCurrencies().values()) {
                        currency.setAmount(currency.getMaxAmount());
                    }
                }
                session.refreshScoreboard();
                float hoursOffline = session.retrieveHoursOffline();
                if(hoursOffline > 0) {
                    island.catchUpRequest(hoursOffline);
                }
                p.setAllowFlight(true);
            }
        },  10);
    }
    public void deleteNPCS() {
        for(NPC npc: npcs) {
            npc.destroy();
        }
        npcs = new ArrayList<>();
    }
    public NPC createArenaNPC(EntityType entityType, Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, UUID.randomUUID().toString(), loc);
        npcs.add(npc);
        return npc;
    }
    public Raid getCurrentRaid() {
        return currentRaid;
    }
    public void drawDebugPath(Path path, Material mat) {
        // clear previous paths
        for(int i=0; i < Arenas.NAV_GRID_X_LENGTH; i++){
            for(int j=0; j < Arenas.NAV_GRID_Z_LENGTH; j++) {
                Block block = getAbsLocationFromNavGridLoc(i,j, 9).getBlock();
                if(block.getType() == mat) {
                    block.setType(Material.AIR);
                }
            }
        }
        List<GridCell> cells = path.getCells();
        for(GridCell cell: cells){
            getAbsLocationFromNavGridLoc(cell.x, cell.y,9).getBlock().setType(Material.BLUE_STAINED_GLASS);
        }
    }
    public void setIsland(Island island) {
        this.island = island;
    }
    public void purchaseNewBuilding(Building building) {
        building.getArena().playSound(Sound.ENTITY_PLAYER_LEVELUP, 0.5f,5f);
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
    public boolean isValidNavGridLocation(Location loc) {
        Pair<Integer,Integer> gridPos = getNavGridLocFromAbsLoc(loc);
        return 0 <= gridPos.first && gridPos.first < Arenas.NAV_GRID_X_LENGTH &&
                0 <= gridPos.second && gridPos.second < Arenas.NAV_GRID_Z_LENGTH;
    }
    public boolean isValidGridLocation(int x, int z) {
        return x < Arenas.GRID_X_LENGTH && z < Arenas.GRID_Z_LENGTH && x >= 0 && z >= 0;
    }
    public Pair<Integer, Integer> getGridLocFromAbsLoc(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        return new Pair<Integer,Integer>((int)(loc.getX() - loc2.getX()), (int)(loc.getZ() - loc2.getZ()));
    }
    public Pair<Integer, Integer> getNavGridLocFromAbsLoc(Location origLoc) {
        Location loc = origLoc.clone();
        Location loc2 = this.loc.clone();
        loc2.setX(loc2.getX() - 2);
        loc2.setZ(loc2.getZ() - 2);
        return new Pair<Integer,Integer>((int)(loc.getX() - loc2.getX()), (int)(loc.getZ() - loc2.getZ()));
    }
    public void unassign() {
        this.unload();

        player.getInventory().clear();
        Arenas.a.sendToSpawn(player);
        this.player.setAllowFlight(false);
        System.out.println("currentraid" + (currentRaid != null));
        if(currentState == RAID_STATE) {
            this.currentRaid.stopUpdates();
            this.currentRaid = null;
        }

        this.player = null;
        this.island = null;
        this.session = null;
    }
    public void assignRaid(Raid raid, Island victimIsland) {
        if(currentState == RAID_STATE) {
            this.currentRaid.stopUpdates();
        }
        this.currentRaid = raid;
        this.player.getInventory().clear();
        this.unload();
        this.island = victimIsland;
        this.currentState = RAID_STATE;
    }
    public void playSound(Sound sound, float vol, float pitch) {
        player.playSound(player.getEyeLocation(), sound, vol,pitch);
    }
    public Location getAbsLocationFromNavGridLoc(int x, int z) {
        return getAbsLocationFromNavGridLoc(x,z,0);
    }
    public Location getAbsLocationFromGridLoc(int x, int z, int heightAboveGround) {
        Location loc = this.getLoc().clone();
        loc.setX(x + loc.getX());
        loc.setZ(z + loc.getZ());
        loc.setY(heightAboveGround + loc.getY());
        return loc;
    }
    public Location getAbsLocationFromNavGridLoc(int x, int z, int heightAboveGround) {
        Location loc = this.getLoc().clone();
        loc.setX(x + loc.getX() - 2);
        loc.setZ(z + loc.getZ() - 2);
        loc.setY(heightAboveGround + loc.getY());
        return loc;
    }
    public Location getAbsLocationFromNavGridLoc(int x, int z, float heightAboveGround) {
        Location loc = this.getLoc().clone();
        loc.setX(x + loc.getX() - 2);
        loc.setZ(z + loc.getZ() - 2);
        loc.setY(heightAboveGround + loc.getY());
        return loc;
    }
    public void unload() {
        GameManager.getInstance().removeFixedUpdatable(island);
        GameManager.getInstance().removeUpdatable(island);

        this.island.stopUpdates();
        this.session.saveLogOffTime();
        this.player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public Location getLoc() {
        return loc;
    }

    public Island getIsland() {
        return island;
    }
}
