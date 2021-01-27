package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "line_specification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @Column(name = "formula")
    private String formula;

    @Column(name = "release_operation")
    private String releaseOperation;

    @Column(name = "write_off_operation")
    private String writeOffOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification")
    private SpecificationEntity specification;

    public LineSpecification clearNonSerializingFields() {
        specification = null;
        return this;
    }

    public LineSpecification(LineSpecification line) {
        materialId = line.materialId;
        name = line.name;
        value = line.value;
        releaseOperation = line.releaseOperation;
        writeOffOperation = line.writeOffOperation;
    }
}
