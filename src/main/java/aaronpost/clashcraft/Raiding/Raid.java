package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingStates.DefenseState;
import aaronpost.clashcraft.Buildings.BuildingStates.DestroyedState;
import aaronpost.clashcraft.Buildings.BuildingStates.IBuildingState;
import aaronpost.clashcraft.Buildings.IDefenseBuilding;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;
import aaronpost.clashcraft.Schematics.Controller;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.*;

public class Raid implements IUpdatable, IFixedUpdatable, Listener {
    //List<Building> buildingsExcludingWalls;
    private final Arena arena;
    private final IslandNavGraph navGraph;
    private final TroopManager troopManager;
    private final Island raiderIsland, victimIsland;
    private final UUID[][] raidGrid = new UUID[Arenas.NAV_GRID_X_LENGTH][Arenas.NAV_GRID_Z_LENGTH];
    public final List<Pair<Integer,Integer>> placeableIndexes;
    public final List<Pair<Integer,Integer>> adjacentIndexes;
    public boolean canPlace(int x, int z) {
        for(Pair<Integer,Integer> placable: placeableIndexes) {
            if(placable.first.equals(x) && placable.second.equals(z)) {
                return true;
            }
        }
        return false;
    }
    public boolean contains(int x, int z) {
        for(Pair<Integer,Integer> adjacent: adjacentIndexes) {
            if(adjacent.first.equals(x) && adjacent.second.equals(z)) {
                return true;
            }
        }
        return false;
    }
    public Raid(Arena arena, UUID uuid) {
        this.placeableIndexes = new ArrayList<>();
        this.adjacentIndexes = new ArrayList<>();
        this.arena = arena;
        Player raider = arena.getPlayer();
        Sessions.s.playerStates.put(raider, Sessions.PlayerState.RAIDING);
        this.raiderIsland = arena.getIsland();
        try {
            Session victimSession = ClashCraft.serializer.deserializeSession(uuid);
            this.victimIsland = victimSession.getIsland();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Building building: victimIsland.getBuildings()) {
            IBuildingState state = building.state;
            building.state = new DefenseState(building,this,state);
        }
        this.arena.assignRaid(this, this.victimIsland);
        this.victimIsland.refreshReferences(arena);
        this.victimIsland.loadBuildings();
        raider.sendMessage(Globals.prefix+" Arrived at " + Bukkit.getOfflinePlayer(uuid).getName() + "'s island.");
        GameManager.getInstance().addUpdatable(this);
        GameManager.getInstance().addFixedUpdatable(this);
        this.navGraph = new IslandNavGraph(victimIsland);

        this.troopManager = new TroopManager();
        raider.getInventory().addItem(Globals.BARBARIAN_HEAD.clone());

        for(int i=0;i<Arenas.GRID_X_LENGTH;i++) {
            for(int j= 0;j<Arenas.GRID_Z_LENGTH;j++) {
                if(victimIsland.getBuilding(i,j) != null && i < Arenas.GRID_X_LENGTH && j < Arenas.GRID_Z_LENGTH) {
                    raidGrid[i+2][j+2] = victimIsland.getBuilding(i,j).getUUID();
                }
            }
        }
        findIndexes();
        for(Pair<Integer,Integer> pair: adjacentIndexes) {
            // set to block
            arena.getAbsLocationFromNavGridLoc(pair.first, pair.second,-1).getBlock().setType(Material.MOSS_BLOCK);
        }
        //initializeRegionalTriggers();
        ClashCraft.plugin.getServer().getPluginManager().registerEvents(this, ClashCraft.plugin);
        //this.troopManager.addTroop(new Barbarian(this,x,z));
    }
//    @EventHandler
//    public void onRegionEntered(RegionEnteredEvent event) {
//        ProtectedRegion region = event.getRegion();
//        Player player = event.getPlayer();
//        System.out.println(1);
//        if(regions.containsValue(region) && troopManager.containsPlayer(player)) {
//            System.out.println(2);
//            Building building = regions.inverse().get(region);
//            if(building instanceof IDefenseBuilding) {
//                System.out.println(3);
//                ((IDefenseBuilding) building).addTroopToDamage(troopManager.getTroopFromPlayer(player));
//            }
//        }
//    }
//    public void initializeRegionalTriggers() {
//        int y = (int) arena.getLoc().getY();
//        int minY = y - 2;
//        int maxY = y + 10;
//        for(Building building: victimIsland.getBuildings()) {
//            if(!(building instanceof IDefenseBuilding)) {
//                continue;
//            }
//            System.out.println("trfygvhbjn");
//            ProtectedRegion region = new ProtectedPolygonalRegion(building.getUUID().toString(),
//                    getBuildingRhombus(building),minY,maxY);
//            region.setFlag(Flags.GREET_MESSAGE, "hello!");
//            WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Globals.world)).addRegion(region);
//            regions.put(building,region);
//        }
//
//    }

//    private List<BlockVector2> getBuildingRhombus(Building building) {
//        assert building instanceof IDefenseBuilding;
//        IDefenseBuilding defenseBuilding = (IDefenseBuilding) building;
//        Location loc = victimIsland.getCenterBuildingLoc(building, 0);
//        double x = loc.getX();
//        double z = loc.getZ();
//        float attackRange = defenseBuilding.getAttackRange();
//        List<BlockVector2> points = new ArrayList<>();
//        points.add(BlockVector2.at(x+attackRange,z));
//        points.add(BlockVector2.at(x-attackRange,z));
//        points.add(BlockVector2.at(x,z+attackRange));
//        points.add(BlockVector2.at(x,z-attackRange));
//        for(BlockVector2 bv: points) {
//            System.out.println(bv.toString());
//        }
//        return points;
//    }
    private void findIndexes() {
        // Iterate over the 2D array to find nearby and null indexes
        for (int i = 0; i < raidGrid.length; i++) {
            for (int j = 0; j < raidGrid[i].length; j++) {
                if (raidGrid[i][j] != null) {
                    // Check nearby indexes within 2 x and z coordinates
                    for (int x = i - 2; x <= i + 2; x++) {
                        for (int z = j - 2; z <= j + 2; z++) {
                            if (isValidIndex(raidGrid, x, z) && raidGrid[x][z] == null) {
                                this.adjacentIndexes.add(new Pair<Integer,Integer>(x, z));
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < raidGrid.length; i++) {
            for (int j = 0; j < raidGrid[i].length; j++) {
                if(raidGrid[i][j] == null && !contains(i,j)) {
                    this.placeableIndexes.add(new Pair<Integer,Integer>(i, j));
                }
            }
        }
    }
    private static boolean isValidIndex(UUID[][] buildings, int x, int z) {
        return x >= 2 && x < buildings.length && z >= 2 && z < buildings[x].length;
    }

    public Arena getArena() { return arena; }
    public IslandNavGraph getNavGraph() { return navGraph; }
    public void destroyBuilding(Building building) {
        // tell nav graph that building should no longer be targeted
        this.navGraph.destroy(building);
        // tell troops to find a new building
        this.troopManager.notifyTroopsOfDestroyedBuilding(building);
    }
    @Override
    public void update() {

    }
    @Override
    public void catchUpRequest(float hours) {

    }
    @Override
    public void fixedUpdateRequest() {

    }
    @Override
    public void startUpdates() {

    }
    @Override
    public void stopUpdates() {
        troopManager.deleteAllTroops();
        for(Pair<Integer,Integer> pair: adjacentIndexes) {
            // set to block
            arena.getAbsLocationFromNavGridLoc(pair.first, pair.second,-1).getBlock().setType(Material.GRASS_BLOCK);
        }
        Raids.r.raids.remove(this);
        GameManager.getInstance().removeUpdatable(this);
        GameManager.getInstance().removeFixedUpdatable(this);
        HandlerList.unregisterAll(this);
    }
    public void addTroop(Troop troop) {
        troopManager.addTroop(troop);
    }
}
