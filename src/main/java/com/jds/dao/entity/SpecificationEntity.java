package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @NaturalId
    @Column(name = "manufacturer_id")
    private String manufacturerId;

    @Column(name = "name")
    private String name;

    @Column(name = "door_id")
    private int doorId;

    @ManyToOne()
    @JoinColumn(name = "doorType_id")
    private DoorType doorType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "specification")
    private List<LineSpecification> lines;

    public void clearNonSerializingFields() {
        if (doorType != null) {
            doorType.clearNonSerializingFields();
        }
        if (lines != null) {
            for (LineSpecification line : lines) {
                line.setSpecification(null);
            }
        }
    }

    public void addLine(LineSpecification line) {
        lines.add(line);
    }

    public void putLine(@NonNull LineSpecification line) {
        if (line.getId() > 0) {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).getId() == line.getId()) {
                    lines.set(i, line);
                }
            }
        } else {
            lines.add(line);
        }

    }

    public SpecificationEntity(int id) {
        this.id = id;
    }

    public void setSpecificationToAllLine() {

        SpecificationEntity simpleSpec = new SpecificationEntity(id);
        setSpecificationToAllLine(simpleSpec);

    }

    public void setSpecificationToAllLine(SpecificationEntity spec) {

        lines.stream()
                .map(line -> setSpecification(line, spec))
                .collect(Collectors.toList());
    }

    private LineSpecification setSpecification(LineSpecification lineSpecification, SpecificationEntity specificationEntity) {
        lineSpecification.setSpecification(specificationEntity);
        return lineSpecification;
    }

    public void fullIdBySpecification(SpecificationEntity spec) {

        id = spec.getId();
        List<LineSpecification> specLines = spec.getLines();

        for (int i = 0; i < specLines.size(); i++) {
            int lineId = specLines.get(i).getId();

            if (lines.size() > i) {
                lines.get(i).setId(lineId);
            }
        }

        setSpecificationToAllLine();

    }

    public SpecificationEntity cloneBySpecification() {

        SpecificationEntity newSpec = new SpecificationEntity();
        List<LineSpecification> newList = new ArrayList<>();
        for (LineSpecification line : lines) {
            newList.add(new LineSpecification(line));
        }
        newSpec.setLines(newList);
        return newSpec;
    }

    public String doorTypeName() {
        if (doorType != null) {
            return doorType.getName();
        }
        return "-";
    }

    public String sizeLines() {
        if (lines != null) {
            return String.valueOf(lines.size());
        }
        return "-";
    }
}
