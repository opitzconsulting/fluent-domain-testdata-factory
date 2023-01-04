package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Fakultaet;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiengangBuilder;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Universitaet;
import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;

import java.util.function.Consumer;

public class FakultaetContext extends AbstractContext<Fakultaet, Universitaet> {


    public FakultaetContext(TestdataFactory testdataFactory, UniversitaetContext universitaetContext, Fakultaet fakultaet) {
        super(testdataFactory, universitaetContext, fakultaet);
        testdataFactory.getFakultaetState().addContext(this);
    }

    public StudiengangContext addStudiengang(Consumer<StudiengangBuilder> configurator) {
        StudiengangBuilder defaultBuilder = testdataFactory.defaultStudiengangBuilder();
        configurator.accept(defaultBuilder);

        //set Fakultät from current context
        Fakultaet entity = getEntity();

        defaultBuilder.withFakultaet(entity);
        Studiengang studiengang = defaultBuilder.build();
        entity.addStudiengang(studiengang);
        return new StudiengangContext(testdataFactory, this, studiengang);
    }

    public UniversitaetContext doneFaktultaet() {
        return getParentContext();
    }
}
