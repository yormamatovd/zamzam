package client.helper;

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

    public static String generatePass() {
        String numbers = "123456789qwertyuiopasdfghjklzxcvbnm";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }


    public static LocalDateTime timeAsSeconds(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of(serverTimeZone));
    }

    public static long currentSeconds() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(serverTimeZone)).atZone(ZoneId.of(serverTimeZone)).toEpochSecond();
    }

    public static LocalDateTime currentTime() {
        return timeAsSeconds(currentSeconds());
    }
}
