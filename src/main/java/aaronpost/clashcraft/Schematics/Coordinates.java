package us.aaronpost.clash.Schematics;

import org.bukkit.Location;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Coordinates implements Serializable {
    private List<LocationWrapper> coordinates = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
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
