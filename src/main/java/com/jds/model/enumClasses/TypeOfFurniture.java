package com.jds.model.enumClasses;

public enum TypeOfFurniture {
    TOP_LOCK("images/findings/preview/lock/","images/findings/sketch/lock/"),
    LOWER_LOCK("images/findings/preview/lock/","images/findings/sketch/lock/"),
    HANDLE("images/findings/preview/handle/","images/findings/sketch/handle/"),
    CLOSER,
    PEEPHOLE("images/findings/preview/peephole/","images/findings/sketch/peephole/"),
    NIGHT_LOCK,
    THRESHOLD,
    END_DOOR_LOCK,	
	LOCK_CYLINDER,
	ARMOR_GLASS_PELLICLE,
	GLASS_PELLICLE,
	TYPE_GLASS,
	LOCK_DECORATION,
	TT_TURNER,
	DOOR_CLASS,
    TOP_IN_LOCK_DECOR,
    TOP_OUT_LOCK_DECOR,
    LOWER_IN_LOCK_DECOR,
    LOWER_OUT_LOCK_DECOR;

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
