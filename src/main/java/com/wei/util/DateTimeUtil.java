package com.wei.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author Administrator
 */
public class DateTimeUtil {

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static ZoneId getDefaultZoneId() {
        return TimeZone.getDefault().toZoneId();
    }

    public static LocalDateTime parseTimeStamp2DateTime(Long ts) {
        return parseTimeStamp2DateTime(ts, getDefaultZoneId());
    }

    public static LocalDateTime parseTimeStamp2DateTime(Long ts, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ts), zoneId);
    }

    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId fromZone, final ZoneId toZone) {
        final ZonedDateTime zonedTime = time.atZone(fromZone);
        final ZonedDateTime converted = zonedTime.withZoneSameInstant(toZone);
        return converted.toLocalDateTime();
    }

    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId toZone) {
        return toZone(time, ZoneId.systemDefault(), toZone);
    }

    public static LocalDateTime toDefaultZone(final LocalDateTime time) {
        return toZone(time, getDefaultZoneId());
    }

}
