package aaronpost.clashcraft.Schematics;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.OfflineSkull;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

public class AdminSchematicMenu implements Listener {
    private final Inventory i;
    private final ArrayList<Schematic> s;
    private final int pages;
    private int currentPage;
    public AdminSchematicMenu(Player p) {
        i = p.getServer().createInventory(null, 54, ChatColor.GREEN + "+ Schematics +");

        s = (ArrayList<Schematic>) Schematics.s.getSchematics();

        // Offline player skull
        ItemStack leftArrow = OfflineSkull.getSkull(Globals.LEFT_ARROW_URL);
        ItemMeta meta = leftArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "<==");
        leftArrow.setItemMeta(meta);

        // Offline player skull
        ItemStack rightArrow = OfflineSkull.getSkull(Globals.RIGHT_ARROW_URL);
        meta = rightArrow.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "==>");
        rightArrow.setItemMeta(meta);

        pages = (int) Math.floor(((double) s.size()) / 45);
        currentPage = 1;

        i.setItem(53, rightArrow);
        i.setItem(45, leftArrow);

        formatPage(1);
        p.openInventory(i);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent i) {
        InventoryView e = i.getView();
        if (e.getTitle().contains(ChatColor.GREEN + "+ Schematics +")) {
            if(i.getCurrentItem() != null) {
                i.setCancelled(true);
                if (i.getCurrentItem().getType().equals(Material.PAPER)) {
                    if(i.getCurrentItem().getItemMeta().hasDisplayName()) {
                        String schematicName = ChatColor.stripColor(i.getCurrentItem().getItemMeta().getDisplayName());
                        if(!schematicName.equals("")) {
                            for (Schematic schematic : s) {
                                if (schematic.getName().equals(schematicName)) {
                                    if(!i.getClick().isShiftClick()) {
                                        if(i.getClick().isLeftClick()) {
                                            schematic.pasteSchematic(i.getWhoClicked().getLocation(), 0);
                                            i.getWhoClicked().closeInventory();
                                        } else if (i.getClick().isRightClick()) {
                                            Schematics.s.getSchematics().remove(schematic);
                                            File file = new File(ClashCraft.plugin.getDataFolder().getAbsolutePath()
                                                    + "/Schematics/" + schematicName + ".json");
                                            boolean fileDeleteSuccess = file.delete();
                                            if(fileDeleteSuccess) {
                                                i.getWhoClicked().sendMessage(Globals.prefix + " Successfully deleted schematic and file for \"" + schematicName + "\".");
                                            } else {
                                                i.getWhoClicked().sendMessage(Globals.prefix + " Successfully deleted schematic for" +
                                                        " \"" + schematicName + "\".");
                                            }
                                            i.getWhoClicked().closeInventory();
                                        }
                                    } else if(i.getClick().isLeftClick()) {
                                        schematic.setyOffset(schematic.getYOffset() + 1);
                                        formatPage(currentPage);
                                    } else if(i.getClick().isRightClick()) {
                                        schematic.setyOffset(schematic.getYOffset() - 1);
                                        formatPage(currentPage);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else if (i.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                    if (i.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "<==")) {
                        if (currentPage > 1) {
                            currentPage--;
                            clearSlots();
                            formatPage(currentPage);
                        }
                    } else if (i.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "==>")) {
                        if (currentPage < pages) {
                            currentPage++;
                            clearSlots();
                            formatPage(currentPage);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(ChatColor.GREEN + "+ Schematics +")) {
            HandlerList.unregisterAll(this);
        }
    }

    public void clearSlots() {
        ItemStack air = new ItemStack(Material.AIR);
        for(int e = 0; e < 45; e++) {
            i.setItem(e, air);
        }

    }

    public void formatPage(int page) {

        ItemStack schematic = new ItemStack(Material.PAPER);
        ItemMeta meta = schematic.getItemMeta();

        int startIndex = ((page - 1) * 45);
        int endIndex = (page * 45);
        if(s.size() < endIndex) {
            endIndex = s.size();
        }

        for(int i = startIndex; i < endIndex; i++) {
            meta.setDisplayName(ChatColor.GOLD + s.get(i).getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Y-Offset: " + ChatColor.YELLOW + s.get(i).getYOffset());
            meta.setLore(lore);
            schematic.setItemMeta(meta);
            this.i.setItem(i, schematic);
        }

        ItemStack infoSign = new ItemStack(Material.OAK_SIGN);
        meta = infoSign.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Menu Tutorial:");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + " Left Click: " + ChatColor.YELLOW + "Paste schematic");
        lore.add(ChatColor.GRAY + " Right Click: " + ChatColor.YELLOW + "Delete schematic");
        lore.add(ChatColor.GRAY + " Shift Left Click: " + ChatColor.YELLOW + "+1 Y-Offset");
        lore.add(ChatColor.GRAY + " Shift Right Click: " + ChatColor.YELLOW + "-1 Y-Offset");
        meta.setLore(lore);
        infoSign.setItemMeta(meta);
        i.setItem(49, infoSign);
    }
}
