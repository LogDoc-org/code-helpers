package org.logdoc.helpers.gears;

import java.util.function.Supplier;

/**
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */
public class LazySupplier<T> implements Supplier<T> {

    private final Supplier<T> instantiator;
    private T value;

    private LazySupplier(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> Supplier<T> lazy(Supplier<T> creator) {
        return new LazySupplier<>(creator);
    }

    @Override
    public T get() {
        if (this.value == null) {
            this.value = instantiator.get();
        }
        return this.value;
    }
}
