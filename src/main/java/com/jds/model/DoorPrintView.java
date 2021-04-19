package com.jds.model;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DoorPrintView {

    private String orderNumber;
    private String customer;
    private String orderDate;

    private DoorEntity door;

    private List<PrintViewParam> sizes;
    private List<PrintViewParam> appearance;
    private List<PrintViewParam> internalView;
    private List<PrintViewParam> furniture;

    public DoorPrintView() {
    }

    public DoorPrintView(DoorEntity door, DoorOrder order) {

        orderNumber = "Приложение к заказу №: " + order.getOrderId() + " /1";
        customer = "Покупатель: " + order.getPartner();
        orderDate = "Дата заказа: " + order.getData();

        this.door = door;

        sizes = new ArrayList<>();
        sizes.add(new PrintViewParam("Толщина металла:", String.valueOf(door.getMetal())));
        sizes.add(new PrintViewParam("Ширина:", String.valueOf(door.getWidthDoor())));
        sizes.add(new PrintViewParam("Высота:", String.valueOf(door.getHeightDoor())));
        sizes.add(new PrintViewParam("Глубина:", String.valueOf(door.getDeepnessDoor())));
        sizes.add(new PrintViewParam("Толщина полотна:", String.valueOf(door.getThicknessDoorLeaf())));
        sizes.add(new PrintViewParam("Открывание:", String.valueOf(door.getSideDoorOpen())));
        sizes.add(new PrintViewParam("Внутреннее открывание:", String.valueOf(door.getInnerOpen())));
        //sizes.add(new ParamApp("Ширина активной створки:", String.valueOf(door.getActiveDoorLeafWidth())));
        //sizes.add(new ParamApp("Высота фрамуги:", String.valueOf(door.getDoorFanlightHeight())));

        appearance = new ArrayList<>();
        appearance.add(new PrintViewParam("Цвет двери:", String.valueOf(door.getDoorColor())));
        appearance.add(new PrintViewParam("Дек.накладка:", door.getOutShieldName()));
        //appearance.add(new ParamApp("Доп.петля:", (door.getAdditionallyHingeMain() == 1 ? "да" : "нет")));
        appearance.add(new PrintViewParam("Стекло:", (door.getDoorGlass() != null) ? "Да" : "Нет"));

        internalView = new ArrayList<>();
        internalView.add(new PrintViewParam("Порог:", door.doorstepView()));
        //internalView.add(new PrintViewParam("Наличник:", (door.getTopDoorTrim() == 1) ? door.getTrimName() : "нет"));

        if (door.getShieldKit() != null) {
            internalView.add(new PrintViewParam("Дек.накладка:", door.getShieldKit().getShieldName()));
        }

        furniture = new ArrayList<>();
        if (door.getFurnitureKit() != null) {
            if (door.getFurnitureKit().getTopLock() != null) {
                furniture.add(new PrintViewParam("Верхний замок:", String.valueOf(door.getFurnitureKit().getTopLock().getName())));
            }
            if (door.getFurnitureKit().getTopInLockDecor() != null) {
                furniture.add(new PrintViewParam("Внутренняя накладка", String.valueOf(door.getFurnitureKit().getTopInLockDecor().getName())));
            }
            if (door.getFurnitureKit().getTopOutLockDecor() != null) {
                furniture.add(new PrintViewParam("Внешняя накладка:", String.valueOf(door.getFurnitureKit().getTopOutLockDecor().getName())));
            }
            if (door.getFurnitureKit().getTopLockCylinder() != null) {
                furniture.add(new PrintViewParam("Цилиндр:", String.valueOf(door.getFurnitureKit().getTopLockCylinder().getName())));
            }
            if (door.getFurnitureKit().getLowerLock() != null) {
                furniture.add(new PrintViewParam("Нижний замок:", String.valueOf(door.getFurnitureKit().getLowerLock().getName())));
            }
            if (door.getFurnitureKit().getLowerInLockDecor() != null) {
                furniture.add(new PrintViewParam("Внутренняя накладка:", String.valueOf(door.getFurnitureKit().getLowerInLockDecor().getName())));
            }
            if (door.getFurnitureKit().getLowerOutLockDecor() != null) {
                furniture.add(new PrintViewParam("Внешняя накладка:", String.valueOf(door.getFurnitureKit().getLowerOutLockDecor().getName())));
            }
            if (door.getFurnitureKit().getLowerLockCylinder() != null) {
                furniture.add(new PrintViewParam("Цилиндр:", String.valueOf(door.getFurnitureKit().getLowerLockCylinder().getName())));
            }

            if (door.getFurnitureKit().getHandle() != null) {
                furniture.add(new PrintViewParam("Ручка:", String.valueOf(door.getFurnitureKit().getHandle().getName())));
            }
            if (door.getFurnitureKit().getCloser() != null) {
                furniture.add(new PrintViewParam("Задвижка:", String.valueOf(door.getFurnitureKit().getCloser().getName())));
            }
            if (door.getFurnitureKit().getEndDoorLock() != null) {
                furniture.add(new PrintViewParam("Торцевой дверной замок:", String.valueOf(door.getFurnitureKit().getEndDoorLock().getName())));
            }
            if (door.getFurnitureKit().getNightLock() != null) {
                furniture.add(new PrintViewParam("Ночная задвижка:", String.valueOf(door.getFurnitureKit().getNightLock().getName())));
            }
            if (door.getFurnitureKit().getPeephole() != null) {
                furniture.add(new PrintViewParam("Дверной глазок:", String.valueOf(door.getFurnitureKit().getPeephole().getName())));
            }
            if (door.getFurnitureKit().getAmplifierCloser() != 0) {
                furniture.add(new PrintViewParam("Усилитель:", String.valueOf(door.getFurnitureKit().getAmplifierCloser())));
            }
        }
    }

}
