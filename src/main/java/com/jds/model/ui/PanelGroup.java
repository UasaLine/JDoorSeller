package com.jds.model.ui;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PanelGroup {
    private String name;
    private List<PanelItem> items = new ArrayList<>();

    private PanelGroup() {
    }

    public static PanelGroup instance(SidePanelGroupEnum panelGroup) {

        PanelGroup group = new PanelGroup();
        group.name = panelGroup.getName();

        return group;
    }

    public void add(PanelItem item) {
        items.add(item);
    }

    private static PanelGroup createOtherGroup(@NonNull PanelGroup group) {

        group.add(PanelItem.builder()
                .name("Пользователи")
                .link("/users")
                .icon("")
                .build());

        group.add(PanelItem.builder()
                .name("Мои настройки")
                .link("/usersetting")
                .icon("")
                .build());

        group.add(PanelItem.builder()
                .name("Выйти")
                .link("/logout")
                .icon("")
                .build());

        return group;
    }

}
