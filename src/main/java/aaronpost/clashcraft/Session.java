package aaronpost.clashcraft;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Currency.Elixir;
import aaronpost.clashcraft.Currency.Gold;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Islands.Island;
import com.sun.jdi.ClassType;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import java.io.Serializable;
import java.util.*;

public class Session implements Serializable {
    private final Island island;
    private transient Arena arena;
    private long timeUpdatesStopped = -1;
    private final Map<String,Currency> currencies = new HashMap<>();
    private final UUID u;
    private String playerUsername;
    private boolean debugMode = false;
    public Session(Player p) {
        this.u = p.getUniqueId();
        this.island = new Island();
        this.currencies.put("gold", new Gold(this));
        this.currencies.put("elixir", new Elixir(this));
        this.playerUsername = p.getName();
    }
    public void toggleDebugMode() {
        debugMode = !debugMode;
    }
    public boolean isDebugMode() {
        return debugMode;
    }
    public void saveLogOffTime() {
        timeUpdatesStopped = System.currentTimeMillis();
        // there is a bug caused by this where it will use the old username for one logon session but who cares
        this.playerUsername = Bukkit.getOfflinePlayer(u).getName();
    }
    public float retrieveHoursOffline() {
        if(timeUpdatesStopped == -1) {
            return -1;
        }
        return (System.currentTimeMillis() - timeUpdatesStopped) /1000f /60f / 60f;
    }
    public void refreshScoreboard() {
        Player player = getPlayer();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("x", "y", ChatColor.BOLD +
                StringUtils.upperCase(player.getName() + "'s Island"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int x = 1;
        for(Currency currency: currencies.values()) {
            Score score = objective.getScore(StringUtils.center(StringUtils.upperCase(currency.getDisplayName())
                    + ChatColor.GRAY + ": " +
                    String.format("%,d",currency.getAmount()) + ChatColor.DARK_GRAY + "/" + ChatColor.GRAY+
                    String.format("%,d",currency.getMaxAmount()), 40));
            score.setScore(x++);
        }
        player.setScoreboard(scoreboard);

    }
    public Island getIsland() {
        return island;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(u);
    }
    public Session getSession() {
        return this;

    }
    public Currency getCurrency(String currency) {
        return currencies.get(currency);
    }
}
