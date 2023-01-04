package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Fakultaet;
import com.opitzconsulting.fluentdomainfactorydemo.domain.FakultaetBuilder;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Universitaet;
import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;

import java.util.function.Consumer;

import static com.opitzconsulting.fluentdomainfactorydemo.factory.NoOperationConsumer.noop;

public class UniversitaetContext extends AbstractContext<Universitaet, Void> {


    public UniversitaetContext(TestdataFactory testdataFactory, Universitaet universitaet) {
        super(testdataFactory, universitaet);
    }

    public TestdataFactory doneUniversitaet() {
        return testdataFactory;
    }

    public FakultaetContext addFakultaet() {
        return addFakultaet(noop());
    }

    public FakultaetContext addFakultaet(Consumer<FakultaetBuilder> configurator) {
        FakultaetBuilder defaultBuilder = testdataFactory
                .defaultFakultaetBuilder();
        configurator.accept(defaultBuilder);

        //set university from current context.
        Universitaet universitaet = getEntity();
        defaultBuilder.withUniversitaet(universitaet);
        Fakultaet fakultaet = defaultBuilder.build();
        universitaet.addFakultaet(fakultaet);
        return new FakultaetContext(testdataFactory, this, fakultaet);
    }


}
