package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime unmarshal(String v) {
        return LocalTime.parse(v, FORMATTER);
    }

    @Override
    public String marshal(LocalTime v) {
        return v.format(FORMATTER);
    }
}
