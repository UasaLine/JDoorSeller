package com.jds.model.image;

public enum TypeOfImage {
    DOOR_COLOR("images/Door/AColor1/"),
    DOOR_DESIGN("images/Door/design/"),
    SHIELD_COLOR("images/shield sketch/"),
    SHIELD_DESIGN("images/shield sketch/design/"),
    SHIELD_GLASS("images/Door/shieldGlass/");

    private final String path;

    TypeOfImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
