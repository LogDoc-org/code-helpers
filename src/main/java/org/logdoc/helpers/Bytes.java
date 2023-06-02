package org.logdoc.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;

import static org.logdoc.helpers.Texts.bytesToHex;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 12:23
 * code-helpers ☭ sweat and blood
 */
public class Bytes {
    private Bytes() { }

    public static String hash256(String data) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean subArrayEquals(final byte[] bigArray, final byte[] match, final int fromIdx) {
        if (fromIdx < 0 || bigArray.length - fromIdx < match.length) return false;

        for (int i = fromIdx, j = 0; i < bigArray.length - match.length && j < match.length; i++, j++)
            if (bigArray[i] != match[j])
                return false;

        return true;
    }

    public static int compare(final byte[] first, final byte[] second) {
        if (Arrays.equals(first, second))
            return 0;

        final int limit = Math.min(first.length, second.length);

        for (int i = 0; i < limit; i++)
            if (first[i] < second[i])
                return -1;
            else if (first[i] > second[i])
                return 1;

        return limit == first.length ? -1 : 1;
    }

    public static void copy(final InputStream in, final OutputStream out) {
        copy(false, in, out);
    }

    public static void copy(final boolean rethrow, final InputStream in, final OutputStream out) {
        if (in == null || out == null)
            throw new NullPointerException();

        final byte[] tmp = new byte[1024 * 1024];
        int read;

        try {
            while ((read = in.read(tmp, 0, tmp.length)) != -1)
                out.write(tmp, 0, read);

            out.flush();
        } catch (final IOException e) {
            if (rethrow)
                throw new RuntimeException(e);
        }
    }

    public static long copyAndCount(final InputStream in, final OutputStream out, final boolean instantCloseOut) throws IOException {
        if (in == null) return 0;
        long total = 0;
        final byte[] pBuffer = new byte[1024 * 512];

        for (int res = 0; res != -1;) {
            res = in.read(pBuffer, 0, pBuffer.length);

            if (res > 0) {
                total += res;

                if (out != null)
                    out.write(pBuffer, 0, res);
            }
        }

        if (out != null)
            out.flush();

        if (out != null) {
            if (instantCloseOut)
                out.close();
            else
                out.flush();
        }
        in.close();

        return total;
    }
}
