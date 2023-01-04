package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractContext<E, P> {

    protected TestdataFactory testdataFactory;

    private AbstractContext<P, ?> parentContext;
    private final E entity;

    public AbstractContext(TestdataFactory testdataFactory, E entity) {
        this.testdataFactory = testdataFactory;
        this.entity = entity;
    }

    public AbstractContext(TestdataFactory testdataFactory, AbstractContext<P, ?> parentContext, E entity) {
        this.testdataFactory = testdataFactory;
        this.parentContext = parentContext;
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

    public <C extends AbstractContext<P, ?>> C getParentContext() {
        return (C) parentContext;
    }

    public boolean isEntityEquals(E candidate) {
        return getEntity().equals(candidate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                //.append("testdataFactory", testdataFactory)
                .append("entity", entity)
                .append("parentContext", parentContext)
                .toString();
    }
}
