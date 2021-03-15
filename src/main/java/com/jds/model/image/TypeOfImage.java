package com.jds.model.image;

public enum TypeOfImage {
    DOOR_COLOR("images/Door/AColor1/", ""),
    DOOR_DESIGN("images/Door/design/", "images/Door/design-mask/"),
    SHIELD_COLOR("images/shield sketch/", ""),
    SHIELD_DESIGN("images/shield sketch/design/", ""),
    SHIELD_GLASS("images/Door/shieldGlass/", "");

    private final String path;
    private final String maskPath;

    TypeOfImage(String path, String maskPath) {
        this.path = path;
        this.maskPath = maskPath;
    }

    public String getPath() {
        return path;
    }

    public String getMaskPath() {
        return maskPath;
    }
}
