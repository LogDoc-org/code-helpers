package org.logdoc.helpers.gears;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 21.08.2023 16:03
 * webtfs ☭ sweat and blood
 */
public class Tuple<A, B, C> {
    public final A first;
    public final B second;
    public final C third;

    private Tuple(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <F, S, T> Tuple<F, S, T> create(final F first, final S second, final T third) {
        return new Tuple<>(first, second, third);
    }
}
