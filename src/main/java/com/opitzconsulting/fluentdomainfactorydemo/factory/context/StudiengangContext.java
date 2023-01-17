package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;
import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;

import java.util.function.Consumer;

public class StudiengangContext extends AbstractContext<Studiengang, Fakultaet> {

    public StudiengangContext(TestdataFactory testdataFactory,
                              AbstractContext<Fakultaet, ?> parentContext, Studiengang entity) {
        super(testdataFactory, parentContext, entity);
        testdataFactory.getStudiengangState().addContext(this);
    }

    public StudiengangContext addStudium(Consumer<StudentBuilder> studentConfigurator, Consumer<StudiumBuilder> studiumConfigurator) {
        StudentContext studentContext = testdataFactory.addStudent(studentConfigurator);

        Student student = studentContext.getEntity();

        Studiengang studiengang = getEntity();

        StudiumBuilder defaultBuilder = testdataFactory.defaultStudiumBuilder();
        studiumConfigurator.accept(defaultBuilder);
        defaultBuilder.withStudiengang(studiengang)
                .withStudent(student);

        Studium studium = defaultBuilder.build();
        studium.setStudiengang(studiengang);

        testdataFactory.getStudiumState().addContext(new StudiumContext(testdataFactory, this, studium));


        return this;
    }

    public FakultaetContext doneStudiengang() {
        return getParentContext();
    }
}
