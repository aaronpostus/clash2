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
        System.out.println(734);
        // only pauses it it doesnt clear the waypoints
        WaypointProvider.EnumerableWaypointProvider provider2 = provider;
        Iterator<Waypoint> iterator = provider2.waypoints().iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            //iterator.remove();
        }

        troop.playAttackAnimation();
        //npc.getNavigator().setPaused(true);
        // find troop class and tell it that it reached the destination
    }
}