package com.jds.entity;

import com.jds.entity.DoorEntity;
import com.jds.entity.DoorFurniture;

import javax.persistence.*;


@Entity
@Table(name = "FurnitureKit")
public class FurnitureKit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "topLock")
    private DoorFurniture topLock;

    @ManyToOne()
    @JoinColumn(name = "topinternaLockDecoration")
    private DoorFurniture topinternaLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "topouterLockDecoration")
    private DoorFurniture topouterLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "toplockCylinder")
    private DoorFurniture toplockCylinder;

    @ManyToOne()
    @JoinColumn(name = "lowerLock")
    private DoorFurniture lowerLock;

    @ManyToOne()
    @JoinColumn(name = "lowerinternaLockDecoration")
    private DoorFurniture lowerinternaLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "lowerouterLockDecoration")
    private DoorFurniture lowerouterLockDecoration;

    @ManyToOne()
    @JoinColumn(name = "lowerlockCylinder")
    private DoorFurniture lowerlockCylinder;

    @ManyToOne()
    @JoinColumn(name = "handle")
    private DoorFurniture handle;

    @ManyToOne()
    @JoinColumn(name = "closer")
    private DoorFurniture closer;

    @ManyToOne()
    @JoinColumn(name = "endDoorLock")
    private DoorFurniture endDoorLock;

    @Column(name = "nightLock")
    private int nightLock;

    @Column(name = "peephole")
    private int peephole;

    @Column(name = "amplifierCloser")
    private int amplifierCloser;

    @OneToOne(mappedBy = "furnitureKit",fetch = FetchType.LAZY)
    private DoorEntity door;


    public boolean exists(){

        if ((topLock!=null)||(lowerLock!=null)||(handle!=null)||(closer!=null)){
            return true;
        }
        return false;
    }

    public FurnitureKit clearNonSerializingFields(){

        door = null;

        if(topLock!=null){
            topLock.setNuulLazyFild();
        }
        if(topinternaLockDecoration!=null){
            topinternaLockDecoration.setNuulLazyFild();
        }
        if(topouterLockDecoration!=null){
            topouterLockDecoration.setNuulLazyFild();
        }

        if(toplockCylinder!=null){
            toplockCylinder.setNuulLazyFild();
        }



        if(lowerLock!=null){
            lowerLock.setNuulLazyFild();
        }

        if(lowerinternaLockDecoration!=null){
            lowerinternaLockDecoration.setNuulLazyFild();
        }
        if(lowerouterLockDecoration!=null){
            lowerouterLockDecoration.setNuulLazyFild();
        }

        if(lowerlockCylinder!=null){
            lowerlockCylinder.setNuulLazyFild();
        }

        if(handle!=null){
            handle.setNuulLazyFild();
        }
        if(closer!=null){
            closer.setNuulLazyFild();
        }
        if(endDoorLock!=null){
            endDoorLock.setNuulLazyFild();
        }
        return this;
    }

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

    public int getNightLock() {
        return nightLock;
    }

    public void setNightLock(int nightLock) {
        this.nightLock = nightLock;
    }

    public int getPeephole() {
        return peephole;
    }

    public void setPeephole(int peephole) {
        this.peephole = peephole;
    }

    public int getAmplifierCloser() {
        return amplifierCloser;
    }

    public void setAmplifierCloser(int amplifierCloser) {
        this.amplifierCloser = amplifierCloser;
    }

    public DoorFurniture getEndDoorLock() {
        return endDoorLock;
    }

    public void setEndDoorLock(DoorFurniture endDoorLock) {
        this.endDoorLock = endDoorLock;
    }

    public DoorEntity getDoor() {
        return door;
    }

    public void setDoor(DoorEntity door) {
        this.door = door;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
