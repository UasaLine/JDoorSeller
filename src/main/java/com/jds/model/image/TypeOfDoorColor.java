package com.jds.model.image;

public enum TypeOfDoorColor implements TypeName {
    MOIRE("Муар"),
    SILK("Шелк"),
    RAL("Рал"),
    BOUCLE("Блеск");

    private final String name;

    public String getName() {
        return name;
    }

    TypeOfDoorColor(String name) {
        this.name = name;
    }
}
