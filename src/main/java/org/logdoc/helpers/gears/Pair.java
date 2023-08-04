package org.logdoc.helpers.gears;

/**
 * @author Denis Danilin | me@loslobos.ru
 * 04.08.2023 15:57
 * code-helpers ☭ sweat and blood
 */
public class Pair<A, B> {
    public final A first;
    public final B second;

    private Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> create(final F first, final S second) {
        return new Pair<>(first, second);
    }
}
