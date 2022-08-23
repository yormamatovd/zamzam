package register.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;


public class Helper {

    private static final String serverTimeZone = "Asia/Tashkent";

    public static String generateOTP(int length) {
        String numbers = "123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        StringBuilder code = new StringBuilder();
        for (int i = 1; i < otp.toString().length() + 1; i++) {
            code.append(otp.toString().charAt(i - 1));
            if (i % 3 == 0 && i != otp.toString().length()) code.append("-");
        }
        return code.toString();
    }
    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }
}
