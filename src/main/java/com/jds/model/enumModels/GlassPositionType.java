package com.jds.model.enumModels;

import java.util.HashMap;
import java.util.Map;

public enum GlassPositionType {
    MAXIMUM_AREA("левый отступ", "правый отступ",
            "нижний отступ", "верхний отступ"),
    BOTTOM("ширина стекла", "высота стекла", "нижний отступ", "# не используется");

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

    public Map<String, String> getNamesMap() {
        Map<String, String> map = new HashMap();
        map.put("value1", value1Name);
        map.put("value2", value2Name);
        map.put("value3", value3Name);
        map.put("value4", value4Name);
        return map;
    }
}
