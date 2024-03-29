package aaronpost.clashcraft.Singletons;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Listener {
    private static GameManager instance = null;
    public boolean active;
    private final List<IUpdatable> updatables;
    private final List<IFixedUpdatable> fixedUpdatables;
    public NPCRegistry registry;
    private BukkitTask fixedUpdate, update;
    private GameManager() {
        active = true;
        updatables = new ArrayList<>();
        fixedUpdatables = new ArrayList<>();
    }
    // Static method
    // Static method to create instance of Singleton class
    public static synchronized GameManager getInstance()
    {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }
    @EventHandler
    public void onCitizensEnable(CitizensEnableEvent ev) {
        registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
    }
    public void startUpdates() {
        active = true;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        fixedUpdate = new BukkitRunnable() {
            @Override
            public void run() {
                fixedUpdate();
            }
        }.runTaskTimer(ClashCraft.plugin, 0, 20);

        update = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(ClashCraft.plugin, 0, 3);
    }
    public void addUpdatable(IUpdatable updatable) {
        updatables.add(updatable);
    }

    public void addFixedUpdatable(IFixedUpdatable fixedUpdatable) {
        fixedUpdatables.add(fixedUpdatable);
    }

    public boolean removeFixedUpdatable(IFixedUpdatable fixedUpdatable) {
        if(fixedUpdatables.contains(fixedUpdatable)) {
            fixedUpdatables.remove(fixedUpdatable);
            //success
            return true;
        }
        return false;
    }

    public boolean removeUpdatable(IUpdatable updatable) {
        if(updatables.contains(updatable)) {
            updatables.remove(updatable);
            //success
            return true;
        }
        return false;
    }

    /**
     * Maintains the list of items to update, but freezes updates til startUpdates is called again.
     */
    public void freezeUpdates() {
        active = false;
        fixedUpdate.cancel();
        update.cancel();
    }

    // Called every tick. Should be used only when necessary.
    private void fixedUpdate() {
        for(IFixedUpdatable fixedUpdatable: fixedUpdatables) {
            fixedUpdatable.fixedUpdateRequest();
        }
    }

    // Called every second.
    private void update() {
        for(IUpdatable updatable: updatables) {
            updatable.update();
        }
    }

}
