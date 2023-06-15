package aaronpost.clashcraft;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Commands.*;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
    public void onItemClick(PlayerInteractEvent interactEvent) {
        Player player = interactEvent.getPlayer();
        if(!Arenas.a.playerAtArena(player)) {
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

    //WILL ONLY CONNECT TO WALLS OF THE SAME TYPE! THE SECOND ARGUMENT NEEDS TO BE UPDATED TO A LIST OF ALL WALL MATERIALS
    public void updateFence(Block b, Material M) {
        Fence f = ((Fence) b.getBlockData());
        Location l = b.getLocation();
        int x = b.getX();
        int z = b.getZ();
        l.setZ(z-1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.NORTH,false);
        }
        l.setZ(z+1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.SOUTH,false);
        }
        l.setZ(z);
        l.setX(x-1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.WEST,false);
        }
        l.setX(x+1);
        if(!l.getBlock().getType().equals(M)) {
            f.setFace(BlockFace.EAST,false);
        }
        l.setX(x);
        b.setBlockData(f);
    }
}