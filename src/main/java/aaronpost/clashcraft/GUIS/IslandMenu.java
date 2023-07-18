package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class IslandMenu extends InventoryGUI {
    private Player p;
    public IslandMenu() {
        super(ChatColor.GOLD + "+ Shop +");
    }

    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 27, name);
    }
    @Override
    public void decorate(Player p) {
        this.p = p;

        this.getInventory().setItem(10, Globals.ARMY_SHOP_ICON.clone());
        this.addButton(10,createSubmenuButton("army"));

        this.getInventory().setItem(12,Globals.RESOURCE_SHOP_ICON.clone());
        this.addButton(12,createSubmenuButton("resources"));

        this.getInventory().setItem(14,Globals.DEFENSE_SHOP_ICON.clone());
        this.addButton(14,createSubmenuButton("defenses"));

        this.getInventory().setItem(16,GUIHelper.attachComingSoonLore(Globals.TRAP_SHOP_ICON.clone()));

        GUIHelper.fillEmptyGUISpots(getInventory(),27);
    }
    private InventoryButton createSubmenuButton(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            Arena a = Arenas.a.findPlayerArena(p);
            switch(key) {
                case "army":
                    ClashCraft.guiManager.openGUI(new ArmyMenu(a),p);
                    break;
                case "resources":
                    ClashCraft.guiManager.openGUI(new ResourceMenu(a),p);
                    break;
                case "defenses":
                    ClashCraft.guiManager.openGUI(new DefenseMenu(a),p);
                    break;
            }
        });
    }
}
