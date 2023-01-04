package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

@GeneratePojoBuilder(withFactoryMethod = "*Builder", withCopyMethod = true)
public class Studium {

    private Studiengang studiengang;
    private Student student;
    private StudiumStatus studiumStatus;
    private LocalDate immatrikulationsDatum;
    private LocalDate exmatrikulationsDatum;
    private String immatrikulationsnummer;

    public String getImmatrikulationsnummer() {
        return immatrikulationsnummer;
    }

    public void setImmatrikulationsnummer(String immatrikulationsnummer) {
        this.immatrikulationsnummer = immatrikulationsnummer;
    }

    public Studiengang getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(Studiengang studiengang) {
        this.studiengang = studiengang;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudiumStatus getStudiumStatus() {
        return studiumStatus;
    }

    public void setStudiumStatus(StudiumStatus studiumStatus) {
        this.studiumStatus = studiumStatus;
    }

    public LocalDate getImmatrikulationsDatum() {
        return immatrikulationsDatum;
    }

    public void setImmatrikulationsDatum(LocalDate immatrikulationsDatum) {
        this.immatrikulationsDatum = immatrikulationsDatum;
    }

    public LocalDate getExmatrikulationsDatum() {
        return exmatrikulationsDatum;
    }

    public void setExmatrikulationsDatum(LocalDate exmatrikulationsDatum) {
        this.exmatrikulationsDatum = exmatrikulationsDatum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("studiengang", studiengang)
                .append("student", student)
                .append("studiumStatus", studiumStatus)
                .append("immatrikulationsDatum", immatrikulationsDatum)
                .append("exmatrikulationsDatum", exmatrikulationsDatum)
                .append("immatrikulationsnummer", immatrikulationsnummer)
                .toString();
    }
}
