package com.jds.model.enumModels;

public enum TypeOfFurniture {
    TOP_LOCK("Верхний замок", "lock/", true, false),
    LOWER_LOCK("Нижний замок", "lock/", true, false),
    HANDLE("Ручка", "handle/", true, false),
    CLOSER("Доводчик", "closer/", true, false),
    PEEPHOLE("Глазок", "peephole/", true, false),
    NIGHT_LOCK("Ночная задвижка", "night-lock/", true, false),
    THRESHOLD("Порог", "", true, false),
    END_DOOR_LOCK("Торцевая задвижка", "", true, false),
    LOCK_CYLINDER("Цилиндр", "cylinder/", true, false),
    LOCK_DECORATION("Торцевая задвижка", "", true, false),
    TT_TURNER("ТТ-поворотник", "", true, false),
    TOP_IN_LOCK_DECOR("Накладка замка верх.внутр.", "lock-decor/", true, false),
    TOP_OUT_LOCK_DECOR("Накладка замка верх.внеш.", "lock-decor/", true, false),
    LOWER_IN_LOCK_DECOR("Накладка замка нижн.внутр.", "lock-decor/", true, false),
    LOWER_OUT_LOCK_DECOR("Накладка замка нижн.внеш.", "lock-decor/", true, false),

    DOOR_CLASS("Стекло", "", true, true),
    ARMOR_GLASS_PELLICLE("Бронь стекла", "armor/", true, true),
    GLASS_PELLICLE("Пленка стекла", "pellicle/", true, true),
    TYPE_GLASS("Тип стекла", "type/", true, true);

    String name;
    String picPath;
    String sketchPath;
    boolean active;

    TypeOfFurniture(String name, String picPath, boolean active, boolean forGlass) {
        String previewTemplate = forGlass ? "images/glass-components/preview/" : "images/findings/preview/";
        String sketchTemplate = forGlass ? "images/glass-components/sketch/" : "images/findings/sketch/";
        this.name = name;
        this.picPath = previewTemplate + picPath;
        this.sketchPath = sketchTemplate + picPath;
        this.active = active;
    }

    TypeOfFurniture() {
    }

    public String getName() {
        return name;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getSketchPath() {
        return sketchPath;
    }
}
