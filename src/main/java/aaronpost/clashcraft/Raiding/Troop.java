package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.TroopAI.FollowPathGoal;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ICallback;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ITroopState;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.NoTargetState;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Troop implements ICallback {
    private final NPC npc;
    private final TroopAgent agent;
    private Path troopPath;
    public int hp;
    public ITroopState state;
    private final Raid raid;
    private final Arena arena;
    public Troop(NPC npc, TroopAgent agent, Raid raid) {
        this.npc = npc;
        this.npc.getNavigator().getLocalParameters().speedModifier(200);
        this.agent = agent;
        this.raid = raid;
        this.arena = raid.getArena();
        this.state = new NoTargetState(this);
    }
    public void refreshLocation() {
        Pair<Integer,Integer> gridLoc = arena.getNavGridLocFromAbsLoc(npc.getEntity().getLocation());
        agent.findClosestWalkableLoc(gridLoc);
    }
    public Pair<Integer,Integer> getGridPos() {
        return agent.getAgentPos();
    }
    public void callback() {
        state.callback();
    }
    public void notifyBuildingDestroyed(Building building) {
        state.buildingDestroyed(building);
    }
    public void target() {
        refreshLocation();
        troopPath = agent.target();
        npc.getNavigator().cancelNavigation();
        addWaypoints();
        arena.drawDebugPath(troopPath, Material.BLUE_STAINED_GLASS);
    }
    public void lookAt(Building building) {
        npc.faceLocation(agent.getLocationToLookAt(building));
    }
    private void addWaypoints() {
        npc.getDefaultGoalController().clear();
        npc.getDefaultGoalController().addGoal(FollowPathGoal.createFromLocations(npc,troopPath.getWaypoints(),this),1000);
    }
    public boolean hasTarget() {
        return troopPath.hasTarget();
    }
    public void skipTarget() {
        troopPath.skipCurrentTarget();
        addWaypoints();
    }
    public Building getTarget() {
        return troopPath.getTarget();
    }
    public void playAttackAnimation() {
        //npc.faceLocation(agent.getLocationToLookAt(getTarget()));
        PlayerAnimation.ARM_SWING.play((Player) npc.getEntity());
        arena.getPlayer().playSound(npc.getEntity(), Sound.ENTITY_PLAYER_ATTACK_WEAK,4f,1f);
    }
    public void update() {
        state.update();
    }
    public void delete() {
        npc.destroy();
    }
}
