package aaronpost.clashcraft.Buildings.NPC;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ICallback;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WanderNPC implements IFixedUpdatable {
    private List<Location> locations;
    private Island island;
    private Arena arena;
    private Random random;
    private Building building;
    private NPC npc;
    public WanderNPC(Building building, NPC npc) {
        this.arena = building.getArena();
        this.island = arena.getIsland();
        this.building = building;
        this.random = new Random();
        this.npc = npc;
    }
    @Override
    public void fixedUpdateRequest() {
        if(random.nextInt(12) == 1) {
            this.npc.getNavigator().setTarget(locations.get(random.nextInt(locations.size())));
        }
    }

    @Override
    public void catchUpRequest(float hours) {

    }

    @Override
    public void startUpdates() {
        List<Pair<Integer,Integer>> gridLocs = island.getOuterRing(building);
        locations = new ArrayList<>();
        for(Pair<Integer,Integer> gridLoc: gridLocs) {
            locations.add(arena.getAbsLocationFromGridLoc(gridLoc.first,gridLoc.second,0));
        }
        this.npc.teleport(locations.get(random.nextInt(locations.size())), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Override
    public void stopUpdates() {

    }

}
