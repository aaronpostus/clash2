package aaronpost.clashcraft.Input;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Commands.*;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Globals.SkinGlobals;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
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
    private final Map<String, SkinGlobals.Troops> troopsMap;
    private final IArenaCommand leftClick = new LeftClickBuilding();
    public Interaction() {
        interactions = new HashMap<>();
        troopsMap = new HashMap<>();
        for(SkinGlobals.Troops t: SkinGlobals.Troops.values()) {
            troopsMap.put(t.toString(),t);
        }
        interactions.put(Globals.NM_KEY_SHOP_ITEM, new OpenShopMenu());
        interactions.put(Globals.NM_KEY_SPAWN_ITEM, new ReturnToSpawn());
        interactions.put(Globals.NM_KEY_BLDNG_PICK_UP_ITEM, new PickBuildingUp());
        interactions.put(Globals.NM_KEY_BLDNG_MENU_ITEM, new OpenBuildingMenu());
        interactions.put(BuildingGlobals.NAMESPACED_KEY_IDENTIFIER, new PlaceBuilding());
        interactions.put(Globals.NM_KEY_RAID, new SearchForRaid());
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
    public void onPlayerPickUp(EntityPickupItemEvent e) {
        Entity entity = e.getEntity();
        if(!entity.getType().equals(EntityType.PLAYER)) {
            return;
        }
        if(Arenas.a.playerAtArena((Player) entity)) {
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
        if(state == Sessions.PlayerState.RAIDING) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                PersistentDataContainer data = meta.getPersistentDataContainer();
                for (String troop : troopsMap.keySet()) {
                    if (data.has(Globals.NM_KEY_PLACE_TROOP, PersistentDataType.STRING)) {
                        String key = data.get(Globals.NM_KEY_PLACE_TROOP, PersistentDataType.STRING);
                        if (troopsMap.containsKey(key)) {
                            SkinGlobals.Troops troopType = troopsMap.get(key);
                            Block block = player.getTargetBlockExact(500);
                            if (block != null) {
                                Location loc = block.getLocation();
                                if (!arena.isValidNavGridLocation(loc)) {
                                    return;
                                }
                                Pair<Integer, Integer> gridLoc = arena.getNavGridLocFromAbsLoc(loc);
                                new PlaceTroop(troopType, gridLoc.first, gridLoc.second).execute(arena);
                                return;
                            }
                        }
                    }
                }
            }
            //return;
        }
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