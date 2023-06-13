package aaronpost.clashcraft.Schematics;

import aaronpost.clashcraft.Schematics.LocationWrapper;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Coordinates implements Serializable {
    private final List<LocationWrapper> coordinates = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();
    // Transient because Gson cannot serialize Maps.
    private transient Map<String, LocationWrapper> coordinatesWithKeys;

    public Coordinates() {

    }

    public static Coordinates c = new Coordinates();

    public void addCoordinate(Location l, String key) {
       LocationWrapper loc = new LocationWrapper(l);
       coordinates.add(loc);
       keys.add(key);
       coordinatesWithKeys.put(key, loc);
    }

    public Location getCoordinate(String key) {
        return coordinatesWithKeys.get(key).getLoc();
    }
}
