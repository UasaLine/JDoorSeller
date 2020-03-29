package com.jds.dao.entity;

import com.jds.model.LimiItem;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Door_Colors")
public class ImageEntity implements LimiItem {

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

}
