package aaronpost.clashcraft.Raiding.Troops;

import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgent;
import aaronpost.clashcraft.Raiding.TroopAI.TroopAgentOptions;

public class Barbarian extends Troop {
    public Barbarian(Raid raid, int x, int z) {
        TroopAgent agent = new TroopAgent(raid.getArena(),raid.getNavGraph(),x,z);
        agent.setAgentOptions(new TroopAgentOptions());
        super.setAgent(agent);
    }
}
