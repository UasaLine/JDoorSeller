package com.jds.dao.entity;

import com.jds.model.AvailableFieldsForSelection;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne(mappedBy = "shieldKit",fetch = FetchType.LAZY)
    private DoorEntity door;

    public static ShieldKit instanceKit(@NonNull AvailableFieldsForSelection AvailableFields) {
        ShieldKit kit = new ShieldKit();
        kit.setShieldColor(getFirst(AvailableFields.getShieldColor()));
        kit.setShieldDesign(getFirst(AvailableFields.getShieldDesign()));
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
        if (shieldColor!=null){
            shieldColor.clearNonSerializingFields();
        }
        if (shieldDesign!=null){
            shieldDesign.clearNonSerializingFields();
        }
        return this;
    }
}
