package aaronpost.clashcraft.Globals;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.OfflineSkull;
import aaronpost.clashcraft.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.TimeUnit;

public class Globals {
    public static final ItemStack RAID_ITEM = new ItemStack(Material.FILLED_MAP);
    public static NamespacedKey NM_KEY_RAID = new NamespacedKey(ClashCraft.plugin, "raid");

    static {
        ItemMeta meta = RAID_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Raid");
        meta.getPersistentDataContainer().set(NM_KEY_RAID,PersistentDataType.STRING, SkinGlobals.Troops.BARBARIAN.toString());
        RAID_ITEM.setItemMeta(meta);
    }

    public static String timeFromSeconds(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long secondsRemaining = seconds - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        StringBuilder sb = new StringBuilder();
        boolean includeDays = (day != 0);
        boolean includeHours = (hours != 0);
        boolean includeMinutes = (minutes != 0);
        boolean includeSeconds = (secondsRemaining != 0);

        if (includeDays) {
            sb.append(day).append((day == 1) ? " day " : " days ");
        }
        if (includeHours) {
            sb.append(hours).append((hours == 1) ? " hr " : " hrs ");
        }
        if (includeMinutes) {
            sb.append(minutes).append((minutes == 1) ? " min " : " mins ");
        }
        if (includeSeconds) {
            sb.append(secondsRemaining).append((secondsRemaining == 1) ? " sec " : " secs ");
        }

        return sb.toString().trim();
    }
    public static World world = ClashCraft.plugin.getServer().getWorld("World");
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Clash" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY;
    public static String RIGHT_ARROW_URL = "http://textures.minecraft.net/texture/1a4f68c8fb279e50ab786f9fa54c88ca4ecfe1eb5fd5f0c38c54c9b1c7203d7a";
    public static String LEFT_ARROW_URL = "http://textures.minecraft.net/texture/737648ae7a564a5287792b05fac79c6b6bd47f616a559ce8b543e6947235bce";
    public static String INVENTORY_TITLE = ChatColor.DARK_RED + "+ Barracks Level ` +";
    public static String ENABLED_TITLE = ChatColor.GREEN + "Training..";
    public static String DISABLED_TITLE = ChatColor.RED + "Not training.. (Click)";

    public static String[] BARBARIAN_URL = { "http://textures.minecraft.net/texture/d32985200010aaa0465f6692fac619f5bafe68b62270f243f1fb6a3333cc40fe" };
    public static String[] BARBARIAN_SIGNATURE = { "IKYQYgpJz7qL+6rg2+U+L1fByImCVvRABaLFo6iwgj9HAxXeIwTt0ESIcwYft9zQEd1xmtlpRpe7A5Unk+gM1jLzJqpz/bYbDBZYnL4062cAOPyO4VAZjyakVcPRaVCdenoSEfTHlOWoczSS+JD4zSQZP24FrgYK5N/+BKRbs5cKvrGtUoyAZAIyXFj8khoMaZ4Ydw1x3BwDJAc/gvrlbmTiZNrZDEXUER1YcNE2wfvWHUjPbghru95RN+6WVnUWyKn+lmVvs/PaK7qkr3GfiueK+9sVLQg+xH34oE0MGAljmKjMj+/TLqODf6Qy+IUeN1Xx4mfywuMHsFE/K1O5dC1S5q/7JmJHhgIHpsUzU2hk9PXJRyCW2Hw1MJDI8inSvjR3r2Fz7Jt71Mgx94u1xH2ur3W8cF36SWDa5LNYfsoV47m+GAWBLvxXwJeycJag/PuzmEticzGlmDMeIqC1oRMaUksFUKy68MqFoet20HVTlfwIRcIk5sqon17IX+pJUJtmcXGeN3WLvS5rN2q2Wz0QMU+7z6nAkrKfh/F8HYe8ulkrrjyD5htnPHZDQY3MxSq6RhRcLO+nkoZhOqkjB8rB3L7fcHPfjxWJiCISwFwWdqvQpIpor+92oFeBwE8sruDN/l0fsQeC0RZSmAtNlsh8oxJtyBtG9KHTKt4/cXI=",
            ""};
    public static String[] BARBARIAN_VALUE = { "ewogICJ0aW1lc3RhbXAiIDogMTY1MjQ5MDc3MTk1OSwKICAicHJvZmlsZUlkIiA6ICIzOTVkZTJlYjVjNjU0ZmRkOWQ2NDAwY2JhNmNmNjFhNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcGFyZXN0ZXZlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QzMjk4NTIwMDAxMGFhYTA0NjVmNjY5MmZhYzYxOWY1YmFmZTY4YjYyMjcwZjI0M2YxZmI2YTMzMzNjYzQwZmUiCiAgICB9CiAgfQp9",
            "" };
    public static String[] BARBARIAN_USERNAME = { "skin20d13f5d" };
    public static String BARBARIAN_TITLE = ChatColor.YELLOW + "Barbarian";
    public static int BARBARIAN_POSITION = 13;
    public static int[] BARBARIAN_COST = {15, 30, 60, 100, 150, 200, 250, 300, 350, 400};
    public static final ItemStack BARBARIAN_HEAD;
    public static NamespacedKey NM_KEY_PLACE_TROOP = new NamespacedKey(ClashCraft.plugin, "placetroop");

