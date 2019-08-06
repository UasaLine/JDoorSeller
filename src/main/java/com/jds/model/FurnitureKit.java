package com.jds.model;

import com.jds.entity.DoorFurniture;

public class FurnitureKit {

    private DoorFurniture topLock;
    private DoorFurniture lowerinternaLockDecoration;
    private DoorFurniture lowerouterLockDecoration;
    private DoorFurniture toplockCylinder;

    private DoorFurniture lowerLock;
    private DoorFurniture topinternaLockDecoration;
    private DoorFurniture topouterLockDecoration;
    private DoorFurniture lowerlockCylinder;

    private DoorFurniture handle;
    private DoorFurniture closer;

    public DoorFurniture getCloser() {
        return closer;
    }

    public void setCloser(DoorFurniture closer) {
        this.closer = closer;
    }

    public DoorFurniture getTopLock() {
        return topLock;
    }

    public void setTopLock(DoorFurniture topLock) {
        this.topLock = topLock;
    }

    public DoorFurniture getLowerLock() {
        return lowerLock;
    }

    public void setLowerLock(DoorFurniture lowerLock) {
        this.lowerLock = lowerLock;
    }

    public DoorFurniture getHandle() {
        return handle;
    }

    public void setHandle(DoorFurniture handle) {
        this.handle = handle;
    }

    public DoorFurniture getLowerinternaLockDecoration() {
        return lowerinternaLockDecoration;
    }

    public void setLowerinternaLockDecoration(DoorFurniture lowerinternaLockDecoration) {
        this.lowerinternaLockDecoration = lowerinternaLockDecoration;
    }

    public DoorFurniture getLowerouterLockDecoration() {
        return lowerouterLockDecoration;
    }

    public void setLowerouterLockDecoration(DoorFurniture lowerouterLockDecoration) {
        this.lowerouterLockDecoration = lowerouterLockDecoration;
    }

    public DoorFurniture getTopinternaLockDecoration() {
        return topinternaLockDecoration;
    }

    public void setTopinternaLockDecoration(DoorFurniture topinternaLockDecoration) {
        this.topinternaLockDecoration = topinternaLockDecoration;
    }

    public DoorFurniture getTopouterLockDecoration() {
        return topouterLockDecoration;
    }

    public void setTopouterLockDecoration(DoorFurniture topouterLockDecoration) {
        this.topouterLockDecoration = topouterLockDecoration;
    }

    public DoorFurniture getToplockCylinder() {
        return toplockCylinder;
    }

    public void setToplockCylinder(DoorFurniture toplockCylinder) {
        this.toplockCylinder = toplockCylinder;
    }

    public DoorFurniture getLowerlockCylinder() {
        return lowerlockCylinder;
    }

    public void setLowerlockCylinder(DoorFurniture lowerlockCylinder) {
        this.lowerlockCylinder = lowerlockCylinder;
    }
}
