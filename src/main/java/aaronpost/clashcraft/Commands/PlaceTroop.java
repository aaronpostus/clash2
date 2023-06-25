package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.SkinGlobals;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;

public class PlaceTroop implements IArenaCommand {
    private SkinGlobals.Troops troopType;
    private int x, z;
    public PlaceTroop(SkinGlobals.Troops troopType, int x, int z) {
        System.out.println(x + " " + z);
        this.troopType = troopType;
        this.x = x;
        this.z = z;
    }
    @Override
    public void execute(Arena arena) {
        // first check if we CAN place at the location
        Raid raid = arena.getCurrentRaid();
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
