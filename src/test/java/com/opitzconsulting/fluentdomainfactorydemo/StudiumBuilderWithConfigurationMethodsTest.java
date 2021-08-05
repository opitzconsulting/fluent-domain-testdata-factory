package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.opitzconsulting.fluentdomainfactorydemo.StudiumBuilderWithConfigurationMethods.createStudium;
import static com.opitzconsulting.fluentdomainfactorydemo.ObjectMother.defaultStudent;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudiumBuilderWithConfigurationMethodsTest {

    @Test
    void factoryMitKonfigurationsMethoden() {
        Student student = defaultStudent();

        Studium studium1 = createStudium(student,
                                         new Studiengang("Sinologie"),
                                         s -> s.withImmatrikulationsDatum(LocalDate.now().minusMonths(1))
                                                 .withExmatrikulationsDatum(LocalDate.now().minusDays(1))
                                                 .withStudiumStatus(StudiumStatus.Exmatrikuliert));

        Studium studium2 = createStudium(student,
                                         new Studiengang("Informatik Bachelor"));

        assertThat(studium1.getStudiumStatus()).isEqualTo(StudiumStatus.Exmatrikuliert);

        //proof that factory method creates reasonable default values:
        assertThat(studium2.getStudiumStatus()).isEqualTo(StudiumStatus.Immatrikuliert);
        assertThat(studium2.getImmatrikulationsDatum()).isEqualTo(LocalDate.now());
        assertThat(studium2.getExmatrikulationsDatum()).isNull();

    }
}
