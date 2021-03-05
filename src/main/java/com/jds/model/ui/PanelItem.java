package com.jds.model.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PanelItem {
    private String name;
    private String link;
    private String icon;
}
