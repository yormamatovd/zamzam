package order.helper;

import order.enums.ApiStatus;
import order.exception.NotAcceptableException;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Helper {

    private static final String serverTimeZone = "Asia/Tashkent";

    public static LocalDateTime timeAsSeconds(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of(serverTimeZone));
    }

    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }

    public static ZonedDateTime currentZonedDateTime() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone));
    }

    public static Long getStartTimeOfDay(LocalDate localDate) {
        LocalDateTime startDayTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        return startDayTime.toEpochSecond(currentZonedDateTime().getOffset());
    }

    public static Long getEndTimeOfDay(LocalDate localDate) {
        LocalDateTime startDayTime = LocalDateTime.of(localDate, LocalTime.of(23, 59, 59));
        return startDayTime.toEpochSecond(currentZonedDateTime().getOffset());
    }

    public static LocalDate parseDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate dateTime = null;
        try {
            dateTime = LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
        return dateTime;
    }
}
