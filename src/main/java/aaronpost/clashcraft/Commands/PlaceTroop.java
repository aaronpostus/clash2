package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Globals.SkinGlobals;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;

public class PlaceTroop implements IArenaCommand {
    private SkinGlobals.Troops troopType;
    private int x, z;
    public PlaceTroop(SkinGlobals.Troops troopType, int x, int z) {
        this.troopType = troopType;
        this.x = x;
        this.z = z;
    }
    @Override
    public void execute(Arena arena) {
        // first check if we CAN place at the location
        Raid raid = arena.getCurrentRaid();
        if(!raid.canPlace(x,z)) {
            arena.sendActionBar(ChatColor.RED + "Cannot place troop here.");
            return;
        }
        arena.playSound(Sound.BLOCK_DISPENSER_DISPENSE,0.5f,1f);
        Globals.world.playEffect(arena.getAbsLocationFromNavGridLoc(x,z,0.5f), Effect.SMOKE, 100,100);
        Troop troop;
        switch (troopType) {
            case ARCHER -> troop = new Barbarian(raid, x, z);
            case BARBARIAN -> troop = new Barbarian(raid, x, z);
            default -> {
                return;
            }
        }
        raid.addTroop(troop);
    }
}
