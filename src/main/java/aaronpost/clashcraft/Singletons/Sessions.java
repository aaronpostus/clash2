package aaronpost.clashcraft.Singletons;

import aaronpost.clashcraft.Session;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sessions {
    private Map<Player,Session> sessions = new HashMap<>();
    public static Sessions s = new Sessions();
    public Session getSession(Player p) {
        return sessions.get(p);
    }
    public List<Session> getSessions() {
        return (List<Session>) sessions.values();
    }
}
