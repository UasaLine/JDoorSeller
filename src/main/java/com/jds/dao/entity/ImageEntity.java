package com.jds.dao.entity;

import com.jds.model.LimiItem;
import com.jds.model.SerializingFields;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
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
public class ImageEntity implements LimiItem, SerializingFields {

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

    @Column(name = "containsDesign")
    private int containsDesign;

    // kit
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldColor")
    private List<ImageEntity> shieldColor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shieldColor")
    private List<ImageEntity> shieldDesign;

    public ImageEntity() {
        pricePaintingMeterOfSpace = 0;
        smooth = 0;
    }

    public ImageEntity clearNonSerializingFields(){
        this.setShieldColor(null);
        this.setShieldDesign(null);
        return this;
    }
}
