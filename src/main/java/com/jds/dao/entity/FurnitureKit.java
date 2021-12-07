package com.jds.dao.entity;

import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.SerializingFields;
import com.jds.model.enumModels.PeepholePosition;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "FurnitureKit")
public class FurnitureKit implements SerializingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "topLock")
    private DoorFurniture topLock;

    @ManyToOne()
    @JoinColumn(name = "topinternaLockDecoration")
    private DoorFurniture topInLockDecor;

    @ManyToOne()
    @JoinColumn(name = "topouterLockDecoration")
    private DoorFurniture topOutLockDecor;

    @ManyToOne()
    @JoinColumn(name = "toplockCylinder")
    private DoorFurniture topLockCylinder;

    @ManyToOne()
    @JoinColumn(name = "lowerLock")
    private DoorFurniture lowerLock;

    @ManyToOne()
    @JoinColumn(name = "lowerinternaLockDecoration")
    private DoorFurniture lowerInLockDecor;

    @ManyToOne()
    @JoinColumn(name = "lowerouterLockDecoration")
    private DoorFurniture lowerOutLockDecor;

    @ManyToOne()
    @JoinColumn(name = "lowerlockCylinder")
    private DoorFurniture lowerLockCylinder;

    @ManyToOne()
    @JoinColumn(name = "handle")
    private DoorFurniture handle;

    @ManyToOne()
    @JoinColumn(name = "closer")
    private DoorFurniture closer;

    @ManyToOne()
    @JoinColumn(name = "endDoorLock")
    private DoorFurniture endDoorLock;

    @ManyToOne()
    @JoinColumn(name = "nightLock")
    private DoorFurniture nightLock;

    @ManyToOne()
    @JoinColumn(name = "peephole")
    private DoorFurniture peephole;

    @Column(name = "peephole_position")
    @Enumerated(EnumType.STRING)
    private PeepholePosition peepholePosition = PeepholePosition.CENTER;

    @Column(name = "amplifierCloser")
    private int amplifierCloser;

    @OneToOne(mappedBy = "furnitureKit", fetch = FetchType.LAZY)
    private DoorEntity door;

    public boolean exists() {

        if ((topLock != null) || (lowerLock != null) || (handle != null) || (closer != null)) {
            return true;
        }
        return false;
    }

    public static FurnitureKit instanceKit(@NonNull RestrictionOfSelectionFields template) {
        FurnitureKit kit = new FurnitureKit();
        kit.setTopLock(convertLimToShortFurniture(template.getTopLock()));
        kit.setLowerLock(convertLimToShortFurniture(template.getLowerLock()));
        kit.setHandle(convertLimToShortFurniture(template.getHandle()));
        return kit;
    }

    public static FurnitureKit instanceKit(@NonNull AvailableFieldsForSelection AvailableFields) {
        FurnitureKit kit = new FurnitureKit();
        kit.setTopLock(getFirst(AvailableFields.getTopLock()));
        kit.setTopOutLockDecor(getFirst(AvailableFields.getTopOutLockDecor()));
        kit.setTopInLockDecor(getFirst(AvailableFields.getTopInLockDecor()));

        kit.setLowerLock(getFirst(AvailableFields.getLowerLock()));
        kit.setLowerOutLockDecor(getFirst(AvailableFields.getLowerOutLockDecor()));
        kit.setLowerInLockDecor(getFirst(AvailableFields.getLowerInLockDecor()));

        if (kit.isTopCylinderLock()) {
            kit.setTopLockCylinder(getFirst(AvailableFields.getLockCylinder()));
        }

        if (kit.isLowerCylinderLock()) {
            kit.setLowerLockCylinder(getFirst(AvailableFields.getLockCylinder()));
        }

        kit.setHandle(getFirst(AvailableFields.getHandle()));
        kit.setPeephole(getFirst(AvailableFields.getPeephole()));
        kit.setPeepholePosition(
                toPeepholePositionEnum(getFirst(AvailableFields.getPeepholePosition())));
        kit.setCloser(getFirst(AvailableFields.getCloser()));
        kit.setNightLock(getFirst(AvailableFields.getNightLock()));

        return kit;
    }

    private static DoorFurniture getFirst(List<DoorFurniture> furnitureList) {
        if (furnitureList != null && furnitureList.size() > 0) {
            return furnitureList.get(0);
        }
        return null;
    }

    private static PeepholePosition toPeepholePositionEnum(DoorFurniture doorFurniture) {
        if (doorFurniture == null) {
            return PeepholePosition.CENTER;
        }
        return PeepholePosition.valueOf(doorFurniture.getName());
    }


    public static DoorFurniture convertLimToShortFurniture(@NonNull List<LimitationDoor> listLim) {

        List<LimitationDoor> defList = listLim.stream()
                .filter(lim -> lim.isDefault())
                .collect(Collectors.toList());

        if (defList.size() == 1) {

            return new DoorFurniture(defList.get(0));
        }

        return null;
    }

    public FurnitureKit clearNonSerializingFields() {

        door = null;

        if (topLock != null) {
            topLock.setNuulLazyFild();
        }
        if (topInLockDecor != null) {
            topInLockDecor.setNuulLazyFild();
        }
        if (topOutLockDecor != null) {
            topOutLockDecor.setNuulLazyFild();
        }

        if (topLockCylinder != null) {
            topLockCylinder.setNuulLazyFild();
        }


        if (lowerLock != null) {
            lowerLock.setNuulLazyFild();
        }

        if (lowerInLockDecor != null) {
            lowerInLockDecor.setNuulLazyFild();
        }
        if (lowerOutLockDecor != null) {
            lowerOutLockDecor.setNuulLazyFild();
        }

        if (lowerLockCylinder != null) {
            lowerLockCylinder.setNuulLazyFild();
        }

        if (handle != null) {
            handle.setNuulLazyFild();
        }
        if (closer != null) {
            closer.setNuulLazyFild();
        }
        if (endDoorLock != null) {
            endDoorLock.setNuulLazyFild();
        }
        if (peephole != null) {
            peephole.setNuulLazyFild();
        }
        if (nightLock != null) {
            nightLock.setNuulLazyFild();
        }
        return this;
    }

    public boolean isContentFurniture(int id) {

        if (topLock != null) {
            if (topLock.getId() == id) {
                return true;
            }
        }
        if (topInLockDecor != null) {
            if (topInLockDecor.getId() == id) {
                return true;
            }
        }
        if (topOutLockDecor != null) {
            if (topOutLockDecor.getId() == id) {
                return true;
            }
        }
        if (topLockCylinder != null) {
            if (topLockCylinder.getId() == id) {
                return true;
            }
        }
        if (lowerLock != null) {
            if (lowerLock.getId() == id) {
                return true;
            }
        }
        if (lowerInLockDecor != null) {
            if (lowerInLockDecor.getId() == id) {
                return true;
            }
        }
        if (lowerOutLockDecor != null) {
            if (lowerOutLockDecor.getId() == id) {
                return true;
            }
        }
        if (lowerLockCylinder != null) {
            if (lowerLockCylinder.getId() == id) {
                return true;
            }
        }
        if (handle != null) {
            if (handle.getId() == id) {
                return true;
            }
        }
        if (closer != null) {
            if (closer.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public int getFurnitureCost(List<LimitationDoor> furnitureKit, int furnId) {

        LimitationDoor lim = furnitureKit.stream()
                .filter((p) -> p.getItemId() == furnId)
                .findFirst().orElse(null);

        if (lim != null) {
            return lim.getCost();
        } else return 0;
    }

    public boolean isTopCylinderLock() {
        if (topLock != null) {
            return topLock.getItCylinderLock() == 1;
        }
        return false;
    }

    public boolean isLowerCylinderLock() {
        if (lowerLock != null) {
            return lowerLock.getItCylinderLock() == 1;
        }
        return false;
    }
}
