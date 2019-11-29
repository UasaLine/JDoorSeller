package com.jds.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "specification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doorType_id")
    DoorType doorType;

    @Column(name = "material_id")
    String materialId;

    @Column(name = "name")
    String name;

    @Column(name = "value")
    double value;

    @Column(name = "formula")
    String formula;

    @Transient
    List<RawMaterials> availableValues;

}
