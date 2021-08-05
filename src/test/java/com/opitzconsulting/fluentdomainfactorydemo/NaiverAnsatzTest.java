package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.opitzconsulting.fluentdomainfactorydemo.ObjectMother.defaultStudent;
import static com.opitzconsulting.fluentdomainfactorydemo.ObjectMother.defaultStudium;

class NaiverAnsatzTest {

    @Test
    void naiverAnsatz() {
        Student student = new Student();
        student.setName("Maier");
        student.setVorname("Hans");

        Studiengang studiengang = new Studiengang();
        studiengang.setBezeichnung("Vergleichende Literaturwissenschaft (Bachelor)");

        Studium studium = new Studium();
        studium.setStudent(student);
        studium.setStudiengang(studiengang);
        studium.setImmatrikulationsDatum(LocalDate.of(2021, 9, 1));
        studium.setStudiumStatus(StudiumStatus.Immatrikuliert);

    }



    @Test
    void naiverAnsatzUtilityMethoden2() {
        Student student = defaultStudent();

        Studium studium3 = new Studium();
        studium3.setStudent(student);
        studium3.setStudiengang(new Studiengang("Sinologie"));
        studium3.setImmatrikulationsDatum(LocalDate.of(2020, 9, 1));
        studium3.setStudiumStatus(StudiumStatus.Exmatrikuliert);
        Studium studium1 = studium3;

        LocalDate datumNeuerStudiengang = studium1.getImmatrikulationsDatum().plusMonths(1);
        studium1.setExmatrikulationsDatum(datumNeuerStudiengang);

        Studium studium = new Studium();
        studium.setStudent(student);
        studium.setStudiengang(new Studiengang("Informatik Bachelor"));
        studium.setImmatrikulationsDatum(datumNeuerStudiengang);
        studium.setStudiumStatus(StudiumStatus.Immatrikuliert);
        Studium studium2 = studium;

    }


}
