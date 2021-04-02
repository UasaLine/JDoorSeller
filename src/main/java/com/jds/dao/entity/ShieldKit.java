package com.jds.dao.entity;

import com.jds.model.AvailableFieldsForSelection;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ShieldKit")
public class ShieldKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "shieldColor")
    private ImageEntity shieldColor;

    @ManyToOne()
    @JoinColumn(name = "shieldDesign")
    private ImageEntity shieldDesign;

    @ManyToOne()
    @JoinColumn(name = "shield_over_color")
    private ImageEntity shieldOverColor;

    @ManyToOne()
    @JoinColumn(name = "shieldGlass")
    private ImageEntity shieldGlass;

    @OneToOne(mappedBy = "shieldKit", fetch = FetchType.LAZY)
    private DoorEntity door;

    public static ShieldKit instanceKit(@NonNull AvailableFieldsForSelection AvailableFields) {
        ShieldKit kit = new ShieldKit();
        kit.setShieldColor(getFirst(AvailableFields.getShieldColor()));
        kit.setShieldDesign(getFirst(AvailableFields.getShieldDesign()));
        if (kit.getShieldDesign() != null && kit.getShieldDesign().getContainsGlass() == 1) {
            ImageEntity glassDefault = AvailableFields.getShieldGlass().stream()
                    .filter((img) -> img.getContainsDesign() == kit.getShieldDesign().getId())
                    .findFirst().orElse(null);
            kit.setShieldGlass(glassDefault);
        }

        return kit;
    }

    private static ImageEntity getFirst(List<ImageEntity> list) {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean exists() {
        if (shieldColor == null && shieldDesign == null) {
            return false;
        }
        return true;
    }

    public ShieldKit clearNonSerializingFields() {
        door = null;
        if (shieldColor != null) {
            shieldColor.clearNonSerializingFields();
        }
        if (shieldDesign != null) {
            shieldDesign.clearNonSerializingFields();
        }
        if (shieldGlass != null) {
            shieldGlass.clearNonSerializingFields();
        }
        if (shieldOverColor != null) {
            shieldOverColor.clearNonSerializingFields();
        }
        return this;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        if (shieldColor != null) {
            builder.append(shieldColor.getName());
        }
        if (shieldOverColor != null) {
            builder.append(" ( ");
            builder.append(shieldOverColor.getName());
            builder.append(" ) ");
        }
        if (shieldDesign != null) {
            builder.append(" ( ");
            builder.append(shieldDesign.getName());
            builder.append(" ) ");
        }

        if (shieldGlass != null) {
            builder.append(shieldGlass.getName());
        }

        return builder.toString();
    }

    public String getShieldName() {
        return toString();
    }

}
