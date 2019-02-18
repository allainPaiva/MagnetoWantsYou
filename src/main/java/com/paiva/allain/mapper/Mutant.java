package com.paiva.allain.mapper;

import javax.persistence.*;

@Table(name = "mutants")
@Entity
public class Mutant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_MUTANT", updatable = false, nullable = false)
    private long id;

    @Column(name = "NITROGENOUS_BASE")
    private String nitrogenousBase;
    @Column(name = "IS_MUTANT")
    private boolean isMutant;

    public Mutant() {
    }

    public Mutant(long id, String nitrogenousBase, boolean isMutant) {
        this.id = id;
        this.nitrogenousBase = nitrogenousBase;
        this.isMutant = isMutant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNitrogenousBase() {
        return nitrogenousBase;
    }

    public void setNitrogenousBase(String nitrogenousBase) {
        this.nitrogenousBase = nitrogenousBase;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }
}
