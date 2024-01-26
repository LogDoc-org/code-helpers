package org.logdoc.helpers;


import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 12:21
 * code-helpers ☭ sweat and blood
 */
public class Texts {
    // http://bjoern.hoehrmann.de/utf-8/decoder/dfa/
    private static final int[] utf8d = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, // 00..1f
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, // 20..3f
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, // 40..5f
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, // 60..7f
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
            9, // 80..9f
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, // a0..bf
            8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, // c0..df
            0xa, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x3, 0x4, 0x3, 0x3, // e0..ef
            0xb, 0x6, 0x6, 0x6, 0x5, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, 0x8, // f0..ff
            0x0, 0x1, 0x2, 0x3, 0x5, 0x8, 0x7, 0x1, 0x1, 0x1, 0x4, 0x6, 0x1, 0x1, 0x1, 0x1, // s0..s0
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1,
            1, // s1..s2
            1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1,
            1, // s3..s4
            1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1,
            1, // s5..s6
            1, 3, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 // s7..s8
    };
    private static final Pattern md = Pattern.compile("([!=#\\.\\(\\)\\*\\[\\]\"`'~_\\-+|])");

    private Texts() {
    }

    public static String escapeMd(final String s) {
        return md.matcher(s).replaceAll("\\\\$1");
    }

    public static boolean getBoolean(final Object o) {
        if (o == null)
            return false;

        if (o instanceof Boolean)
            return (Boolean) o;

        return notNull(o).equalsIgnoreCase("true") || !notNull(o).equals("0");
    }

    public static String notNull(final Object o, final String def) {
        return notNull(isEmpty(o) ? def : o);
    }

    public static String notNull(final Object o) {
        return notNull(o, "");
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(final Object o) {
        if (o == null)
            return true;

        if (o.getClass().isArray())
            return Array.getLength(o) == 0;

        if (o instanceof Collection)
            return ((Collection) o).isEmpty();

        if (o instanceof Map)
            return ((Map) o).isEmpty();

        if (o.getClass().isEnum())
            return false;

        return notNull(o).isEmpty();
    }

    public static int hexToBinary(final byte b) throws IOException {
        final int i = Character.digit((char) b, 16);

        if (i == -1)
            throw new IOException("Invalid quoted printable encoding: not a valid hex digit: " + b);

        return i;
    }

    public static String mimeCharset(final String charset) {
        if (charset == null)
            return null;

        switch (charset.toLowerCase()) {
            case "iso-2022-cn":
                return "ISO2022CN";
            case "iso-2022-kr":
                return "ISO2022KR";
            case "utf-8":
            case "utf8":
                return "UTF8";
            case "ja_jp.iso2022-7":
                return "ISO2022JP";
            case "ja_jp.eucjp":
                return "EUCJIS";
            case "euc-kr":
            case "euckr":
                return "KSC5601";
            case "us-ascii":
            case "x-us-ascii":
                return "ISO-8859-1";
            default:
                return charset;
        }
    }

    public static boolean isValidUTF8(final byte[] data, int off) {
        final int len = data.length;
        if (len < off)
            return false;

        for (int i = off, state = 0; i < len; ++i) {
            state = utf8d[256 + (state << 4) + utf8d[(0xff & data[i])]];

            if (state == 1)
                return false;
        }

        return true;
    }

    public static boolean isValidUTF8(final byte[] data) {
        return isValidUTF8(data, 0);
    }

    public static String stringUtf8(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String encodeMD5(final String value) throws Exception {
        return encodeMD5("", value);
    }

    public static String encodeMD5(final String salt, final String value) throws Exception {
        if (value == null || value.length() == 0)
            return "";

        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (!notNull(salt).isEmpty())
            md5.update(salt.getBytes(StandardCharsets.UTF_8));
        if (!notNull(value).isEmpty())
            md5.update(value.getBytes(StandardCharsets.UTF_8));
        return hex2string(md5.digest());
    }

    public static String encodeMD5(final byte[] salt, final String value) throws Exception {
        if (value == null || value.length() == 0)
            return "";


        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (salt != null && salt.length > 0)
            md5.update(salt);
        md5.update(value.getBytes(StandardCharsets.UTF_8));
        return hex2string(md5.digest());
    }

    public static byte[] encodeMD5(final byte[] salt, final byte[] value) throws NoSuchAlgorithmException {
        if (value == null || value.length == 0)
            return new byte[0];

        final MessageDigest md5 = MessageDigest.getInstance("MD5");

        if (salt != null && salt.length > 0)
            md5.update(salt);

        md5.update(value);

        return md5.digest();
    }

    public static String hex2string(final byte[] hash) {
        final StringBuilder buf = new StringBuilder(hash.length << 1);
        int i;
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10)
                buf.append("0");
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String notNullNoTrim(final Object s) {
        if (s == null)
            return "";

        return String.valueOf(s);
    }

    public static boolean getBoolean(final Object parameter, final Boolean nullValue) {
        return notNull(parameter).isEmpty() ? nullValue : getBoolean(parameter);
    }

    public static String notNull(final Object parameter, final Pattern pattern) {
        return notNull(parameter, pattern, null);
    }

    public static String notNull(final Object parameter, final Pattern pattern, final String defaultValue) {
        return pattern.matcher(notNull(parameter)).matches() ? notNull(parameter) : notNull(defaultValue);
    }

    public static String notNullNoTrim(final Object parameter, final String defaultValue) {
        return notNullNoTrim(parameter).length() > 0 ? notNullNoTrim(parameter) : notNullNoTrim(defaultValue);
    }

    public static String notNullNoTrim(final Object parameter, final Pattern pattern) {
        return notNullNoTrim(parameter, pattern, null);
    }

    public static String notNullNoTrim(final Object parameter, final Pattern pattern, final String defaultValue) {
        return pattern.matcher(notNullNoTrim(parameter)).matches() ? notNullNoTrim(parameter) : notNullNoTrim(defaultValue);
    }

    public static String capitalize(final String s) {
        return capitalize(s, true);
    }

    public static String capitalize(final String s, final boolean row) {
        if (s.length() < 2) return s.toUpperCase();

        return s.substring(0, 1).toUpperCase() + (row ? s.substring(1).toLowerCase() : s.substring(1));
    }

    public static String join(final Object[] array) {
        return join(array, null, 0, array.length);
    }

    public static String join(final Object[] array, final String separator) {
        return join(array, separator, 0, array.length);
    }

    public static String join(final String separator, final Object... array) {
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, final String separator, final int startIndex, final int endIndex) {
        if (array == null)
            return null;

        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0)
            return "";

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
        final StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex && separator != null && !separator.isEmpty())
                buf.append(separator);

            if (array[i] != null)
                buf.append(array[i]);
        }

        return buf.toString();
    }

    public static String join(final List<?> array) {
        return join(array, null, 0, array.size());
    }

    public static String join(final List<?> array, final String separator) {
        return join(array, separator, 0, array.size());
    }

    public static String join(final List<?> array, final String separator, final int startIndex, final int endIndex) {
        if (array.isEmpty())
            return "";

        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0)
            return "";

        bufSize *= ((array.get(startIndex) == null ? 16 : String.valueOf(array.get(startIndex)).length()) + 1);
        final StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex && separator != null && !separator.isEmpty())
                buf.append(separator);

            if (array.get(i) != null)
                buf.append(array.get(i));
        }

        return buf.toString();
    }

    public static String toString(final Object[] array) {
        if (array == null)
            return "";

        return join(array, ",");
    }

    public static String toString(final Collection<?> collection) {
        if (collection == null)
            return "";

        return join(collection.toArray(), ",");
    }

    public static String makeEnding(double q, String one, String be4, String after5) {
        return makeEnding(new Double(q).intValue(), one, be4, after5, after5);
    }

    public static String makeEnding(int q, String one, String be4, String after5) {
        return makeEnding(q, one, be4, after5, after5);
    }

    public static String makeEnding(double q, String one, String be4, String after5, String alterZero) {
        return makeEnding(new Double(q).intValue(), one, be4, after5, alterZero);
    }

    public static String makeEnding(int q, String one, String be4, String after5, String alterZero) {
        final int e = q % 10;

        if (e == 0)
            return alterZero;

        if (e == 1 && (q < 10 || q > 20))
            return one;

        if (e < 5 && (q < 10 || q > 20))
            return be4;

        return after5;
    }

    public static String byteArrayToHexString(final byte[] raw) {
        final byte[] HEX_CHAR_TABLE = {
                (byte) '0', (byte) '1', (byte) '2', (byte) '3',
                (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
                (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
        };
        final byte[] hex = new byte[2 * raw.length];
        int index = 0;

        for (byte b : raw) {
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }

        return new String(hex, StandardCharsets.US_ASCII);
    }

    public static byte[] hexStringToByteArray(String s) {
        final int len = s.length();
        final byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2)
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));

        return data;
    }

    public static StringBuilder append(final Collection<?> collection, final StringBuilder appendable) {
        collection.forEach(appendable::append);

        return appendable;
    }

    public static boolean anyNotEmpty(final Object... o) {
        return o == null || o.length == 0 || Arrays.stream(o).anyMatch(o1 -> !isEmpty(o1));
    }

    public static boolean allEmpty(final Object... o) {
        return o == null || o.length == 0 || Arrays.stream(o).allMatch(Texts::isEmpty);
    }

    public static boolean noneEmpty(final Object... o) {
        return o != null && o.length > 0 && Arrays.stream(o).noneMatch(Texts::isEmpty);
    }

    public static String quoteEscape(final String s) {
        return quoteEscape(s, "");
    }

    public static String bytesToHex(byte[] bytes) {
        final StringBuilder result = new StringBuilder();

        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));

        return result.toString();
    }

    public static String quoteEscape(final CharSequence s, final String nullHolder) {
        if (isEmpty(s))
            return nullHolder;

        final StringBuilder o = new StringBuilder(s.length());
        char c;

        for (int i = 0; i < s.length(); i++) {
            if ((c = s.charAt(i)) == '\'' && (i == 0 || s.charAt(i - 1) != '\\'))
                o.append('\\');
            o.append(c);
        }

        return o.toString();
    }
}
