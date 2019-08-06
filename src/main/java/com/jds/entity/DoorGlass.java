package com.jds.entity;

public class DoorGlass {

    private String typeDoorGlass;
    private String toning;
    private String armor;
    private int glassWidth;
    private int glassHeight;
    private int leftGlassPosition;
    private int bottomGlassPosition;

    public DoorGlass() {

    }

    public boolean exists(){

        if ((typeDoorGlass !="")&&(glassWidth>0)&&(glassHeight>0)){
            return true;
        }
        return false;
    }

    public String getTypeDoorGlass() {
        return typeDoorGlass;
    }

    public void setTypeDoorGlass(String typeDoorGlass) {
        this.typeDoorGlass = typeDoorGlass;
    }

    public String getToning() {
        return toning;
    }

    public void setToning(String toning) {
        this.toning = toning;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
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
