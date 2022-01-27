package com.jds.model.enumModels;

public enum GlassPositionType {
    MAXIMUM_AREA("left indent", "right indent",
            "bottom indent", "top indent"),
    BOTTOM("width", "height", "bottom indent", "indent from the side");

    String value1Name;
    String value2Name;
    String value3Name;
    String value4Name;

    GlassPositionType(String value1Name, String value2Name, String value3Name, String value4Name) {
        this.value1Name = value1Name;
        this.value2Name = value2Name;
        this.value3Name = value3Name;
        this.value4Name = value4Name;
    }
}
