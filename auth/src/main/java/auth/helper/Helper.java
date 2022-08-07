package auth.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class Helper {

    private static final String serverTimeZone = "Asia/Tashkent";

    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }

    public static Date currentDate() {
        return new Date(currentSeconds() * 1000);
    }

    public static boolean hasAlphabetic(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isAlphabetic(text.charAt(i))) return true;
        }
        return false;
    }

    public static boolean validPhone(String value) {
        if (value.length() != 9) return false;
        String[] phonePrefix = new String[]{
                "99",
                "95",
                "77",
                "88",
                "87",
                "93",
                "94",
                "90",
                "91",
        };
        for (String prefix : phonePrefix) {
            if (value.startsWith(prefix)) return true;
        }
        return false;
    }
}
