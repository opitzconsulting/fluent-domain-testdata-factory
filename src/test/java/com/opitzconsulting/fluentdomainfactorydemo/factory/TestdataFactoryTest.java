package com.opitzconsulting.fluentdomainfactorydemo.factory;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TestdataFactoryTest {

    TestdataFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TestdataFactory();
    }

    @Test
    void addStudent() {
        Student student1 = factory.addStudent();
        Student student2 = factory.addStudent();
        assertThat(student1.getName()).isEqualTo("Student 1");
        assertThat(student2.getName()).isEqualTo("Student 2");
    }
    @Test
    void addStudiumDependenciesAreCreatedPerDefault() {

        Studium studium = factory.addStudium();
        assertThat(studium.getStudent()).isNotNull();
        assertThat(studium.getStudent().getName()).isEqualTo("Student 1");

        Studiengang actualStudiengang = studium.getStudiengang();

        assertThat(actualStudiengang).isNotNull();
        assertThat(actualStudiengang.getBezeichnung()).isEqualTo("Studiengang 1");

        assertThat(actualStudiengang.getFakultaet()).isNotNull();
        assertThat(actualStudiengang.getFakultaet().getName()).isEqualTo("Fakultät 1");
    }

    @Test
    void addStudiumUsedCurrentStudent() {
        String studentName = "Martin";
        factory.addStudent(s->s.withName(studentName));

        Studium studium = factory.addStudium();
        assertThat(studium.getStudent().getName()).isEqualTo(studentName);
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo("Studiengang 1");

        String studentName2 = "Sabine";
        factory.addStudent(s->s.withName(studentName2));
        studium = factory.addStudium();
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo("Studiengang 1");

        assertThat(studium.getStudent().getName())
                .as("Last created Student is used for new Studium")
                .isEqualTo(studentName2);

        Studiengang studiengangEnglisch = factory.addStudiengang(s -> s.withBezeichnung("Englisch"));
        studium = factory.addStudium(s-> s.withStudiumStatus(StudiumStatus.Exmatrikuliert));
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo(studiengangEnglisch.getBezeichnung());

    }
}