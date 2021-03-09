package com.jds.model.ui;

public class AdminUiBuilder implements UiBuilder {

    @Override
    public MainSidePanel mainSidePanel() {

        MainSidePanel SidePanel = new MainSidePanel();

        PanelGroup mainGroup = PanelGroup.instance(SidePanelGroupEnum.MAIN);
        mainGroup.add(new PanelItem("Заказы", "/pages/orders", ""));
        SidePanel.add(mainGroup);

        PanelGroup settingGroup = PanelGroup.instance(SidePanelGroupEnum.SETTING);
        settingGroup.add(new PanelItem("Шаблоны", "/templates/page-list", ""));
        settingGroup.add(new PanelItem("Спецификации", "/pages/specifications", ""));
        settingGroup.add(new PanelItem("Классы", "/doorclasslist", ""));
        settingGroup.add(new PanelItem("Модели", "/pages/doortype", ""));
        settingGroup.add(new PanelItem("Материалы", "/pages/materials", ""));
        settingGroup.add(new PanelItem("Фурнитура", "/furniture", ""));
        settingGroup.add(new PanelItem("Металл", "/metal", ""));
        settingGroup.add(new PanelItem("Цвета", "/color", ""));
        SidePanel.add(settingGroup);

        PanelGroup otherGroup = PanelGroup.instance(SidePanelGroupEnum.SETTING);
        otherGroup.add(new PanelItem("Пользователи", "/users", ""));
        otherGroup.add(new PanelItem("Мои настройки", "/usersetting", ""));
        otherGroup.add(new PanelItem("Выйти", "/logout", ""));
        SidePanel.add(otherGroup);

        return SidePanel;
    }
}
