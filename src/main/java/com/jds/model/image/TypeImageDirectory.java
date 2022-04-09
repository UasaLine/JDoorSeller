package com.jds.model.image;

public enum TypeImageDirectory {
    PREVIEW("preview/"),
    SKETCH("sketch/"),
    MASK("mask/");

    String prefix;

    TypeImageDirectory(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
