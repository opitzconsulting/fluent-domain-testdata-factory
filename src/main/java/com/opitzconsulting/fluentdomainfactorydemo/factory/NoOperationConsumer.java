package com.opitzconsulting.fluentdomainfactorydemo.factory;

import java.util.function.Consumer;

public class NoOperationConsumer {

    private static final Consumer<?> NO_OP = t -> {};
    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> noop() {
        return (Consumer<T>) NO_OP;
    }
}
