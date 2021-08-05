package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.*;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudiumBuilder.studiumBuilder;

public class StudiumBuilderWithConfigurationMethods {

    // 1. erstellen der Methode, welche einen sinnvollen default Datensatz erstellt
    private static StudiumBuilder createDefaultStudium(Student student, Studiengang studiengang) {
        return studiumBuilder().withStudent(student)
                .withStudiengang(studiengang)
                .withImmatrikulationsDatum(LocalDate.now())
                .withStudiumStatus(StudiumStatus.Immatrikuliert);
    }

    // 2. Erstellen einer Methode, mittels der via erster Methode erstellte Default-Datensatz modifiziert werden kann
    public static Studium createStudium(Student student,
                                        Studiengang studiengang,
                                        Consumer<StudiumBuilder> studiumConfigurator) {
        StudiumBuilder defaultStudium = createDefaultStudium(student, studiengang);
        studiumConfigurator.accept(defaultStudium);
        return defaultStudium.build();
    }

    public static Studium createStudium(Student student,
                                        Studiengang studiengang) {
        return createStudium(student, studiengang, s -> {
        });
    }
}
