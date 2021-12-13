package com.jds.model.enumModels;

public enum YesOrNo {
    YES("Да"), NO("Нет");

    private String name;

    YesOrNo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
