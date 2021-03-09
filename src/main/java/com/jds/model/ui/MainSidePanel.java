package com.jds.model.ui;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainSidePanel {
    private List<PanelGroup> groups = new ArrayList<>();

    public void add(PanelGroup group) {
        groups.add(group);
    }
}
