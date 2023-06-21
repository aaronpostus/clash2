package aaronpost.clashcraft;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Commands.*;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class Interaction implements Listener {
    private final Map<NamespacedKey, IArenaCommand> interactions;
    private final IArenaCommand leftClick = new LeftClickBuilding();
    public Interaction() {
        interactions = new HashMap<>();
        interactions.put(Globals.NM_KEY_SHOP_ITEM, new OpenShopMenu());
        interactions.put(Globals.NM_KEY_SPAWN_ITEM, new ReturnToSpawn());
        interactions.put(Globals.NM_KEY_BLDNG_PICK_UP_ITEM, new PickBuildingUp());
        interactions.put(Globals.NM_KEY_BLDNG_MENU_ITEM, new OpenBuildingMenu());
        interactions.put(BuildingGlobals.NAMESPACED_KEY_IDENTIFIER, new PlaceBuilding());
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(Arenas.a.playerAtArena(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryItemClick(InventoryClickEvent e) {
        if(Arenas.a.playerAtArena((Player) e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void HungerDeplete(FoodLevelChangeEvent e) {
        // 20 is full, 0 is empty
        e.setCancelled(true);
        e.getEntity().setFoodLevel(20);
    }
    @EventHandler
    public void onItemClick(PlayerInteractEvent interactEvent) {
        Player player = interactEvent.getPlayer();
        Sessions.PlayerState state = Sessions.s.playerStates.get(player);
        if(state == Sessions.PlayerState.DEFAULT) {
            return;
        }
        Arena arena = Arenas.a.findPlayerArena(player);
        Island island = arena.getIsland();
        interactEvent.setCancelled(true);
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            leftClick.execute(arena);
            return;
        }
        PersistentDataContainer data = meta.getPersistentDataContainer();
        for(NamespacedKey namespacedKey : interactions.keySet()) {
            if(data.has(namespacedKey, PersistentDataType.STRING)) {
                interactions.get(namespacedKey).execute(arena);
                return;
            }
        }
    }
}