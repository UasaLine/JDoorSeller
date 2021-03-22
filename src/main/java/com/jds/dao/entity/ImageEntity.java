package com.jds.dao.entity;

import com.jds.model.LimiItem;
import com.jds.model.SerializingFields;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import com.jds.model.image.TypeOfShieldDesign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Door_Colors")
public class ImageEntity implements LimiItem, SerializingFields, Comparable<ImageEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "idManufacturerProgram")
    private String idManufacturerProgram;

    @Column(name = "name")
    private String name;

    @Column(name = "picturePathFirst")
    private String picturePath;

    @Column(name = "maskPath")
    private String maskPath;

    @Column(name = "price")
    private int pricePaintingMeterOfSpace;

    @Column(name = "smooth")
    private int smooth;

    @Column(name = "typeOfImage")
    @Enumerated(EnumType.STRING)
    private TypeOfImage typeOfImage;

    @Column(name = "typeOfDoorColor")
    @Enumerated(EnumType.STRING)
    private TypeOfDoorColor typeOfDoorColor;

    @Column(name = "typeOfShieldDesign")
    @Enumerated(EnumType.STRING)
    private TypeOfShieldDesign typeOfShieldDesign;

    @Column(name = "containsDesign")
    private int containsDesign;

    @Column(name = "containsGlass")
    private int containsGlass;

    @Column(name = "containsWoodPanel")
    private int containsWoodPanel;

    @Column(name = "containsOtherColor")
    private int containsOtherColor;

    @Column(name = "containsLimit")
    private int containsLimit;

    // kit
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldColor")
    private List<ImageEntity> shieldColor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldDesign")
    private List<ImageEntity> shieldDesign;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldOverColor")
    private List<ImageEntity> shieldOverColor;

    // design
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorColor")
    private List<ImageEntity> doorColor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doorDesign")
    private List<ImageEntity> doorDesign;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "outShieldColor")
    private List<ImageEntity> outShieldColor;

    public ImageEntity() {
        pricePaintingMeterOfSpace = 0;
        smooth = 0;
    }

    public ImageEntity(int id) {
        this.id = id;
    }

    public ImageEntity clearNonSerializingFields() {
        this.setShieldColor(null);
        this.setShieldDesign(null);
        this.setDoorColor(null);
        this.setDoorDesign(null);
        this.setOutShieldColor(null);
        this.setShieldOverColor(null);
        return this;
    }

    @Override
    public int compareTo(ImageEntity imageEntity) {
        return name.compareTo(imageEntity.getName());
    }

    public String getViewAdditionalType() {

        if (typeOfImage == TypeOfImage.DOOR_COLOR) {
            return typeOfDoorColor != null ? typeOfDoorColor.toString() : "";
        }
        if (typeOfImage == TypeOfImage.SHIELD_DESIGN) {
            return typeOfShieldDesign != null ? typeOfShieldDesign.toString() : "";
        }
        return "";
    }
}
