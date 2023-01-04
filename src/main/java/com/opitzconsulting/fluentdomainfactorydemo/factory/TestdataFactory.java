package com.opitzconsulting.fluentdomainfactorydemo.factory;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;
import com.opitzconsulting.fluentdomainfactorydemo.factory.context.*;
import com.opitzconsulting.fluentdomainfactorydemo.factory.state.*;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.opitzconsulting.fluentdomainfactorydemo.domain.FakultaetBuilder.fakultaetBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudentBuilder.studentBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudiengangBuilder.studiengangBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumBuilder.studiumBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.domain.UniversitaetBuilder.universitaetBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.factory.NoOperationConsumer.noop;

public class TestdataFactory {

    UniversitaetState universitaetState = new UniversitaetState();
    FakultaetState fakultaetState = new FakultaetState();
    StudiengangState studiengangState = new StudiengangState();
    StudentState studentState = new StudentState();
    StudiumState studiumStage = new StudiumState();

    // Universität
    private UniversitaetContext getCurrentUniversitaet() {
        return universitaetState.getCurrentOrCreate(this::addUniversitaet);
    }

    public UniversitaetContext addUniversitaet() {
        return addUniversitaet(noop());
    }

    public UniversitaetContext addUniversitaet(Consumer<UniversitaetBuilder> configurator) {
        UniversitaetBuilder defaultBuilder = defaultUniversitaetBuilder();
        configurator.accept(defaultBuilder);
        UniversitaetContext universitaetContext = new UniversitaetContext(this, defaultBuilder.build());
        return universitaetState.addContext(universitaetContext);
    }

    private UniversitaetBuilder defaultUniversitaetBuilder() {
        return universitaetBuilder()
                .withName("Universität " + universitaetState.getNextNumber());
    }

    //
    // Fakultät
    //
    private FakultaetContext getCurrentFakultaet() {
        return fakultaetState.getCurrentOrCreate(this::addFakultaet);
    }

    public FakultaetContext addFakultaet() {
        return addFakultaet(noop());
    }

    public FakultaetContext addFakultaet(Consumer<FakultaetBuilder> configurator) {
        FakultaetBuilder defaultBuilder = defaultFakultaetBuilder();
        configurator.accept(defaultBuilder);

        Fakultaet fakultaet = defaultBuilder.build();

        UniversitaetContext universitaetContext =
                universitaetState.findContext(fakultaet.getUniversitaet())
                        .orElseGet(this::getCurrentUniversitaet);
        Universitaet universitaet = universitaetContext.getEntity();
        universitaet.addFakultaet(fakultaet);
        fakultaet.setUniversitaet(universitaet);
        return new FakultaetContext(this, universitaetContext, fakultaet);
    }

    public FakultaetBuilder defaultFakultaetBuilder() {
        return fakultaetBuilder()
                .withName("Fakultät " + fakultaetState.getNextNumber());
    }


    //
    // Studiengang
    //
    public StudiengangContext addStudiengang() {
        return addStudiengang(noop());
    }

    public StudiengangContext addStudiengang(Consumer<StudiengangBuilder> StudiengangConfigurator) {
        StudiengangBuilder defaultBuilder = defaultStudiengangBuilder();
        StudiengangConfigurator.accept(defaultBuilder);
        Studiengang studiengang = defaultBuilder.build();

        FakultaetContext fakultaetContext
                = fakultaetState.findContext(studiengang.getFakultaet())
                .orElseGet(this::getCurrentFakultaet);
        studiengang.setFakultaet(fakultaetContext.getEntity());
        return new StudiengangContext(this, fakultaetContext, studiengang);
    }

    public StudiengangBuilder defaultStudiengangBuilder() {
        Integer studiengangNumber = studiengangState.getNextNumber();
        return studiengangBuilder()
                .withBezeichnung("Studiengang " + studiengangNumber);
    }

    private StudiengangContext getCurrentStudiengang() {
        return studiengangState.getCurrentOrCreate(this::addStudiengang);
    }

    //
    // Studium
    //
    public StudiumContext addStudium() {
        return addStudium(noop());
    }

    public StudiumContext addStudium(Consumer<StudiumBuilder> studiumConfigurator) {
        StudiumBuilder defaultBuilder = defaultStudiumBuilder();

        studiumConfigurator.accept(defaultBuilder);

        Studium studium = defaultBuilder.build();
        StudiengangContext studiengangContext
                = studiengangState.findContext(studium.getStudiengang())
                .orElseGet(this::getCurrentStudiengang);

        if (studium.getStudent() == null) {
            studium.setStudent(getCurrentStudent().getEntity());
        }

        studium.setStudiengang(studiengangContext.getEntity());
        return new StudiumContext(this, studiengangContext, studium);
    }

    public StudiumBuilder defaultStudiumBuilder() {
        return studiumBuilder()
                .withImmatrikulationsDatum(LocalDate.now())
                .withStudiumStatus(StudiumStatus.Immatrikuliert);
    }

    //
    //Student
    //

    public StudentContext addStudent() {
        return addStudent(noop());
    }

    public StudentContext addStudent(Consumer<StudentBuilder> studentConfigurator) {
        StudentBuilder defaultBuilder = defaultStudentBuilder();
        studentConfigurator.accept(defaultBuilder);
        Student student = defaultBuilder.build();

        return new StudentContext(this, student);
    }

    private StudentBuilder defaultStudentBuilder() {
        Integer studentNumber = studentState.getNextNumber();
        return studentBuilder()
                .withName("Student " + studentNumber)
                .withVorname("Vorname " + studentNumber)
                .withGeburtstag(LocalDate.now().minusYears(19));
    }

    private StudentContext getCurrentStudent() {
        return studentState.getCurrentOrCreate(this::addStudent);
    }

    public UniversitaetState getUniversitaetState() {
        return universitaetState;
    }

    public FakultaetState getFakultaetState() {
        return fakultaetState;
    }

    public StudiengangState getStudiengangState() {
        return studiengangState;
    }

    public StudentState getStudentState() {
        return studentState;
    }

    public StudiumState getStudiumStage() {
        return studiumStage;
    }
}