    static {
        BARBARIAN_HEAD = OfflineSkull.getSkull(Globals.BARBARIAN_URL[0]);
        ItemMeta meta = BARBARIAN_HEAD.getItemMeta();
        meta.setDisplayName(Globals.BARBARIAN_TITLE);
        meta.getPersistentDataContainer().set(NM_KEY_PLACE_TROOP,PersistentDataType.STRING, SkinGlobals.Troops.BARBARIAN.toString());
        BARBARIAN_HEAD.setItemMeta(meta);
    }
    public static String[] ARCHER_URL = { "http://textures.minecraft.net/texture/e95016226b3d7dfcc165c6c7bb60a5a2c619f52e880868faac4315e31153c248" };
    public static String ARCHER_TITLE = ChatColor.LIGHT_PURPLE + "Archer";
    public static int[] ARCHER_COST = {30, 60, 120, 200, 300, 400, 500, 600, 700, 800};

    public static String[] ARCHER_VALUE = { "ewogICJ0aW1lc3RhbXAiIDogMTY1MjI2NjU5MzU1MCwKICAicHJvZmlsZUlkIiA6ICIwNDNkZWIzOGIyNjE0MTg1YTIzYzU4ZmI2YTc5ZWZkYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaXRhbFNpZ256MiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lOTUwMTYyMjZiM2Q3ZGZjYzE2NWM2YzdiYjYwYTVhMmM2MTlmNTJlODgwODY4ZmFhYzQzMTVlMzExNTNjMjQ4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=" };
    public static String[] ARCHER_SIGNATURE = { "QCaNLM2UgQE6z600LiJ7xrL+mpGZ5CwMw4L6IDZx8vkdX/BbwAN7sQ0qOsBiaa5IyEbPZR2/gObKYljxgPFXHxCrDkLrQbcUoajMuydwnQ4/O+JnIB8klEsLpas4vLulsFx5U0IW8dS8iXFjA8wVElu+jaDg8WiAY4HT/9jRuEDSm9hm86NIMsthpo8dIBIktYThNpL7wAf154wzmspgfycF2TOVe9uUOYa3O669lZX23HcdF7Nj1NgPrJ1nvq1njqsyMYI0nx7gUMrjHqk8+Z0hbyzDhh3OQxlLgQqk0VrOj1o/c15r8pAn4I0d1B3wFAg7bcICmEVe0RV0kxeg1DyZoulB0QOuOyDPNOHyi5p46MDakQWGcvj/ToRK3TGbD1odNrRAU8N+8QS5PP8uiDtmfpIbuV7YYWNFc7bT097SLZzYd5kXRV2EyStqupttqPp55lKBEVUmOxq7+KIJVkWAH3FyC0MxIn6pqgczx299Mc9tYpPjN3k31jtCcoBM/GS83IpMjx5hW0gvy6VLw9RD//u0ejuFMwy+rQbToRdMr8Z4n+YAltFhWiFz29a3nhajC4RwkmimpP4bAPif45cuV3LF+1awSbcDo2Sg9Us+L3RADNxLhoh4Yagte6j2VtqoDuHiSnMjbMvYwcdnacZo0kfnGWbilrClVSeN/lA=" };
    public static String[] ARCHER_USERNAME = { "skin4a376b0e" };
    public static int ARCHER_POSITION = 12;
    public static final ItemStack ARCHER_HEAD;

    static {
        ARCHER_HEAD = OfflineSkull.getSkull(Globals.ARCHER_URL[0]);
        ItemMeta meta = ARCHER_HEAD.getItemMeta();
        meta.setDisplayName(Globals.ARCHER_TITLE);
        ARCHER_HEAD.setItemMeta(meta);
    }
    public static final ItemStack NOT_TRAINING;

    static {
        NOT_TRAINING = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta meta = NOT_TRAINING.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Not training.");
        NOT_TRAINING.setItemMeta(meta);
    }

    public static final ItemStack TRAINING;

