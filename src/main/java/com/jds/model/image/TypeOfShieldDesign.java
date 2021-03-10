package com.jds.model.image;

public enum  TypeOfShieldDesign implements TypeName {
    LUX("Lux-серии"),
    C("C-серии"),
    T("T-серии");

    private final String name;

    TypeOfShieldDesign(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
