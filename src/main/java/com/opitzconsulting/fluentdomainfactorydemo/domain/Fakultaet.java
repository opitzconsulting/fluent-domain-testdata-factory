package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.ArrayList;
import java.util.List;

@GeneratePojoBuilder(withFactoryMethod = "*Builder")
public class Fakultaet {

    private String name;
    private Universitaet universitaet;
    private List<Studiengang> studiengangList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Universitaet getUniversitaet() {
        return universitaet;
    }

    public void setUniversitaet(Universitaet universitaet) {
        this.universitaet = universitaet;
    }

    public void addStudiengang(Studiengang studiengang) {
        studiengangList.add(studiengang);
    }
}
