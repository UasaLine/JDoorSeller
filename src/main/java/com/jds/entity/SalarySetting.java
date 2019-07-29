package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "salary_setting")
public class SalarySetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "metal")
    private double metal;

    @Column(name = "contactweldingforoneleaf")
    private int contactWeldingForOneLeaf;

    @Column(name = "contactweldingfornwoleaf")
    private int contactWeldingForTwoLeaf;

    public SalarySetting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }

    public int getContactWeldingForOneLeaf() {
        return contactWeldingForOneLeaf;
    }

    public void setContactWeldingForOneLeaf(int contactWeldingForOneLeaf) {
        this.contactWeldingForOneLeaf = contactWeldingForOneLeaf;
    }

    public int getContactWeldingForTwoLeaf() {
        return contactWeldingForTwoLeaf;
    }

    public void setContactWeldingForTwoLeaf(int contactWeldingForTwoLeaf) {
        this.contactWeldingForTwoLeaf = contactWeldingForTwoLeaf;
    }
}
