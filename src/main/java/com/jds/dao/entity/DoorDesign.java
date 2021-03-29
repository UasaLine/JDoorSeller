package com.jds.dao.entity;

import com.jds.dao.repository.ColorRepository;
import com.jds.model.AvailableFieldsForSelection;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "design")
public class DoorDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "door_color")
    private ImageEntity doorColor;

    @ManyToOne()
    @JoinColumn(name = "door_design")
    private ImageEntity doorDesign;

    @ManyToOne()
    @JoinColumn(name = "out_shield")
    private ImageEntity outShieldColor;

    @ManyToOne()
    @JoinColumn(name = "out_shield_design")
    private ImageEntity outShieldDesign;

    @OneToOne(mappedBy = "doorDesign", fetch = FetchType.LAZY)
    private DoorEntity door;

    public static DoorDesign instanceDesign(ImageEntity doorColor,
                                            ImageEntity doorDesign,
                                            ImageEntity outShieldColor,
                                            ImageEntity outShieldDesign
    ) {
        DoorDesign design = new DoorDesign();

        design.setDoorColor(doorColor);
        design.setDoorDesign(doorDesign);
        design.setOutShieldColor(outShieldColor);
        design.setOutShieldDesign(outShieldDesign);

        design.clearNonSerializingFields();
        return design;
    }

    private static ImageEntity getFirst(List<ImageEntity> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean exists() {
        if (doorColor == null && doorDesign == null) {
            return false;
        }
        return true;
    }

    public DoorDesign clearNonSerializingFields() {
        door = null;
        if (doorColor != null) {
            doorColor.clearNonSerializingFields();
        }
        if (doorDesign != null) {
            doorDesign.clearNonSerializingFields();
        }
        if (outShieldColor != null) {
            outShieldColor.clearNonSerializingFields();
        }
        if (outShieldDesign != null) {
            outShieldDesign.clearNonSerializingFields();
        }

        return this;
    }

    public String getOutShieldName(){
        StringBuffer name = new StringBuffer("");
        if (outShieldColor != null) {
            name.append(outShieldColor.getName());
            if (outShieldDesign != null) {
                name.append("(");
                name.append(outShieldDesign.getName());
                name.append(")");
            }
        } else {
            name.append("нет");
        }
        return name.toString();
    }

}
