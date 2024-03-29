package aaronpost.clashcraft.Singletons;

import aaronpost.clashcraft.Session;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sessions {
    public enum PlayerState { DEFAULT, ISLAND, RAIDING }
    public Map<Player,PlayerState> playerStates = new HashMap<>();
    private final Map<Player,Session> sessions = new HashMap<>();
    public static Sessions s = new Sessions();
    public void addSession(Player p, Session s) { sessions.put(p,s); }
    public void removeSession(Player p) { sessions.remove(p); }
    public Session getSession(Player p) {
        return sessions.get(p);
    }
    public List<Session> getSessions() {
        return new ArrayList<>(sessions.values());
    }
}
