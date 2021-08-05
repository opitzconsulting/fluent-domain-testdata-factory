package com.opitzconsulting.fluentdomainfactorydemo.factory;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;
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

    public Universitaet addUniversitaet() {
        return addUniversitaet(noop());
    }
    public Universitaet addUniversitaet(Consumer<UniversitaetBuilder> configurator) {
        UniversitaetBuilder defaultBuilder = defaultUniversitaetBuilder();
        configurator.accept(defaultBuilder);
        return universitaetState.addEntry(defaultBuilder.build());
    }

    private UniversitaetBuilder defaultUniversitaetBuilder() {
        return universitaetBuilder()
                .withName("Universität " + universitaetState.getNextNumber());
    }

    public Fakultaet addFakultaet() {
        return addFakultaet(noop());
    }
    public Fakultaet addFakultaet(Consumer<FakultaetBuilder> configurator) {
        FakultaetBuilder defaultBuilder = defaultFakultaetBuilder(getCurrentUniversitaet());
        configurator.accept(defaultBuilder);
        return fakultaetState.addEntry(defaultBuilder.build());
    }

    private FakultaetBuilder defaultFakultaetBuilder(Universitaet uni) {
        return fakultaetBuilder().withUniversitaet(uni)
                .withName("Fakultät" + fakultaetState.getNextNumber());
    }

    private Universitaet getCurrentUniversitaet() {
        return universitaetState.getCurrentOrCreate(
                           () -> addUniversitaet());
    }



    private Fakultaet getCurrentFakultaet() {
        return fakultaetState.getCurrentOrCreate(
                           () -> initDefaultFakultaet());
    }

    private Fakultaet initDefaultFakultaet() {
        Universitaet universitaet = getCurrentUniversitaet();
        Fakultaet fakultaet = fakultaetBuilder().withName("Fakultät " + fakultaetState.getNextNumber())
                .withUniversitaet(universitaet)
                .build();
        universitaet.addFakultaet(fakultaet);
        return fakultaet;
    }


    public Studiengang addStudiengang() {
        return addStudiengang(noop());
    }
    public Studiengang addStudiengang(Consumer<StudiengangBuilder> StudiengangConfigurator) {
        StudiengangBuilder defaultBuilder = defaultStudiengangBuilder();
        StudiengangConfigurator.accept(defaultBuilder);
        Studiengang Studiengang = defaultBuilder.build();
        studiengangState.addEntry(Studiengang);
        return Studiengang;
    }

    private StudiengangBuilder defaultStudiengangBuilder() {
        Integer studiengangNumber = studiengangState.getNextNumber();
        return studiengangBuilder()
                .withBezeichnung("Studiengang " + studiengangNumber)
                .withFakultaet(getCurrentFakultaet());
    }

    private Studiengang getCurrentStudiengang() {
        return studiengangState.getCurrentOrCreate(
                           () -> addStudiengang());
    }

    private Studiengang initDefaultStudiengang() {
        Fakultaet fakultaet = getCurrentFakultaet();
        Studiengang studiengang = studiengangBuilder()
                .withBezeichnung("Studiengang " + studiengangState.getNextNumber())
                .withFakultaet(fakultaet)
                .build();
        fakultaet.addStudiengang(studiengang);
        return studiengang;
    }

    private Student getCurrentStudent() {
        return  studentState.getCurrentOrCreate(() -> addStudent());
    }

    public Student addStudent() {
        return addStudent(noop());
    }
    public Student addStudent(Consumer<StudentBuilder> studentConfigurator) {
        StudentBuilder defaultBuilder = defaultStudentBuilder();
        studentConfigurator.accept(defaultBuilder);
        Student student = defaultBuilder.build();
        studentState.addEntry(student);
        return student;
    }

    private StudentBuilder defaultStudentBuilder() {
        Integer studentNumber = studentState.getNextNumber();
        return studentBuilder()
                .withName("Student " + studentNumber)
                .withVorname("Vorname " + studentNumber)
                .withGeburtstag(LocalDate.now().minusYears(19));
    }


    public Studium addStudium() {
        return addStudium(noop());
    }
    public Studium addStudium(Consumer<StudiumBuilder> studiumConfigurator) {
        Student student = getCurrentStudent();
        Studiengang studiengang = getCurrentStudiengang();
        Studium studium = createStudium(student,studiengang,studiumConfigurator);
        studiumStage.addEntry(studium);
        return studium;
    }

    private static Studium createStudium(Student student,
                                        Studiengang studiengang,
                                        Consumer<StudiumBuilder> studiumConfigurator) {
        StudiumBuilder defaultStudium = createDefaultStudium(student, studiengang);
        studiumConfigurator.accept(defaultStudium);
        return defaultStudium.build();
    }
    private static StudiumBuilder createDefaultStudium(Student student, Studiengang studiengang) {
        return studiumBuilder().withStudent(student)
                .withStudiengang(studiengang)
                .withImmatrikulationsDatum(LocalDate.now())
                .withStudiumStatus(StudiumStatus.Immatrikuliert);
    }
}
