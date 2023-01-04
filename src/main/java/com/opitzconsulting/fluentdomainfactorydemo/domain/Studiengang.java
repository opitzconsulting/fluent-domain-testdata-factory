package com.opitzconsulting.fluentdomainfactorydemo.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@GeneratePojoBuilder(withFactoryMethod = "*Builder", withCopyMethod = true)
public class Studiengang {

    private String bezeichnung;
    private Fakultaet fakultaet;

    public Studiengang() {
    }

    public Studiengang(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Fakultaet getFakultaet() {
        return fakultaet;
    }

    public void setFakultaet(Fakultaet fakultaet) {
        this.fakultaet = fakultaet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("bezeichnung", bezeichnung)
                .append("fakultaet", fakultaet)
                .toString();
    }
}
