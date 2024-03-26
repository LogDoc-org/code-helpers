package org.logdoc.helpers.gears;

import java.util.Optional;

/**
 * Copyright (C) 2009-2019 Lightbend Inc. https://www.lightbend.com
 */
public class Either<L, R> {
    public final Optional<L> left;

    public final Optional<R> right;

    private Either(final Optional<L> left, final Optional<R> right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> Left(L value) {
        return new Either<>(Optional.of(value), Optional.empty());
    }

    public static <L, R> Either<L, R> Right(R value) {
        return new Either<>(Optional.empty(), Optional.of(value));
    }

    @Override
    public String toString() {
        return "Either(left: " + this.left + ", right: " + this.right + ")";
    }
}
