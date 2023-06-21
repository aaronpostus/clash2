package aaronpost.clashcraft.Buildings.Aesthetics;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class ItemFountain {
    public ItemFountain(Location loc, List<Material> items, float intensity, int periodInTicks, int lengthInSec) {
        Random random = new Random();
        int totalItems = items.size();

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack item = new ItemStack(items.get(random.nextInt(totalItems)));
                Item entity = Globals.world.dropItem(loc,item);
                entity.setVelocity(
                    new Vector(
                        random.nextFloat(intensity),
                        random.nextFloat(intensity),
                        random.nextFloat(intensity)
                    )
                );
                BukkitTask killItem = new BukkitRunnable() {
                    @Override
                    public void run() {
                        entity.remove();
                    }
                }.runTaskLater(ClashCraft.plugin, 20L);
            }
        }.runTaskTimer(ClashCraft.plugin, 0, periodInTicks);
        BukkitTask cancelTask = new BukkitRunnable() {
            @Override
            public void run() {
                task.cancel();
                System.out.println("Cancelled");
            }
        }.runTaskLater(ClashCraft.plugin, 20L * lengthInSec);
    }
}
