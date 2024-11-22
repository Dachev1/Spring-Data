package softuni.exam.util;

import com.google.gson.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // For XML (JAXB)
    @Override
    public String marshal(LocalDate localDate) {
        return localDate != null ? localDate.format(FORMATTER) : null;
    }

    @Override
    public LocalDate unmarshal(String date) {
        return date != null ? LocalDate.parse(date, FORMATTER) : null;
    }

    // For JSON (Gson)
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return src != null ? new JsonPrimitive(src.format(FORMATTER)) : JsonNull.INSTANCE;
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json != null && !json.isJsonNull() ? LocalDate.parse(json.getAsString(), FORMATTER) : null;
    }
}
