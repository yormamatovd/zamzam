package systemuser.helper;

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
        return otp.toString();
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 5;
    }


    public static LocalDateTime timeAsSeconds(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of(serverTimeZone));
    }

    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }

    public static boolean hasDigit(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) return true;
        }
        return false;
    }
}
