package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.GUIS.IslandMenu;
import aaronpost.clashcraft.Interfaces.IArenaCommand;

public class OpenShopMenu implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        ClashCraft.guiManager.openGUI(new IslandMenu(),arena.getPlayer());
    }
}
