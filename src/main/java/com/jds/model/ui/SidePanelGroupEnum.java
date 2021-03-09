package com.jds.model.ui;

public enum SidePanelGroupEnum {
    MAIN("Основное:"),
    SETTING("Настройки:"),
    OTHER("Прочее:");

    private final String name;

    SidePanelGroupEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
