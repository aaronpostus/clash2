package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ITroopState;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.NoTargetState;
import net.citizensnpcs.api.ai.PathStrategy;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.waypoint.LinearWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoint;
import net.citizensnpcs.trait.waypoint.WaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

public abstract class Troop {
    private final NPC npc;
    private final TroopAgent agent;
    private Path troopPath;
    public int hp;
    public ITroopState state;
    private final Raid raid;
    private final Arena arena;
    public Troop(NPC npc, TroopAgent agent, Raid raid) {
        this.npc = npc;
        this.agent = agent;
        this.raid = raid;
        this.arena = raid.getArena();
        this.state = new NoTargetState(this);
    }
    public void target() {
        troopPath = agent.target();
        PathStrategy pathStrategy = npc.getNavigator().getPathStrategy();
        npc.getNavigator().cancelNavigation();
        addWaypoints();
        arena.drawDebugPath(troopPath, Material.BLUE_STAINED_GLASS);
    }
    private void addWaypoints() {
        Waypoints waypoints = this.npc.getOrAddTrait(Waypoints.class);
        LinearWaypointProvider provider = (LinearWaypointProvider) this.npc.getOrAddTrait(Waypoints.class).getCurrentProvider();
        for(Location waypoint: this.troopPath.getWaypointsToTarget()) {
            provider.addWaypoint(new Waypoint(waypoint));
        }
    }
    public boolean hasTarget() {
        return troopPath.hasTarget();
    }
    public void skipTarget() {
        troopPath.skipCurrentTarget();
        addWaypoints();

    }
    public void playAttackAnimation() {
        PlayerAnimation.ARM_SWING.play((Player) npc.getEntity());
    }
    public void update() {
        state.update();
    }
    public void delete() {
        npc.destroy();
    }
}
