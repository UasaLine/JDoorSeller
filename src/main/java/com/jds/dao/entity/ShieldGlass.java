package com.jds.dao.entity;

import com.jds.model.image.TypeOfImage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class ShieldGlass {
    private int id;

    private String idManufacturerProgram;

    private String name;

    private TypeOfImage typeOfImage;

    // kit
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldColor")
    private List<ImageEntity> shieldColor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldColor")
    private List<ImageEntity> shieldDesign;

    // design
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorColor")
    private List<ImageEntity> doorColor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorDesign")
    private List<ImageEntity> doorDesign;

    public ShieldGlass clearNonSerializingFields(){
        this.setShieldColor(null);
        this.setShieldDesign(null);
        this.setDoorColor(null);
        this.setDoorDesign(null);
        return this;
    }
}
