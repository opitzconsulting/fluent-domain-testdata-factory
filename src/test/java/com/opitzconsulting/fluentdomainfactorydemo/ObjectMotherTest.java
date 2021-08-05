package com.opitzconsulting.fluentdomainfactorydemo;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.domain.Studium;
import org.junit.jupiter.api.Test;

import static com.opitzconsulting.fluentdomainfactorydemo.ObjectMother.defaultStudent;
import static com.opitzconsulting.fluentdomainfactorydemo.ObjectMother.defaultStudium;

class ObjectMotherTest {


    @Test
    void objectMotherWithFactoryMethods() {
        Student student = defaultStudent();
        Studium studium = defaultStudium(student);
    }
}
