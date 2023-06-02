package ru.gang.logdoc.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static ru.gang.logdoc.helpers.Digits.tol;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 02.06.2023 14:32
 * code-helpers ☭ sweat and blood
 */
public class Sporadics {
    private Sporadics() { }

    private static final SecureRandom rnd;

    static {
        rnd = new SecureRandom();
        rnd.setSeed(System.currentTimeMillis());
    }

    public static SecureRandom getRnd() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    public static void nextBytes(final byte[] bytes) {
        rnd.nextBytes(bytes);
    }

    public static byte[] getSeed(final int numBytes) {
        return SecureRandom.getSeed(numBytes);
    }

    public static byte[] generateSeed(final int numBytes) {
        return rnd.generateSeed(numBytes);
    }

    public static int nextInt() {
        return rnd.nextInt();
    }

    public static int nextInt(final int bound) {
        return rnd.nextInt(bound);
    }

    public static long nextLong() {
        return rnd.nextLong();
    }

    public static boolean nextBoolean() {
        return rnd.nextBoolean();
    }

    public static float nextFloat() {
        return rnd.nextFloat();
    }

    public static double nextDouble() {
        return rnd.nextDouble();
    }

    public static double nextGaussian() {
        return rnd.nextGaussian();
    }

    public static IntStream ints(final long streamSize) {
        return rnd.ints(streamSize);
    }

    public static IntStream ints() {
        return rnd.ints();
    }

    public static IntStream ints(final long streamSize, final int randomNumberOrigin, final int randomNumberBound) {
        return rnd.ints(streamSize, randomNumberOrigin, randomNumberBound);
    }

    public static IntStream ints(final int randomNumberOrigin, final int randomNumberBound) {
        return rnd.ints(randomNumberOrigin, randomNumberBound);
    }

    public static LongStream longs(final long streamSize) {
        return rnd.longs(streamSize);
    }

    public static LongStream longs() {
        return rnd.longs();
    }

    public static LongStream longs(final long streamSize, final long randomNumberOrigin, final long randomNumberBound) {
        return rnd.longs(streamSize, randomNumberOrigin, randomNumberBound);
    }

    public static LongStream longs(final long randomNumberOrigin, final long randomNumberBound) {
        return rnd.longs(randomNumberOrigin, randomNumberBound);
    }

    public static DoubleStream doubles(final long streamSize) {
        return rnd.doubles(streamSize);
    }

    public static DoubleStream doubles() {
        return rnd.doubles();
    }

    public static DoubleStream doubles(final long streamSize, final double randomNumberOrigin, final double randomNumberBound) {
        return rnd.doubles(streamSize, randomNumberOrigin, randomNumberBound);
    }

    public static DoubleStream doubles(final double randomNumberOrigin, final double randomNumberBound) {
        return rnd.doubles(randomNumberOrigin, randomNumberBound);
    }

    public static UUID generateUuid() {
        final byte[] buffer = new byte[16];
        rnd.nextBytes(buffer);

        return generateUuid(buffer);
    }

    private static UUID generateUuid(final byte[] buffer) {
        long r1, r2;

        r1 = tol(buffer, 0);
        r2 = tol(buffer, 1);

        r1 &= ~0xF000L;
        r1 |= 4 << 12;
        r2 = ((r2 << 2) >>> 2);
        r2 |= (2L << 62);

        return new UUID(r1, r2);
    }

}
