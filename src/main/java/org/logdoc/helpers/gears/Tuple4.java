package org.logdoc.helpers.gears;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 21.08.2023 16:14
 * webtfs ☭ sweat and blood
 */
public class Tuple4<A, B, C, D> {
    public final A first;
    public final B second;
    public final C third;
    public final D fourth;

    private Tuple4(final A first, final B second, final C third, final D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <F, S, T, X> Tuple4<F, S, T, X> create(final F first, final S second, final T third, final X fourth) {
        return new Tuple4<>(first, second, third, fourth);
    }
}
