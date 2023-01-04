package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("fakultaetList",
                        fakultaetList == null ? "" : fakultaetList.stream().map(Fakultaet::getName).collect(Collectors.joining(",")))
                .toString();
    }
}
