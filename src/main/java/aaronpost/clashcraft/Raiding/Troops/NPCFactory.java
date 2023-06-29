package aaronpost.clashcraft.Raiding.Troops;

import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Globals.SkinGlobals;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class NPCFactory {
    public static NPC createBarbarianNPC(Location loc, int level) {
        NPC npc =  CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.GOLD + "Barbarian " +
                ChatColor.GRAY + "- Lvl " + level, loc);
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent("",
                Globals.BARBARIAN_SIGNATURE[0],Globals.BARBARIAN_VALUE[0]);
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
        return npc;
    }
    public static NPC createArcherNPC(Location loc, int level) {
        return null;
    }
    public static NPC createBuilderNPC(Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.GRAY + "Builder", loc);
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent("",
                SkinGlobals.BUILDER_SIGNATURE,SkinGlobals.BUILDER_VALUE);
        return npc;
    }
}
