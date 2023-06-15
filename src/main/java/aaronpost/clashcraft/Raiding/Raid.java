package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class Raid implements IUpdatable, IFixedUpdatable {
    //List<Building> buildingsExcludingWalls;
    private final Arena arena;
    private final IslandNavGraph navGraph;
    private final TroopManager troopManager;
    public Raid(Arena arena, UUID uuid, int x, int z) {
        this.arena = arena;
        Player raider = arena.getPlayer();
        Island raiderIsland = arena.getIsland();
        Island victimIsland;
        try {
            Session victimSession = ClashCraft.serializer.deserializeSession(uuid);
            victimIsland = victimSession.getIsland();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.arena.assignRaid(this, victimIsland);
        victimIsland.refreshReferences(arena);
        victimIsland.loadBuildings();
        raider.sendMessage(Globals.prefix+" Arrived at " + Bukkit.getOfflinePlayer(uuid).getName() + "'s island.");
        GameManager.getInstance().addUpdatable(this);
        GameManager.getInstance().addFixedUpdatable(this);
        this.navGraph = new IslandNavGraph(victimIsland);
        this.troopManager = new TroopManager();
        this.troopManager.addTroop(new Barbarian(this,x,z));
        //ClashCraft.plugin.getLogger().info("Selected " + selectedBuilding.getDisplayName());
        //List<Building> buildings = raiderIsland.getBuildings();
        //remove walls from this

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
        GameManager.getInstance().removeUpdatable(this);
        GameManager.getInstance().removeFixedUpdatable(this);
    }
    public void addTroop(Troop troop) {
        troopManager.addTroop(troop);
    }
}
