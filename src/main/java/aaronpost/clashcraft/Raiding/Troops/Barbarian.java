package aaronpost.clashcraft.Raiding.Troops;

import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgentOptions;
import aaronpost.clashcraft.Raiding.TroopManager;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class Barbarian extends Troop {
    private NPC npc;
    private TroopAgent agent;
    public Barbarian(Raid raid, int x, int z) {
        this(createNPC(raid,x,z),createAgent(raid,x,z),raid);
    }
    private Barbarian(NPC npc, TroopAgent agent,Raid raid) {
        super(npc,agent,raid);
        this.npc = npc;
        this.agent = agent;
    }
    private static NPC createNPC(Raid raid, int x, int z) {
        Location loc = raid.getArena().getAbsLocationFromNavGridLoc(x,z,1);
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, UUID.randomUUID().toString(), loc);
        return npc;
    }
    private static TroopAgent createAgent(Raid raid, int x, int z) {
        TroopAgent agent = new TroopAgent(raid,raid.getNavGraph(),x,z);
        agent.setAgentOptions(new TroopAgentOptions());
        return agent;
    }
}
