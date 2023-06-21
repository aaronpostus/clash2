package aaronpost.clashcraft.PersistentData;

import aaronpost.clashcraft.Islands.Island;
import com.google.gson.*;

import java.lang.reflect.Type;

public class IslandAdapter implements JsonDeserializer<Island> {
    @Override
    public Island deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Island island = jsonDeserializationContext.deserialize(jsonElement, IslandWrapper.class);
        island.initializeUUIDGrid();
        return island;
    }
}
