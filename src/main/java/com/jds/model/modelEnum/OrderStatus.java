package com.jds.model.modelEnum;

import jdk.nashorn.internal.runtime.ParserException;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {

    CALC("расчет"),
    TO_WORK("в работу"),
    IN_WORK("в работе"),
    READY("готов"),
    CLOSED("закрыт");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus startStatus() {
        return CALC;
    }

    public static List<OrderStatus> statusList(OrderStatus orderStatus) {

        List<OrderStatus> list = new ArrayList<>();
        switch (orderStatus) {
            case IN_WORK:
                list.add(IN_WORK);
                break;
            case READY:
                list.add(READY);
                list.add(CLOSED);
                break;
            default:
                list.add(CALC);
                list.add(TO_WORK);
                list.add(CLOSED);
                break;
        }
        return list;
    }

    /**
     * Parses Factory Status method.
     *
     * @param status a {@code String} containing status IN_WORK, READY
     * @return the OrderStatus value .
     * @throws IllegalArgumentException if the string does not contain a
     *                                  parsable status.
     */
    public static OrderStatus parseForFactory(String status) {
        if ("IN_WORK".equals(status)) {
            return OrderStatus.IN_WORK;
        } else if ("READY".equals(status)) {
            return OrderStatus.READY;
        }
        throw new IllegalArgumentException("does not contain the correct factory status: IN_WORK, READY");
    }
}
