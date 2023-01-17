package com.opitzconsulting.fluentdomainfactorydemo.factory.state;

import com.opitzconsulting.fluentdomainfactorydemo.factory.context.AbstractContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class DomainState<C extends AbstractContext<E, ?>, E> {

    private C currentContext;
    private final List<C> allContexts = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public C addContext(C context) {
        E entity = context.getEntity();
        if (!existsContextWithEntity(entity)) {
            allContexts.add(context);
            currentContext = context;
        }
        return context;
    }

    public C getCurrentContext() {
        return currentContext;
    }

    public boolean hasCurrentContext() {
        return currentContext != null;
    }

    public Integer getNextNumber() {
        return counter.incrementAndGet();
    }

    public List<C> getAllContexts() {
        return allContexts;
    }

    public List<E> getAllEntities() {
        return allContexts.stream().map(c -> c.getEntity()).toList();
    }

    public C getCurrentOrCreate(Supplier<C> newValueSupplier) {
        if (this.hasCurrentContext()) {
            return this.getCurrentContext();
        }
        C newElement = newValueSupplier.get();

        //if supplier already added the new element to this domainState object, we will not add it again
        if (!this.hasCurrentContext()) {
            this.addContext(newElement);
        }

        return newElement;
    }

    public boolean existsContextWithEntity(E entity) {
        return findContext(entity).isPresent();
    }

    public Optional<C> findContext(E entity) {
        return getAllContexts().stream().filter(c -> c.isEntityEquals(entity))
                .findFirst();
    }

    public void resetCurrentState() {
        currentContext = null;
    }

}
