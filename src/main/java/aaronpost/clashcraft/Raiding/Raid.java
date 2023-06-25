package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingStates.DefenseState;
import aaronpost.clashcraft.Buildings.BuildingStates.IBuildingState;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class Raid implements IUpdatable, IFixedUpdatable {
    //List<Building> buildingsExcludingWalls;
    private final Arena arena;
    private final IslandNavGraph navGraph;
    private final TroopManager troopManager;
    private final Island raiderIsland,victimIsland;
    public Raid(Arena arena, UUID uuid) {

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
        //this.troopManager.addTroop(new Barbarian(this,x,z));
    }
    public Arena getArena() { return arena; }
    public IslandNavGraph getNavGraph() { return navGraph; }

    @Override
    public void update() {
        this.troopManager.updateTroops();
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
        Raids.r.raids.remove(this);
        GameManager.getInstance().removeUpdatable(this);
        GameManager.getInstance().removeFixedUpdatable(this);
    }
    public void addTroop(Troop troop) {
        troopManager.addTroop(troop);
    }
}
