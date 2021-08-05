package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumStatus;

import java.time.LocalDate;

public class ObjectMother {

    public static Student defaultStudent() {
        Student student = new Student();
        student.setName("Maier");
        student.setVorname("Hans");
        return student;
    }

    public static Studium defaultStudium(Student student) {
        Studiengang studiengang = defaultStudiengang();
        return defaultStudium(student, studiengang);
    }

    private static Studium defaultStudium(Student student, Studiengang studiengang) {
        Studium studium = new Studium();
        studium.setStudent(student);
        studium.setStudiengang(studiengang);
        studium.setImmatrikulationsDatum(LocalDate.of(2021, 9, 1));
        studium.setStudiumStatus(StudiumStatus.Immatrikuliert);
        return studium;
    }

    public static Studium defaultStudium(Student student,
                                         Studiengang studiengang,
                                         LocalDate immatrikulationsDatum,
                                         StudiumStatus studiumStatus) {
        Studium studium = new Studium();
        studium.setStudent(student);
        studium.setStudiengang(studiengang);
        studium.setImmatrikulationsDatum(immatrikulationsDatum);
        studium.setStudiumStatus(studiumStatus);
        return studium;
    }

    private static Studiengang defaultStudiengang() {
        Studiengang studiengang = new Studiengang();
        studiengang.setBezeichnung("Vergleichende Literaturwissenschaft (Bachelor)");
        return studiengang;
    }
}
