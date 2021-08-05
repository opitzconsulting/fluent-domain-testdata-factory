package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.StudentBuilder;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.opitzconsulting.fluentdomainfactorydemo.domain.StudentBuilder.studentBuilder;

public class StudentBuilderWithConfigurationMethods {

    // 1. erstellen der Methode, welche einen sinnvollen default Studenten erstellt
    private static StudentBuilder createDefaultStudentBuilder() {
        return studentBuilder()
                .withName("Maier")
                .withVorname("Hans")
                .withGeburtstag(LocalDate.now().minusYears(19));
    }

    // 2. Erstellen einer Methode, mittels der via erster Methode erstellte Default-Datensatz modifiziert werden kann
    public static Student createStudent(Consumer<StudentBuilder> studentConfigurer) {
        StudentBuilder defaultBuilder = createDefaultStudentBuilder();
        studentConfigurer.accept(defaultBuilder);
        return defaultBuilder.build();
    }

    public static Student createStudent() {
        return createStudent(s -> {
        });
    }
}
