package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Raiding.Raids;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SearchForRaid implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Player player = arena.getPlayer();
        boolean foundRaid = Raids.r.tryRaid(player);
        if(foundRaid) {
            arena.sendActionBar(ChatColor.GREEN+"Found a raid.");
            arena.playSound(Sound.ENTITY_BAT_TAKEOFF,0.5f,1f);
            return;
        }
        arena.sendActionBar(ChatColor.RED+"No islands to raid. Try again later.");
        arena.playSound(Sound.BLOCK_NOTE_BLOCK_BASS,1f,1f);

    }
}
