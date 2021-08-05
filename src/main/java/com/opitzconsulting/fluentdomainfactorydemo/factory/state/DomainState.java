package com.opitzconsulting.fluentdomainfactorydemo.factory.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class DomainState<T> {

    private T currentEntry;
    private final List<T> allEntries = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public T addEntry(T entry) {
        allEntries.add(entry);
        currentEntry = entry;
        return entry;
    }

    public T getCurrentEntry() {
        return currentEntry;
    }

    public boolean hasElement() {
        return currentEntry != null;
    }

    public Integer getNextNumber() {
        return counter.incrementAndGet();
    }

    public List<T> getAllEntries() {
        return allEntries;
    }

    public  T getCurrentOrCreate(Supplier<T> newValueSupplier) {
        if (this.hasElement()) {
            return this.getCurrentEntry();
        }
        T newElement = newValueSupplier.get();
        this.addEntry(newElement);
        return newElement;
    }

}
