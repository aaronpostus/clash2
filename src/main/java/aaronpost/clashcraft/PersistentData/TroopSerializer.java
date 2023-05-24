package us.aaronpost.clash.PersistentData;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import us.aaronpost.clash.Troops.Archer;
import us.aaronpost.clash.Troops.Barbarian;
import us.aaronpost.clash.Troops.Troop;

import java.lang.reflect.Type;

public class TroopSerializer implements JsonSerializer<Troop> {
    public JsonElement serialize(Troop src, Type typeOfSrc, JsonSerializationContext context) {
        switch (src.getType()) {
            case 1: return context.serialize((Barbarian) src);
            case 2: return context.serialize((Archer) src);
            default: return null;
        }
    }
}
