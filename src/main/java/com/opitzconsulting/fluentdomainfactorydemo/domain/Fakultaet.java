package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@GeneratePojoBuilder(withFactoryMethod = "*Builder")
public class Fakultaet {

    private String name;
    private Universitaet universitaet;
    private final List<Studiengang> studiengangList = new ArrayList<>();

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

    public List<Studiengang> getStudiengangList() {
        return studiengangList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("universitaet", universitaet)
                .append("studiengangList", studiengangList)
                .toString();
    }
}
