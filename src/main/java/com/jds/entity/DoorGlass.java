package com.jds.entity;

import com.jds.model.modelEnum.TypeOfFurniture;

public class DoorGlass {

    private DoorFurniture typeDoorGlass;
    private DoorFurniture toning;
    private DoorFurniture armor;
    private int glassWidth;
    private int glassHeight;
    private int leftGlassPosition;
    private int bottomGlassPosition;


    public DoorGlass() {

    }

    public boolean exists(){

        if ((typeDoorGlass!=null)&&(glassWidth>0)&&(glassHeight>0)){
            return true;
        }
        return false;
    }

    public double getSpace(){

        double S =((double)glassWidth*(double) glassHeight)/1000000;

        return S;
    }

    public DoorFurniture getTypeDoorGlass() {
        return typeDoorGlass;
    }

    public int getCost(TypeOfFurniture type,double space){

        if(type==TypeOfFurniture.TYPE_GLASS){
            return (int)(typeDoorGlass.getPrice()*space);
        }
        if(type==TypeOfFurniture.ARMOR_GLASS_PELLICLE){
            return (int)(armor.getPrice()*space);
        }
        if(type==TypeOfFurniture.GLASS_PELLICLE){
            return (int)(toning.getPrice()*space);
        }

        return 0;
    }


    public void setTypeDoorGlass(DoorFurniture typeDoorGlass) {
        this.typeDoorGlass = typeDoorGlass;
    }

    public DoorFurniture getToning() {
        return toning;
    }

    public void setToning(DoorFurniture toning) {
        this.toning = toning;
    }

    public DoorFurniture getArmor() {
        return armor;
    }

    public void setArmor(DoorFurniture armor) {
        this.armor = armor;
    }

    public int getGlassWidth() {
        return glassWidth;
    }

    public void setGlassWidth(int glassWidth) {
        this.glassWidth = glassWidth;
    }

    public int getGlassHeight() {
        return glassHeight;
    }

    public void setGlassHeight(int glassHeight) {
        this.glassHeight = glassHeight;
    }

    public int getLeftGlassPosition() {
        return leftGlassPosition;
    }

    public void setLeftGlassPosition(int leftGlassPosition) {
        this.leftGlassPosition = leftGlassPosition;
    }

    public int getBottomGlassPosition() {
        return bottomGlassPosition;
    }

    public void setBottomGlassPosition(int bottomGlassPosition) {
        this.bottomGlassPosition = bottomGlassPosition;
    }
}
