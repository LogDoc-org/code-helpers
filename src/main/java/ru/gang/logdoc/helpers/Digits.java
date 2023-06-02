package ru.gang.logdoc.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static ru.gang.logdoc.helpers.Texts.notNull;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:16
 * code-helpers ☭ sweat and blood
 */
public class Digits {
    private Digits() { }
    public static long getLong(final Object value, final int radix) {
        try {
            return Long.parseLong(String.valueOf(value), radix);
        } catch (Exception ee) {
            return 0;
        }
    }

    public static long getLong(final Object value) {
        try {
            return Long.decode(String.valueOf(value));
        } catch (Exception e) {
            try {
                return Long.parseLong(value.toString().replaceAll("([^0-9-])", ""));
            } catch (Exception ee) {
                return 0;
            }
        }
    }

    public static double getDouble(final Object parameter) {
        try {
            return Double.parseDouble(parameter.toString().replaceAll("([^0-9-.])", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public static float getFloat(final Object parameter) {
        try {
            return Float.parseFloat(parameter.toString().replaceAll("([^0-9-.])", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(final Object parameter, final int max, final int min) {
        final int i = getInt(parameter);

        return i > max ? max : Math.max(i, min);
    }

    public static int getInt(final Object parameter) {
        final String param = notNull(parameter);
        try {
            return Integer.decode(param);
        } catch (Exception e) {
            try {
                return Integer.parseInt(param.replaceAll("([^0-9-])", ""));
            } catch (Exception ee) {
                return 0;
            }
        }
    }

    public static short getShort(final Object parameter, final short max, final short min) {
        final short i = getShort(parameter);

        return i > max ? max : i < min ? min : i;
    }

    public static short getShort(final Object parameter) {
        final String param = notNull(parameter);
        try {
            return Short.decode(param);
        } catch (Exception e) {
            try {
                return Short.parseShort(param.replaceAll("([^0-9-])", ""));
            } catch (Exception ee) {
                return 0;
            }
        }
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
