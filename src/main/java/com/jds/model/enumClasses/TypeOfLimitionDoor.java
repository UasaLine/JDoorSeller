package com.jds.model.enumClasses;

public enum TypeOfLimitionDoor {
    METAL_THICKNESS("Металл"),

    WIDTH("Ширина"),
    HEIGHT("Высота"),
    WIDTH_ACTIVE_LEAF("Ширина активной створки"),
    DEPTH("Глубина"),
    LEAF_THICKNESS("Толщина металла"),

    INTERNAL_OPENING("Внутренее открывание"),

    FANLIGHT("Фрамуга"),
    HEIGHT_FANLIGHT("Высота фрамуги"),

    COLOR_DOOR("Цвет двери"),
    DESIGN_DOOR("Дизайн двери"),
    COLOR_WOOD_PANEL("Цвет деревянной панели"),

    DOORSTEP("Порог"),
    STAINLESS_STEEL_DOORSTEP("Порог из нержавейки"),

    FIRST_SEALING_LINE("Первый уплотнитель"),
    SECOND_SEALING_LINE("Второй уплотнитель"),
    THIRD_SEALING_LINE("Третий уплотнитель"),

    TOP_DOOR_TRIM("Верхний наличник"),
    LEFT_DOOR_TRIM("Левый наличник"),
    RIGHT_DOOR_TRIM("Правый наличник"),

    TOP_DOOR_TRIM_SIZE("Размер верхнего наличника"),
    LEFT_DOOR_TRIM_SIZE("Размер левого наличника"),
    RIGHT_DOOR_TRIM_SIZE("Размер правого наличника"),

    STYROFOAM("Пена"),

    TOP_LOCK("Верхний замок"),
    LOWER_LOCK("Нижний замок"),
    HANDLE("Ручка"),
    CLOSER("Довотчик"),
    PEEPHOLE("Глазок"),
    PEEPHOLE_POSITION("Позиция глазка"),
    NIGHT_LOCK("Ночная задвижка"),
    THRESHOLD("Порог"),
    END_DOOR_LOCK("Замок"),
    LOCK_CYLINDER("Цилиндр"),

    TOP_IN_LOCK_DECOR("Верхняя внутренняя накладка"),
    TOP_OUT_LOCK_DECOR("Верхняя внешняя накладка"),
    LOWER_IN_LOCK_DECOR("Нижняя внутренняя накладка"),
    LOWER_OUT_LOCK_DECOR("Нижняя внешняя накладка"),

    TYPE_GLASS("Тип Стекла"),
    TONING("Тонировка"),
    ARMOR("Броня"),

    SHIELD_COLOR("Цвет щита"),
    SHIELD_DESIGN("Дизайн щита"),
    SHIELD_GLASS("Стекло щита"),

    OUT_SHIELD_COLOR("Цвет внешнего щита"),
    OUT_SHIELD_DESIGN("Дизайн внешнего щита"),

    SIZE_COST_WIDTH("Цена ширины"),
    SIZE_COST_HEIGHT("Цена высоты");

    private String name;

    TypeOfLimitionDoor(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


}
