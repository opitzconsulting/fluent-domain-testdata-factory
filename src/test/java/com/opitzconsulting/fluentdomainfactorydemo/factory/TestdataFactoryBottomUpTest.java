package com.opitzconsulting.fluentdomainfactorydemo.factory;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Fakultaet;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studiengang;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumStatus;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Universitaet;
import com.opitzconsulting.fluentdomainfactorydemo.factory.context.FakultaetContext;
import com.opitzconsulting.fluentdomainfactorydemo.factory.context.UniversitaetContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestdataFactoryBottomUpTest {

    TestdataFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TestdataFactory();
    }

    @Test
    void bottomUpWith0NestedObjects() {

        factory.addUniversitaet()
                .doneUniversitaet()
                .addUniversitaet(u -> u.withName("Universität Freiburg"));

        factory.addUniversitaet();

        assertThat(factory.getUniversitaetState().getAllEntities())
                .extracting(Universitaet::getName)
                .containsExactly("Universität 1", "Universität Freiburg", "Universität 3");
    }

    @Test
    void bottomUpWith1NestedObjects() {

        UniversitaetContext uniContext = factory.addUniversitaet(u -> u.withName("Universität Freiburg"));

        uniContext.addFakultaet().doneFaktultaet()
                .addFakultaet(f -> f.withName("Technische Fakultät"));
        factory
                .addFakultaet(f -> f.withName("Medizinische Fakultät"));

        assertThat(factory.getUniversitaetState().getAllEntities())
                .extracting(Universitaet::getName).containsExactly("Universität Freiburg");
        assertThat(factory.getFakultaetState().getAllEntities())
                .extracting(Fakultaet::getName).containsExactly("Fakultät 1", "Technische Fakultät", "Medizinische Fakultät");

        Universitaet actualUniversitaet = uniContext.getEntity();
        assertThat(actualUniversitaet.getFakultaetList())
                .as("Fakultäten wurden zur Uni hinzugefügt")
                .extracting("name")
                .containsExactly("Fakultät 1", "Technische Fakultät", "Medizinische Fakultät");

    }

    @Test
    void bottomUpWith5NestedObjects() {
        UniversitaetContext uniContext = factory.addUniversitaet(u->u.withName("Universität Freiburg"));

        uniContext
                .addFakultaet(f -> f.withName("Technische Fakultät"))
                //
                .addStudiengang(s -> s.withBezeichnung("Informatik"))
                .addStudium(student -> student.withName("Hans"),
                        studium -> studium.withStudiumStatus(StudiumStatus.Exmatrikuliert)
                                .withImmatrikulationsDatum(LocalDate.now().minusYears(1))
                                .withExmatrikulationsDatum(LocalDate.now().minusDays(2)))
                .addStudium(student -> student.withName("Susi"),
                        studium -> studium.withStudiumStatus(StudiumStatus.Immatrikuliert))
                .doneStudiengang()
                //
                .addStudiengang(s -> s.withBezeichnung("Microsystemtechnik"))
                .addStudium(student -> student.withName("Sandra"),
                        studium -> studium.withStudiumStatus(StudiumStatus.Immatrikuliert));

        uniContext
                .addFakultaet(f -> f.withName("Medizinische Fakultät"))
                .addStudiengang(f -> f.withBezeichnung("Humanmedizin")).doneStudiengang()
                .addStudiengang(f -> f.withBezeichnung("Tiermedizin"));

        List<FakultaetContext> fakultaetContexts = factory.getFakultaetState().getAllContexts();
        assertThat(fakultaetContexts).hasSize(2);
        assertThat(fakultaetContexts.stream().map(c->c.getEntity().getName()))
                .containsExactly("Technische Fakultät","Medizinische Fakultät");

        Fakultaet fakultaet = fakultaetContexts.get(0).getEntity();
        assertThat(fakultaet.getUniversitaet().getName()).isEqualTo("Universität Freiburg");
        assertThat(fakultaet.getStudiengangList()).hasSize(2);
        assertThat(fakultaet.getStudiengangList()).extracting(Studiengang::getBezeichnung)
                .containsExactly("Informatik", "Microsystemtechnik");

        assertThat(factory.getStudentState().getAllContexts()).hasSize(3);

        assertThat(factory.getStudiumState().getAllContexts()).hasSize(3);

    }

}
