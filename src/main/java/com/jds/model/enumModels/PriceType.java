package com.jds.model.enumModels;

public enum PriceType {
    FOR_SELL(100),
    FOR_BUY(200);

    private int group;

    PriceType(int index) {
        this.group = index;
    }

    public int getGroup(){
        return group;
    }
}
