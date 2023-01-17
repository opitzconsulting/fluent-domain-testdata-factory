package com.opitzconsulting.fluentdomainfactorydemo.factory;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class TestdataFactoryTest {

    TestdataFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TestdataFactory();
    }


    @Test
    void addUniversitaet() {
        factory.addUniversitaet();
        List<Universitaet> universitaetList = factory.getUniversitaetState().getAllEntities();
        assertThat(universitaetList).extracting(Universitaet::getName).containsExactly("Universität 1");
    }

    @Test
    void addFakultaet() {
        factory.addFakultaet();

        List<Fakultaet> fakultaetList = factory.getFakultaetState().getAllEntities();
        assertThat(fakultaetList).extracting(Fakultaet::getName).containsExactly("Fakultät 1");

        Fakultaet fakultaet = fakultaetList.get(0);
        assertThat(fakultaet.getUniversitaet()).isNotNull();
        assertThat(fakultaet.getUniversitaet().getName()).isEqualTo("Universität 1");
    }

    @Test
    void addFakultaetUsesLastConfiguredUniversitaet() {
        factory.addUniversitaet();
        factory.addUniversitaet();
        factory.addFakultaet();

        assertThat(factory.getFakultaetState().getAllEntities().get(0).getUniversitaet().getName())
                .isEqualTo("Universität 2");
    }

    @Test
    void addStudent() {
        Student student1 = factory.addStudent().getEntity();
        Student student2 = factory.addStudent().getEntity();
        assertThat(student1.getName()).isEqualTo("Student 1");
        assertThat(student2.getName()).isEqualTo("Student 2");
    }

    @Test
    void addStudiumDependenciesAreCreatedPerDefault() {

        Studium studium = factory.addStudium().getEntity();
        assertThat(studium.getStudent()).isNotNull();
        assertThat(studium.getStudent().getName()).isEqualTo("Student 1");

        Studiengang actualStudiengang = studium.getStudiengang();

        assertThat(actualStudiengang)
                .as("Studium ist mit Studiengang verknüpft!")
                .isNotNull();
        assertThat(actualStudiengang.getBezeichnung()).isEqualTo("Studiengang 1");

        assertThat(actualStudiengang.getFakultaet())
                .as("Studiengang ist mit Fakultät verknüpft!")
                .isNotNull();
        assertThat(actualStudiengang.getFakultaet().getName()).isEqualTo("Fakultät 1");
    }

    @Test
    void addStudiengangCreatesDefaultNames() {
        Studiengang studiengang1 = factory.addStudiengang().getEntity();
        Studiengang studiengang2 = factory.addStudiengang().getEntity();

        assertThat(studiengang1.getBezeichnung()).isEqualTo("Studiengang 1");
        assertThat(studiengang2.getBezeichnung()).isEqualTo("Studiengang 2");
    }

    @Test
    void addStudiengangDependenciesAreCreated() {
        Studiengang studiengang = factory.addStudiengang().getEntity();


        assertThat(studiengang.getFakultaet())
                .as("Fakultät wurde automatisch erstellt")
                .isNotNull()
                //
                .extracting(Fakultaet::getName)
                .as("Fakulät hat default - Name")
                .isEqualTo("Fakultät 1");

        assertThat(studiengang.getFakultaet().getUniversitaet())
                .as("Universität wurde automatisch erstellt")
                .isNotNull()
                //
                .extracting(Universitaet::getName)
                .as("Universität hat default - Name")
                .isEqualTo("Universität 1");
    }

    @Test
    void addStudiengangUsesCurrentFakultaet() {
        factory.addStudiengang();
        factory.addFakultaet(f -> f.withName("Meine Fakultät"));
        factory.addStudiengang();

        Studiengang studiengang1 = factory.getStudiengangState().getAllEntities().get(0);
        assertThat(studiengang1.getFakultaet().getName())
                .as("Fakultät hat default - Name")
                .isEqualTo("Fakultät 1");

        Studiengang studiengang2 = factory.getStudiengangState().getAllEntities().get(1);
        assertThat(studiengang2.getFakultaet().getName())
                .as("Zuletzt erstellten Fakultät wurde verwendet")
                .isEqualTo("Meine Fakultät");
    }

    @Test
    void addUniversitaetResetsContexts() {
        Studiengang studiengang1 = factory.addStudiengang().getEntity();

        assertThat(studiengang1.getFakultaet().getName())
                .isEqualTo("Fakultät 1");
        assertThat(studiengang1.getFakultaet().getUniversitaet().getName())
                .isEqualTo("Universität 1");

        factory.addUniversitaet(u -> u.withName("Hamburg"));
        Studiengang studiengang2 = factory.addStudiengang().getEntity();

        assertThat(studiengang2.getFakultaet().getName())
                .as("addUniversitaet() followed by addStudiengang() implicitly creates new Fakultaet")
                .isEqualTo("Fakultät 2");
        assertThat(studiengang2.getFakultaet().getUniversitaet().getName())
                .as("new Fakultät should be added to recently created Universität")
                .isEqualTo("Hamburg");

    }

    @Test
    void addStudiumUsedCurrentStudent() {
        String studentName = "Martin";
        factory.addStudent(s -> s.withName(studentName));

        Studium studium = factory.addStudium().getEntity();
        assertThat(studium.getStudent().getName()).isEqualTo(studentName);
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo("Studiengang 1");

        String studentName2 = "Sabine";
        factory.addStudent(s -> s.withName(studentName2));
        studium = factory.addStudium().getEntity();
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo("Studiengang 1");

        assertThat(studium.getStudent().getName())
                .as("Last created Student is used for new Studium")
                .isEqualTo(studentName2);

        Studiengang studiengangEnglisch = factory.addStudiengang(s -> s.withBezeichnung("Englisch")).getEntity();
        studium = factory.addStudium(s -> s.withStudiumStatus(StudiumStatus.Exmatrikuliert)).getEntity();
        assertThat(studium.getStudiengang().getBezeichnung()).isEqualTo(studiengangEnglisch.getBezeichnung());


    }


}