package aaronpost.clashcraft;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Currency.Gold;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Islands.Island;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Session implements Serializable {
    private final Island island;
    private transient Scoreboard scoreboard;
    private transient Arena arena;
    private long timeUpdatesStopped = -1;
    private List<Currency> currencies = new ArrayList<>();
    private final UUID u;

    public Session(Player p) {
        u = p.getUniqueId();
        island = new Island();
        currencies.add(new Gold(this));
    }
    public void saveLogOffTime() {
        timeUpdatesStopped = System.currentTimeMillis();
    }
    public float retrieveHoursOffline() {
        if(timeUpdatesStopped == -1) {
            return -1;
        }
        return ((float) (System.currentTimeMillis() - timeUpdatesStopped)) * BuildingGlobals.MILLISECONDS_TO_SECONDS *
                BuildingGlobals.SECONDS_TO_HOURS;
    }
    public void refreshScoreboard() {
        Player player = getPlayer();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("x", "y", ChatColor.BOLD +
                StringUtils.upperCase(player.getName() + "'s Island"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int x = 1;
        for(Currency currency: currencies) {
            Score score = objective.getScore(StringUtils.center(StringUtils.upperCase(currency.getDisplayName())
                    + ChatColor.GRAY + ": " +
                    String.format("%,d",currency.getAmount()) + ChatColor.DARK_GRAY + "/" + ChatColor.GRAY+
                    String.format("%,d",currency.getMaxAmount()), 40));
            score.setScore(x++);
        }
        player.setScoreboard(scoreboard);

    }
    public Gold getGold() {
        for(Currency currency : currencies) {
            if(currency instanceof Gold) {
                return (Gold) currency;
            }
        }
        return null;
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
}
