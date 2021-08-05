package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudiengangBuilder.studiengangBuilder;
import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumBuilder.studiumBuilder;

public class BuilderTest {

    @Test
    void naiverAnsatzMitNaivenBuildern() {
        Student student = StudentBuilder.studentBuilder()
                .withName("Maier")
                .withVorname("Hans")
                .build();
        Studiengang studiengang = studiengangBuilder()
                .withBezeichnung("Vergleichende Literaturwissenschaft (Bachelor)")
                .build();
        Studium studium = studiumBuilder()
                .withStudent(student)
                .withStudiengang(studiengang)
                .withExmatrikulationsDatum(LocalDate.of(2021, 9, 1))
                .withStudiumStatus(StudiumStatus.Immatrikuliert)
                .build();
    }


}
