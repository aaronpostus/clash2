package aaronpost.clashcraft.Raiding;

import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.waypoint.*;
import net.citizensnpcs.trait.waypoint.triggers.WaypointTrigger;
import org.bukkit.Location;

import java.util.Iterator;

public class WaypointEnd implements WaypointTrigger {
    private final Troop troop;
    private final LinearWaypointProvider provider;
    public WaypointEnd(Troop troop, LinearWaypointProvider provider) {
        this.troop = troop;
        this.provider = provider;
    }
    @Override
    public String description() {
        return "stops navigation when the last waypoint is reached";
    }

    @Override
    public void onWaypointReached(NPC npc, Location location) {
        Iterator<Waypoint> iterator = ((WaypointProvider.EnumerableWaypointProvider) provider).waypoints().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        troop.playAttackAnimation();
    }
}
