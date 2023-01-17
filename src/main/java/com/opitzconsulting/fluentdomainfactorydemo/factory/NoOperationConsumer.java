package com.opitzconsulting.fluentdomainfactorydemo.factory;

import java.util.function.Consumer;

public class NoOperationConsumer {

    public static <T> Consumer<T> noop() {
        return t -> {
        };
    }
}
