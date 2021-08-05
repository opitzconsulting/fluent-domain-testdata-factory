package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import org.junit.jupiter.api.Test;

import static com.opitzconsulting.fluentdomainfactorydemo.StudentBuilderWithConfigurationMethods.createStudent;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudentBuilderWithConfigurationMethodsTest {

    @Test
    void studentAndDefaultStudent() {
        Student s1 = createStudent();
        Student s2 = createStudent(s-> s.withVorname("Peter"));

        assertThat(s1.getVorname()).isEqualTo("Hans");
        assertThat(s1.getName()).isEqualTo("Maier");

        assertThat(s2.getVorname()).isEqualTo("Peter");
        assertThat(s2.getName()).isEqualTo("Maier");
    }
}
