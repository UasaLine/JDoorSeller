package com.jds.model.enumModels;

public enum TypeOfFurniture {
    TOP_LOCK("images/findings/preview/lock/", "images/findings/sketch/lock/"),
    LOWER_LOCK("images/findings/preview/lock/", "images/findings/sketch/lock/"),
    HANDLE("images/findings/preview/handle/", "images/findings/sketch/handle/"),
    CLOSER("images/findings/preview/closer/", "images/findings/sketch/closer/"),
    PEEPHOLE("images/findings/preview/peephole/", "images/findings/sketch/peephole/"),
    NIGHT_LOCK("images/findings/preview/night-lock/", "images/findings/sketch/night-lock/"),
    THRESHOLD,
    END_DOOR_LOCK,
    LOCK_CYLINDER("images/findings/preview/cylinder/", "images/findings/sketch/cylinder/"),
    ARMOR_GLASS_PELLICLE,
    GLASS_PELLICLE,
    TYPE_GLASS,
    LOCK_DECORATION,
    TT_TURNER,
    DOOR_CLASS,
    TOP_IN_LOCK_DECOR("images/findings/preview/lock-decor/", "images/findings/sketch/lock-decor/"),
    TOP_OUT_LOCK_DECOR("images/findings/preview/lock-decor/", "images/findings/sketch/lock-decor/"),
    LOWER_IN_LOCK_DECOR("images/findings/preview/lock-decor/", "images/findings/sketch/lock-decor/"),
    LOWER_OUT_LOCK_DECOR("images/findings/preview/lock-decor/", "images/findings/sketch/lock-decor/");

    String picPath;
    String sketchPath;

    TypeOfFurniture(String picPath, String sketchPath) {
        this.picPath = picPath;
        this.sketchPath = sketchPath;
    }

    TypeOfFurniture() {
    }

    public String getPicPath() {
        return picPath;
    }

    public String getSketchPath() {
        return sketchPath;
    }
}
