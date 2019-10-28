package com.jds.model;

import com.jds.entity.DoorEntity;
import com.jds.entity.DoorsОrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrintAppToTheOrder {

    private String orderNumber;
    private String customer;
    private String orderDate;

    private DoorEntity door;

    private List<ParamApp> sizes;
    private List<ParamApp> appearance;
    private List<ParamApp> internalView;
    private List<ParamApp> furniture;

    public PrintAppToTheOrder() {
    }

    public PrintAppToTheOrder(DoorEntity door, DoorsОrder order) {

        orderNumber = "Приложение к заказу №: " + order.getId() + " /1";
        customer = "Клиент: " + order.getPartner();
        orderDate = "Дата заказа: " + order.getData();

        this.door = door;

        sizes = new ArrayList<>();
        sizes.add(new ParamApp("Толщина металла:", String.valueOf(door.getMetal())));
        sizes.add(new ParamApp("Ширина:", String.valueOf(door.getWidthDoor())));
        sizes.add(new ParamApp("Высота:", String.valueOf(door.getHeightDoor())));
        sizes.add(new ParamApp("Глубина:", String.valueOf(door.getDeepnessDoor())));
        sizes.add(new ParamApp("Толщина полотна:", String.valueOf(door.getThicknessDoorLeaf())));
        sizes.add(new ParamApp("Открывание:", String.valueOf(door.getSideDoorOpen())));
        sizes.add(new ParamApp("Внутреннее открывание:", String.valueOf(door.getInnerDoorOpen())));
        sizes.add(new ParamApp("Ширина активной створки:", String.valueOf(door.getActiveDoorLeafWidth())));
        sizes.add(new ParamApp("Высота фрамуги:", String.valueOf(door.getDoorFanlightHeight())));

        appearance = new ArrayList<>();
        appearance.add(new ParamApp("Цвет двери:", String.valueOf(door.getDoorColor())));
        appearance.add(new ParamApp("Дек.накладка:", String.valueOf("нет")));
        appearance.add(new ParamApp("Доп.петля:", String.valueOf(door.getAdditionallyHingeMain())));
        appearance.add(new ParamApp("Стекло:", String.valueOf(door.getIsDoorGlass())));

        internalView = new ArrayList<>();
        internalView.add(new ParamApp("Порог:", String.valueOf(door.getDoorstep())));
        internalView.add(new ParamApp("Наличник:", String.valueOf(door.getTopDoorTrim())));
        internalView.add(new ParamApp("Цвет накладки:", "нет"));


        furniture = new ArrayList<>();
        furniture.add(new ParamApp("Верхний замок:", String.valueOf(door.getDoorstep())));

    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public DoorEntity getDoor() {
        return door;
    }

    public void setDoor(DoorEntity door) {
        this.door = door;
    }

    public List<ParamApp> getSizes() {
        return sizes;
    }

    public void setSizes(List<ParamApp> sizes) {
        this.sizes = sizes;
    }

    public List<ParamApp> getAppearance() {
        return appearance;
    }

    public void setAppearance(List<ParamApp> appearance) {
        this.appearance = appearance;
    }

    public List<ParamApp> getInternalView() {
        return internalView;
    }

    public void setInternalView(List<ParamApp> internalView) {
        this.internalView = internalView;
    }

    public List<ParamApp> getFurniture() {
        return furniture;
    }

    public void setFurniture(List<ParamApp> furniture) {
        this.furniture = furniture;
    }
}
