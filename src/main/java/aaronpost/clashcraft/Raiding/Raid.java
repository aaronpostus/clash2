package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Raid implements IUpdatable, IFixedUpdatable {
    //List<Building> buildingsExcludingWalls;
    private final Arena arena;
    private final Island raiderIsland;
    private Session victimSession;
    private Island victimIsland;
    private final Player raider;
    private IslandNavGraph navGraph;
    public Raid(Arena arena, UUID uuid, int x, int z) {
        this.arena = arena;
        this.raider = arena.getPlayer();
        this.raiderIsland = arena.getIsland();
        try {
            this.victimSession = ClashCraft.serializer.deserializeSession(uuid);
            this.victimIsland = victimSession.getIsland();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.arena.assignRaid(this, victimIsland);
        this.victimIsland.refreshReferences(arena);
        this.victimIsland.loadBuildings();
        this.raider.sendMessage(Globals.prefix+" Arrived at " + Bukkit.getOfflinePlayer(uuid).getName() + "'s island.");
        GameManager.getInstance().addUpdatable(this);
        GameManager.getInstance().addFixedUpdatable(this);
        this.navGraph = new IslandNavGraph(victimIsland);
        TroopAgent agent = new TroopAgent(arena,navGraph,x,z);
        List<Building> cheapestBuildings = agent.pickBuildings();
        for(Building building1: cheapestBuildings) {
            ClashCraft.plugin.getLogger().info(building1.getDisplayName());
        }
        Building selectedBuilding = agent.pickBuilding(cheapestBuildings);
        //ClashCraft.plugin.getLogger().info("Selected " + selectedBuilding.getDisplayName());
        //List<Building> buildings = raiderIsland.getBuildings();
        //remove walls from this

    }

    @Override
    public void update() {
        //this.navGraph.refreshNavMesh();
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
        GameManager.getInstance().removeUpdatable(this);
        GameManager.getInstance().removeFixedUpdatable(this);
    }
}
