package omg.jd.tvmazeapiclient.ws.model.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import omg.jd.tvmazeapiclient.ws.model.Links;

public class LinksDeserializer implements JsonDeserializer<Links> {
    @Override
    public Links deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String self = null;
        String previousepisode = null;
        String nextepisode = null;

        JsonObject rootObject = (JsonObject) json;
        if (rootObject.has("self")) {
            self = ((JsonObject)rootObject.get("self")).get("href").getAsString();
        }
        if (rootObject.has("previousepisode")) {
            previousepisode = ((JsonObject)rootObject.get("previousepisode")).get("href").getAsString();
        }
        if (rootObject.has("nextepisode")) {
            nextepisode = ((JsonObject)rootObject.get("nextepisode")).get("href").getAsString();
        }

        return new Links(self,previousepisode,nextepisode);
    }
}