    static {
        TRAINING = new ItemStack(Material.GREEN_STAINED_GLASS);
        ItemMeta meta = NOT_TRAINING.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Training: ");
        TRAINING.setItemMeta(meta);
    }

    public static final ItemStack CAN_TRAIN_ITEM = new ItemStack(Material.LIME_CONCRETE);
    public static final ItemStack CANNOT_TRAIN_ITEM = new ItemStack(Material.RED_CONCRETE);
    public static int COST_POSITION = 8;

    // GOLD
    public static ItemStack GOLD_ITEM_STACK = new ItemStack(Material.GOLD_INGOT);
    public static String GOLD_DISPLAY_NAME = ChatColor.YELLOW + "Gold";

    // ELIXIR
    public static ItemStack ELIXIR_ITEM_STACK = new ItemStack(Material.MAGENTA_DYE);
    public static String ELIXIR_DISPLAY_NAME = ChatColor.LIGHT_PURPLE + "Elixir";

    public static NamespacedKey NM_KEY_SHOP_ITEM = new NamespacedKey(ClashCraft.plugin, "shop");
    public static NamespacedKey NM_KEY_BLDNG_MENU_ITEM= new NamespacedKey(ClashCraft.plugin, "buildingMenu");
    public static NamespacedKey NM_KEY_BLDNG_PICK_UP_ITEM = new NamespacedKey(ClashCraft.plugin, "buildingPickUp");
    public static NamespacedKey NM_KEY_SPAWN_ITEM = new NamespacedKey(ClashCraft.plugin, "spawn");
    public static NamespacedKey NM_KEY_LEFT_CLICK_BLDNG = new NamespacedKey(ClashCraft.plugin, "leftClickBuilding");

    public static ItemStack SHOP_ITEM;
    static {
        SHOP_ITEM=new ItemStack(Material.BOOK);
        ItemMeta meta = SHOP_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Shop");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_SHOP_ITEM, PersistentDataType.STRING, "shop");
        SHOP_ITEM.setItemMeta(meta);
    }
    public static ItemStack RETURN_TO_SPAWN_ITEM;
    static {
        RETURN_TO_SPAWN_ITEM = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = RETURN_TO_SPAWN_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Return to Spawn");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_SPAWN_ITEM, PersistentDataType.STRING, "spawn");
        RETURN_TO_SPAWN_ITEM.setItemMeta(meta);
    }
    public static ItemStack PICK_UP_ITEM;
    static {
        PICK_UP_ITEM = new ItemStack(Material.LEAD);
        ItemMeta meta = PICK_UP_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Pick Building Up");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_BLDNG_PICK_UP_ITEM, PersistentDataType.STRING, "pickup");
        PICK_UP_ITEM.setItemMeta(meta);
    }
    public static ItemStack OPEN_BUILDING_MENU_ITEM;
    static {
        OPEN_BUILDING_MENU_ITEM = new ItemStack(Material.FLOWER_BANNER_PATTERN);
        ItemMeta meta = OPEN_BUILDING_MENU_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Open Building Menu");
        meta.getPersistentDataContainer().set(Globals.NM_KEY_BLDNG_MENU_ITEM, PersistentDataType.STRING, "buildingMenu");
        OPEN_BUILDING_MENU_ITEM.setItemMeta(meta);
    }
    public static ItemStack ARMY_SHOP_ICON;
    static {
        ARMY_SHOP_ICON = new ItemStack(Material.BOW);
        ItemMeta meta = ARMY_SHOP_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Army");
        ARMY_SHOP_ICON.setItemMeta(meta);
    }
    public static ItemStack RESOURCE_SHOP_ICON;
    static {
        RESOURCE_SHOP_ICON = new ItemStack(Material.CHEST);
        ItemMeta meta = RESOURCE_SHOP_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Resources");
        RESOURCE_SHOP_ICON.setItemMeta(meta);
    }

    public static ItemStack DEFENSE_SHOP_ICON;
    static {
        DEFENSE_SHOP_ICON = new ItemStack(Material.SHIELD);
        ItemMeta meta = DEFENSE_SHOP_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Defenses");
        DEFENSE_SHOP_ICON.setItemMeta(meta);
    }

    public static ItemStack TRAP_SHOP_ICON;
    static {
        TRAP_SHOP_ICON = new ItemStack(Material.TNT);
        ItemMeta meta = TRAP_SHOP_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "Traps");
        TRAP_SHOP_ICON.setItemMeta(meta);
    }

    public static ItemStack MAXED_OUT_ITEM;
    static {
        MAXED_OUT_ITEM = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = MAXED_OUT_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "MAXED OUT");
        MAXED_OUT_ITEM.setItemMeta(meta);
    }
}
