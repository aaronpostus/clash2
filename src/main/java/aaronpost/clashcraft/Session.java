package aaronpost.clashcraft;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Currency.Gold;
import aaronpost.clashcraft.Islands.Island;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Session implements Serializable {
    private final Island island;
    private transient Scoreboard scoreboard;
    private transient Arena arena;
    private Temporal timeUpdatesStopped = null;
    private List<Currency> currencies = new ArrayList<>();
    private final UUID u;

    public Session(Player p) {
        u = p.getUniqueId();
        island = new Island();
        currencies.add(new Gold(p));
    }
    public void saveLogOffTime() {
        timeUpdatesStopped = LocalTime.now();
    }
    public Duration retrieveTimeOffline() {
        if(timeUpdatesStopped == null) {
            return null;
        }
        return Duration.between(timeUpdatesStopped, LocalTime.now());
    }
    public void initializeScoreboard(Player player) {
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
