package com.jds.model.ui;

public class SellerUiBuilder implements UiBuilder {
    @Override
    public MainSidePanel mainSidePanel() {
        MainSidePanel SidePanel = new MainSidePanel();

        PanelGroup mainGroup = PanelGroup.instance(SidePanelGroupEnum.MAIN);
        mainGroup.add(new PanelItem("Заказы", "/pages/orders", ""));
        SidePanel.add(mainGroup);

        PanelGroup otherGroup = PanelGroup.instance(SidePanelGroupEnum.SETTING);
        otherGroup.add(new PanelItem("Мои настройки", "/usersetting", ""));
        otherGroup.add(new PanelItem("Выйти", "/logout", ""));
        SidePanel.add(otherGroup);

        return SidePanel;
    }
}
