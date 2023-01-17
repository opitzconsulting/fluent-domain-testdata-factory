package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;

public class StudiumContext extends AbstractContext<Studium, Studiengang> {

    public StudiumContext(TestdataFactory testdataFactory,
                          AbstractContext<Studiengang, ?> parentContext, Studium entity) {
        super(testdataFactory, parentContext, entity);
        testdataFactory.getStudiumState().addContext(this);
    }
}
