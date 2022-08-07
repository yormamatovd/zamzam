package attachment.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Helper {

    private static final String serverTimeZone = "Asia/Tashkent";

    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }
}
