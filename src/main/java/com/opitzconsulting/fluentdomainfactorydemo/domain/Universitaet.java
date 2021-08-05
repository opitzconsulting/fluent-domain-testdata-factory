package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.ArrayList;
import java.util.List;

@GeneratePojoBuilder(withFactoryMethod = "*Builder", withCopyMethod = true)
public class Universitaet {

    private String name;
    private List<Fakultaet> fakultaetList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fakultaet> getFakultaetList() {
        return fakultaetList;
    }

    public void setFakultaetList(List<Fakultaet> fakultaetList) {
        this.fakultaetList = fakultaetList;
    }

    public void addFakultaet(Fakultaet fakultaet) {
        fakultaetList.add(fakultaet);
    }
}
