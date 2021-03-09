package com.jds.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jds.model.cutting.DoorPart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "material_formula")
public class MaterialFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram", nullable = false)
    private String idManufacturerProgram;

    @Column(name = "name")
    private String name;

    @Column(name = "condition1")
    private String condition1;

    @Column(name = "calculationFormula1")
    private String calculationFormula1;

    @Column(name = "condition2")
    private String condition2;

    @Column(name = "calculationFormula2")
    private String calculationFormula2;

    @Column(name = "condition3")
    private String condition3;

    @Column(name = "calculationFormula3")
    private String calculationFormula3;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formula", cascade = CascadeType.ALL)
    private List<SpecificationSetting> specificationSettings;

    public double Calculation(DoorEntity door){


        if (DoorPart.eval(parsePatternForCalculation(condition1,door))==1){
            return DoorPart.evalDouble(parsePatternForCalculation(calculationFormula1,door));
        }
        else if (DoorPart.eval(parsePatternForCalculation(condition2,door))==1){
            return DoorPart.evalDouble(parsePatternForCalculation(calculationFormula2,door));
        }
        else if (DoorPart.eval(parsePatternForCalculation(condition3,door))==1){
            return DoorPart.evalDouble(parsePatternForCalculation(calculationFormula3,door));
        }

       return 0;
    }

    public void decodeAfterJson(){
        this.condition1 = this.condition1.replace("//","*");
        this.condition1 = this.condition1.replace("/","+");

        this.condition2 = this.condition2.replace("//","*");
        this.condition2 = this.condition2.replace("/","+");

        this.condition3 = this.condition3.replace("//","*");
        this.condition3 = this.condition3.replace("/","+");

        this.calculationFormula1 = this.calculationFormula1.replace("//","*");
        this.calculationFormula1 = this.calculationFormula1.replace("/","+");

        this.calculationFormula2 = this.calculationFormula2.replace("//","*");
        this.calculationFormula2 = this.calculationFormula2.replace("/","+");

        this.calculationFormula3 = this.calculationFormula3.replace("//","*");
        this.calculationFormula3 = this.calculationFormula3.replace("/","+");

    }

    private  String parsePatternForCalculation(String condition,DoorEntity door) {

        //H - widthDoor
        condition =condition.replace("H", String.valueOf(door.getHeightDoor()));
        //L
        condition = condition.replace("L", String.valueOf(door.getWidthDoor()));
        //g - deepnessDoor
        condition = condition.replace("g",String.valueOf(door.getDeepnessDoor()));
        //t - thicknessDoorLeaf
        condition = condition.replace("t",String.valueOf(door.getThicknessDoorLeaf()));
        //qsh - DoorLeaf
        condition = condition.replace("qsh",String.valueOf(door.getDoorLeaf()));
        //k - metal
        condition = condition.replace("k",String.valueOf(door.getDoorLeaf()));
        //P1
        condition = condition.replace("P1",String.valueOf(door.getFirstSealingLine()));
        //P2
        condition = condition.replace("P2",String.valueOf(door.getSecondSealingLine()));
        //P3
        condition = condition.replace("P3",String.valueOf(door.getThirdSealingLine()));
        //N
        condition = condition.replace("N",String.valueOf(door.getFilling()));
        //P5
        condition = condition.replace("P5",String.valueOf(door.getAmplifierCloser()));

        return condition;
    }
}
