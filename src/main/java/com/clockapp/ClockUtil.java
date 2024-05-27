package com.clockapp;

import java.time.Duration;

public class ClockUtil {
    public static String convertDurationToString(Duration duration) {
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.toSeconds();
        duration = duration.minusSeconds(seconds);
        long milliseconds = duration.toMillis();

        String millisecondsString = String.format("%03d", milliseconds);
        String firstTwoDigits = millisecondsString.substring(0, 2);
        return String.format("%02d:%02d:%02d:%s", hours, minutes, seconds, firstTwoDigits);
    }

    public static long convertTime(String lapTime) {
        String[] fields = lapTime.split(":");

        // Parse hours, minutes, seconds, and milliseconds
        int hours = Integer.parseInt(fields[0]);
        int minutes = Integer.parseInt(fields[1]);
        int seconds = Integer.parseInt(fields[2]);
        int milliseconds = Integer.parseInt(fields[3]);

        // Calculate total milliseconds
        return (hours * 3600L + minutes * 60L + seconds) * 1000L + milliseconds;
    }
}
