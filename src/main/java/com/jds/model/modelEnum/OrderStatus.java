package com.jds.model.modelEnum;

public enum OrderStatus {

    CALC("расчет"),
    TO_WORK("в работу"),
    IN_WORK("в работе"),
    READY("готов"),
    CLOSED("закрыт");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
