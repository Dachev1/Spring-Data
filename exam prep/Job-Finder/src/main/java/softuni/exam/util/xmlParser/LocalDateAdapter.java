package softuni.exam.util.xmlParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Converts a String (from XML) to LocalDate (for Java object)
    @Override
    public LocalDate unmarshal(String v) {
        return v != null ? LocalDate.parse(v, FORMATTER) : null;
    }

    // Converts a LocalDate (from Java object) to String (for XML)
    @Override
    public String marshal(LocalDate v) {
        return v != null ? v.format(FORMATTER) : null;
    }
}
