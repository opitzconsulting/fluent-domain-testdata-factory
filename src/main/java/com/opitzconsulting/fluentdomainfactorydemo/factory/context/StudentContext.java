package com.opitzconsulting.fluentdomainfactorydemo.factory.context;

import com.opitzconsulting.fluentdomainfactorydemo.domain.Student;
import com.opitzconsulting.fluentdomainfactorydemo.factory.TestdataFactory;

public class StudentContext extends AbstractContext<Student, Void> {
    public StudentContext(TestdataFactory testdataFactory, Student entity) {
        super(testdataFactory, entity);
        testdataFactory.getStudentState().addContext(this);
    }
}
