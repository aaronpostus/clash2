package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Raiding.Raids;

public class SearchForRaid implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Raids.r.tryRaid(arena.getPlayer());
    }
}
