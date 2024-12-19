package org.logdoc.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.function.Function;

import static org.logdoc.helpers.Texts.notNull;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:16
 * code-helpers ☭ sweat and blood
 */
public class Digits {
    private Digits() {
    }

    private static <T extends Number> T get(final String string, final boolean mayBeNegative, final boolean mayBeFractional, final Function<String, T> resolver, final T nullReplace) {
        try {
            char[] s = notNull(string).toCharArray();
            boolean negative = false;
            int fractionPos = -1;

            if (mayBeNegative)
                for (final char c : s)
                    if (c == '-') {
                        negative = true;
                        break;
                    } else if (Character.isDigit(c))
                        break;

            if (mayBeFractional) {
                final String k = notNull(string).replaceAll("([^0-9.])", "");
                fractionPos = k.lastIndexOf('.');
                fractionPos = fractionPos < 0 ? 0 : k.length() - fractionPos;
            }

            String ss = notNull(string).replaceAll("([^0-9])", "");
            if (negative)
                ss = "-" + ss;

            if (mayBeFractional)
                ss = ss.substring(0, ss.length() - fractionPos) + "." + ss.substring(ss.length() - fractionPos);

            return resolver.apply(ss);
        } catch (final Exception ignore) {}

        return nullReplace;
    }


    public static long getLong(final Object value, final int radix) {
        return get(String.valueOf(value), true, false, s -> Long.parseLong(s, radix), 0L);
    }

    public static long getLong(final Object value) {
        return get(String.valueOf(value), true, false, Long::parseLong, 0L);
    }

    public static double getDouble(final Object value) {
        return get(String.valueOf(value), true, true, Double::parseDouble, 0D);
    }

    public static float getFloat(final Object value) {
        return get(String.valueOf(value), true, true, Float::parseFloat, 0F);
    }

    public static long getLong(final Object value, final long substitute) {
        return get(String.valueOf(value), true, false, Long::parseLong, substitute);
    }

    public static int getInt(final Object value, final int substitute) {
        return get(String.valueOf(value), true, false, Integer::decode, substitute);
    }

    public static int getInt(final Object value, final int max, final int min) {
        final int i = getInt(value);

        return i > max ? max : Math.max(i, min);
    }

    public static int getInt(final Object value) {
        return getInt(value, 0);
    }

    public static short getShort(final Object parameter, final short max, final short min) {
        final short i = getShort(parameter);

        return i > max ? max : i < min ? min : i;
    }

    public static short getShort(final Object value) {
        return get(String.valueOf(value), true, false, Short::decode, (short) 0);
    }

    public static String formatPrice(final double money) {
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("#0.00", symbols).format(money);
    }

    public static String formatPriceShort(final double money) {
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("#0", symbols).format(money);
    }

    public static String formatPriceLong(final float money) {
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("#0.0000", symbols).format(money);
    }

    public static String formatPriceCents(final double money) {
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        return new DecimalFormat("#0", symbols).format(money * 100d);
    }

    public static long tol(final byte[] buf, final int shift) {
        return (toi(buf, shift) << 32) + ((toi(buf, shift + 4) << 32) >>> 32);
    }

    public static long toi(final byte[] buf, int shift) {
        return (buf[shift] << 24)
                + ((buf[++shift] & 0xFF) << 16)
                + ((buf[++shift] & 0xFF) << 8)
                + (buf[++shift] & 0xFF);
    }

    public static String minTwo(final int min0Max99) {
        return String.valueOf((char) (min0Max99 / 10 + '0')) + (char) (min0Max99 % 10 + '0');
    }
}
