package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Interfaces.IArenaCommand;

public class ReturnToSpawn implements IArenaCommand {

    @Override
    public void execute(Arena arena) {
        System.out.println("teiowjs df");
        arena.getPlayer().performCommand("island");
    }
}
