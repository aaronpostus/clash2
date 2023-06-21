package aaronpost.clashcraft.Raiding.Troops;

import aaronpost.clashcraft.Globals.Globals;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class TroopNPCFactory {
    public static NPC createBarbarianNPC(Location loc, int level) {
        NPC npc =  CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.GOLD + "Barbarian " +
                ChatColor.GRAY + "- Lvl " + level, loc);
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent("",
                Globals.BARBARIAN_SIGNATURE[level-1],Globals.BARBARIAN_VALUE[level-1]);
        return npc;

    }
}
