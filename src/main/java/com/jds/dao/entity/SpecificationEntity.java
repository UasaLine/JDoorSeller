package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "specification")
    private List<LineSpecification> lineSpecifications;

    public void clearNonSerializingFields(){
        for (LineSpecification line : lineSpecifications){
            line.setDoorType(null);
            line.setSpecification(null);
        }
    }

    public void addLine(LineSpecification line){
        lineSpecifications.add(line);
    }

}
