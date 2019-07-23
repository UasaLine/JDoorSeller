package com.jds.model.cutting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HorizontalCut {

List<VerticalСut> verticalСutList;

    public HorizontalCut() {
    }

    public HorizontalCut(VerticalСut verticalСutList) {
        this.verticalСutList = new LinkedList<>();
        this.verticalСutList.add(verticalСutList);
    }

    public List<VerticalСut> getVerticalСutList() {
        return verticalСutList;
    }

    public void setVerticalСutList(List<VerticalСut> verticalСutList) {
        this.verticalСutList = verticalСutList;
    }
}
