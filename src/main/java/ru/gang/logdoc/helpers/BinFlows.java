package ru.gang.logdoc.helpers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static ru.gang.logdoc.helpers.Texts.isEmpty;
import static ru.gang.logdoc.helpers.Texts.notNull;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:24
 * code-helpers ☭ sweat and blood
 */
public class BinFlows {
    private BinFlows() {}

    public static void writeShort(final short sh, final OutputStream os) throws IOException {
        os.write((sh >>> 8) & 0xff);
        os.write((sh) & 0xff);
    }

    public static void writeInt(final int in, final OutputStream os) throws IOException {
        os.write((in >>> 24) & 0xff);
        os.write((in >>> 16) & 0xff);
        os.write((in >>> 8) & 0xff);
        os.write((in) & 0xff);
    }

    public static void writeLong(final long in, final OutputStream os) throws IOException {
        os.write((byte) (in >>> 56));
        os.write((byte) (in >>> 48));
        os.write((byte) (in >>> 40));
        os.write((byte) (in >>> 32));
        os.write((byte) (in >>> 24));
        os.write((byte) (in >>> 16));
        os.write((byte) (in >>> 8));
        os.write((byte) (in));
    }

    public static void writeUtf(final String s, final OutputStream os) throws IOException {
        final byte[] data = notNull(s).getBytes(StandardCharsets.UTF_8);

        writeShort((short) data.length, os);
        os.write(data);
    }

    public static long asLong(final byte[] buf) {
        if (isEmpty(buf) || buf.length < 8)
            return 0;

        return ((long) buf[0] << 56) +
                ((long) (buf[1] & 255) << 48) +
                ((long) (buf[2] & 255) << 40) +
                ((long) (buf[3] & 255) << 32) +
                ((long) (buf[4] & 255) << 24) +
                ((buf[5] & 255) << 16) +
                ((buf[6] & 255) << 8) +
                ((buf[7] & 255));
    }

    public static int asInt(final byte[] buf) {
        if (isEmpty(buf) || buf.length < 4)
            return 0;

        return ((buf[0] & 0xff) << 24) + ((buf[1] & 0xff) << 16) + ((buf[2] & 0xff) << 8) + (buf[3] & 0xff);
    }

    public static short asShort(final byte[] buf) {
        if (isEmpty(buf) || buf.length < 2)
            return 0;

        return (short) ((buf[0] & 0xFF) << 8 | (buf[1] & 0xFF));
    }

}
