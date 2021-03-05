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

        if (panelGroup == SidePanelGroupEnum.MAIN) {
            group = createMainGroup(group);
        } else if (panelGroup == SidePanelGroupEnum.SETTING) {
            group = createSettingGroup(group);
        } else if (panelGroup == SidePanelGroupEnum.OTHER) {
            group = createOtherGroup(group);
        }

        return group;
    }

    private void add(PanelItem item) {
        items.add(item);
    }

    private static PanelGroup createMainGroup(@NonNull PanelGroup group) {

        group.add(PanelItem.builder()
                .name("Заказы")
                .link("/pages/orders")
                .icon("")
                .build());

        return group;
    }

    private static PanelGroup createSettingGroup(@NonNull PanelGroup group) {

        group.add(PanelItem.builder().name("Шаблоны").link("/templates/page-list").icon("")
                .build());
        group.add(PanelItem.builder().name("Спецификации").link("/pages/specifications").icon("")
                .build());

        group.add(PanelItem.builder().name("Классы").link("/doorclasslist").icon("")
                .build());
        group.add(PanelItem.builder().name("Модели").link("/pages/doortype").icon("")
                .build());
        group.add(PanelItem.builder().name("Материалы").link("/pages/materials").icon("")
                .build());
        group.add(PanelItem.builder().name("Фурнитура").link("/furniture").icon("")
                .build());
        group.add(PanelItem.builder().name("Металл").link("/metal").icon("")
                .build());
        group.add(PanelItem.builder().name("Цвета").link("/color").icon("")
                .build());

        return group;
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
