package com.jds.entity;

import javax.persistence.*;

@Entity
@Table(name = "specification_setting")
public class SpecificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rawMaterials_id")
    private RawMaterials rawMaterials;

    @Column(name = "formula")
    private String formula;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @Column(name = "metal")
    private double metal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RawMaterials getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(RawMaterials rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public DoorType getDoorType() {
        return doorType;
    }

    public void setDoorType(DoorType doorType) {
        this.doorType = doorType;
    }

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }


}
