package com.jds.model;

import com.jds.entity.DoorFurniture;
import com.jds.entity.LimitationDoor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableFieldsForSelection {
    private List<DoorFurniture> topLock = new ArrayList<>();
    private List<DoorFurniture> lowerLock = new ArrayList<>();
    private List<DoorFurniture> handle = new ArrayList<>();
    private List<DoorFurniture> lockCylinder = new ArrayList<>();
    private List<DoorFurniture> topInLockDecor = new ArrayList<>();
    private List<DoorFurniture> topOutLockDecor = new ArrayList<>();
    private List<DoorFurniture> lowerInLockDecor = new ArrayList<>();
    private List<DoorFurniture> lowerOutLockDecor = new ArrayList<>();
    private List<DoorFurniture> closer = new ArrayList<>();
}
