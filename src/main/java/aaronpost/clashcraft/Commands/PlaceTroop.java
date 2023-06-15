package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Raiding.Raid;
import aaronpost.clashcraft.Raiding.Troop;
import aaronpost.clashcraft.Raiding.Troops.Barbarian;

public class PlaceTroop implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Raid raid = arena.getCurrentRaid();
        Troop troop = new Barbarian(raid, 0, 0);
        raid.addTroop(troop);
    }
}
