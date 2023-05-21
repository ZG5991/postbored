package postbored.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {

    public String localDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(formatter);
    }

    public LocalDateTime stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDateTime.parse(date, formatter);
    }

    public String localTimeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        return time.format(formatter);
    }

    public LocalDateTime stringToLocalTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        return LocalDateTime.parse(time, formatter);
    }

}
