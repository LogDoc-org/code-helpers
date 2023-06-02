package org.logdoc.helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.logdoc.helpers.Digits.minTwo;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:04
 * code-helpers ☭ sweat and blood
 */
public class Chronos {
    private Chronos() {}

    public static final long MINUTELIMIT = 1000L * 60L;
    public static final long HALFHOURLIMIT = MINUTELIMIT * 30L;
    public static final long HOURLIMIT = MINUTELIMIT * 60L;
    public static final long DAYLIMIT = HOURLIMIT * 24L;
    public static final long WEEKLIMIT = DAYLIMIT * 7L;

    public static int fullMinutesLength(final long from, final long till) {
        return (int) ChronoUnit.MINUTES.between(Instant.ofEpochMilli(from), Instant.ofEpochMilli(till));
    }

    public static int fullHoursLength(final long from, final long till) {
        return (int) ChronoUnit.HOURS.between(Instant.ofEpochMilli(from), Instant.ofEpochMilli(till));
    }

    public static int fullDaysLength(final long from, final long till) {
        return (int) ChronoUnit.DAYS.between(Instant.ofEpochMilli(from), Instant.ofEpochMilli(till));
    }

    public static int fullWeeksLength(final long from, final long till) {
        return (int) ChronoUnit.WEEKS.between(Instant.ofEpochMilli(from).atZone(ZoneId.systemDefault()), Instant.ofEpochMilli(till).atZone(ZoneId.systemDefault()));
    }

    public static int fullMinutesLength(final long diff) {
        return (int) (diff / MINUTELIMIT);
    }

    public static int fullHoursLength(final long diff) {
        return (int) (diff / HOURLIMIT);
    }

    public static int fullDaysLength(final long diff) {
        return (int) (diff / DAYLIMIT);
    }

    public static int fullWeeksLength(final long diff) {
        return (int) (diff / WEEKLIMIT);
    }

    public static String timeLength(final long from, final long till) {
        return timeLength(from, till, ChronoUnit.SECONDS);
    }

    public static String timeLength(final long from, final long till, final ChronoUnit resolution) {
        final StringBuilder out = new StringBuilder(16);

        long diff = Math.max(from, till) - Math.min(from, till);
        final int hours = fullHoursLength(diff);

        out.append(minTwo(hours)).append(":").append(minTwo(fullMinutesLength((diff = diff % HOURLIMIT))));

        if (resolution.ordinal() <= ChronoUnit.SECONDS.ordinal()) {
            out.append(":").append(minTwo((int) (diff % MINUTELIMIT)));

            if (resolution == ChronoUnit.MILLIS)
                out.append(".").append(diff % 1000L);
        }

        return out.toString();
    }

    public static String timeLength(long range, final ChronoUnit resolution) {
        final StringBuilder out = new StringBuilder(16);

        final int hours = fullHoursLength(range);

        out.append(minTwo(hours)).append(":").append(minTwo(fullMinutesLength((range = range % HOURLIMIT))));

        if (resolution.ordinal() <= ChronoUnit.SECONDS.ordinal()) {
            out.append(":").append(minTwo((int) (range % MINUTELIMIT / 1000)));

            if (resolution == ChronoUnit.MILLIS)
                out.append(".").append(range % 1000L);
        }

        return out.toString();
    }

    public static String timeLength(final long range, final ChronoUnit maxRes, final ChronoUnit minRes) {
        final StringBuilder out = new StringBuilder(16);

        long diff = range;
        final int weeks = fullWeeksLength(diff);
        int days = fullDaysLength(diff);
        int hours = fullHoursLength(diff);
        int mins = fullMinutesLength(diff);
        int secs = (int) (diff / 1000L);

        if (maxRes == ChronoUnit.WEEKS && weeks > 0) {
            out.append(weeks).append(":");
            days = fullDaysLength((diff = diff % WEEKLIMIT));
        }

        if (minRes != ChronoUnit.WEEKS) {
            if (maxRes.ordinal() <= ChronoUnit.DAYS.ordinal() && days > 0) {
                out.append(out.length() > 0 ? ":" : "").append(days).append(":");
                hours = fullHoursLength((diff = diff % DAYLIMIT));
            }

            if (minRes != ChronoUnit.DAYS) {
                if (maxRes.ordinal() <= ChronoUnit.HOURS.ordinal() && hours > 0) {
                    out.append(out.length() > 0 ? ":" : "").append(hours).append(":");
                    mins = fullMinutesLength((diff = diff % HOURLIMIT));
                }

                if (minRes != ChronoUnit.HOURS) {
                    if (maxRes.ordinal() <= ChronoUnit.MINUTES.ordinal() && mins > 0) {
                        out.append(out.length() > 0 ? ":" : "").append(mins).append(":");
                        secs = (int) ((int) (diff / 1000L) % MINUTELIMIT);
                    }

                    if (minRes != ChronoUnit.MINUTES) {
                        out.append(out.length() > 0 ? ":" : "").append(secs);

                        if (minRes != ChronoUnit.SECONDS)
                            out.append(out.length() > 0 ? "." : "").append(diff % 1000L);
                    }
                }
            }
        }

        return out.toString();
    }

}
