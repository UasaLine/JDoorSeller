package com.jds.model.enumModels;

public enum SideDoorOpen {
    LEFT("ЛЕВОЕ"),
    RIGHT("ПРАВОЕ");

    private String ru;

    SideDoorOpen(String ru) {
        this.ru = ru;
    }

    public String ru() {
        return ru;
    }
}
