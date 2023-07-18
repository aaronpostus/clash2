package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.TroopAI.FollowPathGoal;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ICallback;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.ITroopState;
import aaronpost.clashcraft.Raiding.TroopAI.TroopStates.NoTargetState;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Troop implements ICallback {
    public enum TroopTypes { GROUND, AIR }
    private final NPC npc;
    private final Player troopPlayer;
    private final TroopAgent agent;
    private Path troopPath;
    public float hp;
    public ITroopState state;
    private final Raid raid;
    private final Arena arena;
    private final BukkitTask task;
    private Pair<Integer,Integer> navGridLoc;
    public Troop(NPC npc, TroopAgent agent, Raid raid) {
        this.npc = npc;
        this.troopPlayer = (Player) npc.getEntity();;
        this.agent = agent;
        this.raid = raid;
        this.arena = raid.getArena();
        this.state = new NoTargetState(this);
        this.navGridLoc = agent.getAgentPos();
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(ClashCraft.plugin, 0, 20L);
    }
    public Pair<Integer,Integer> getNavGridPos() {
        Pair<Integer,Integer> gridLoc = arena.getNavGridLocFromAbsLoc(npc.getEntity().getLocation());
        return agent.findClosestWalkableLoc(gridLoc);
    }
    public Pair<Integer,Integer> getGridPos() {
        return agent.getAgentPos();
    }
    public void callback() {
        state.callback();
    }
    public Player getTroopPlayer() {
        return (Player) npc.getEntity();
    }
    public void notifyBuildingDestroyed(Building building) {
        state.buildingDestroyed(building);
    }
    public void target() {
        Pair<Integer,Integer> pos = getNavGridPos();
        agent.setAgentPos(pos.first,pos.second);
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
        npc.faceLocation(agent.getLocationToLookAt(getTarget()));
        PlayerAnimation.ARM_SWING.play((Player) npc.getEntity());
        arena.getPlayer().playSound(npc.getEntity(), Sound.ENTITY_PLAYER_ATTACK_WEAK,4f,1f);
    }
    public void update() {
        state.update();
    }
    public void damage(float amountToDamage) {
        hp -= amountToDamage;
        if(hp <= 0) {
            //die();
            Globals.world.playEffect(troopPlayer.getLocation(), Effect.REDSTONE_TORCH_BURNOUT, 100,100);
            delete();
        }
        else {
            PlayerAnimation.HURT.play(troopPlayer);
        }
    }
    public void delete() {
        npc.destroy();
        this.task.cancel();
    }
}
