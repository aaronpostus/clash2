package us.aaronpost.clash.PersistentData;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import us.aaronpost.clash.Troops.Archer;
import us.aaronpost.clash.Troops.Barbarian;
import us.aaronpost.clash.Troops.Troop;

import java.lang.reflect.Type;

public class TroopDeserializer implements JsonDeserializer<Troop> {
    @Override
    public Troop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int myType = json.getAsJsonObject().get("type").getAsInt();
        switch (myType) {
            case 1: return context.deserialize(json, Barbarian.class);
            case 2: return context.deserialize(json, Archer.class);
            default: return null;
        }
    }
}
