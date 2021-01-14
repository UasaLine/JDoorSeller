package com.jds.service;

import com.jds.dao.repository.OrderDAO;
import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.backResponse.OrderResponse;
import com.jds.model.PrintAppToTheOrder;
import com.jds.model.enumClasses.OrderStatus;
import com.jds.model.orders.OrderParamsDto;
import com.jds.model.orders.filter.OrderFilter;
import com.jds.model.orders.sort.OrderSorter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderDAO dAO;

    @Autowired
    private UserService userService;

    @Autowired
    OrderDiscountService orderDiscountService;


    public List<DoorOrder> getOrders() {

        UserEntity user = userService.getCurrentUser();
        List<DoorOrder> orders;

        if (user.isAdmin()) {
            orders = dAO.getOrders();
        } else {
            orders = dAO.getOrdersByUser(user);
        }

        return orders;
    }

    public List<DoorOrder> getOrders(OrderParamsDto params) {
        return dAO.getOrders(params.getSorter());
    }

    public List<DoorOrder> getOrders(OrderStatus status) {

        UserEntity user = userService.getCurrentUser();
        List<DoorOrder> orders;

        if (user.isAdmin() && status != null) {
            orders = dAO.getOrdersByStatus(status.name());
        } else {
            orders = dAO.getOrdersByUser(user);
        }

        return orders;
    }

    public List<DoorOrder> getAllOrders(OrderStatus status) {
        List<DoorOrder> orders;
        orders = dAO.getOrdersByStatus(status.name());
        return orders;
    }

    public List<DoorOrder> getOrders(@NonNull String userId) {

        UserEntity user = userService.getUser(userId);
        return getOrders(user);
    }

    public List<DoorOrder> getOrders(UserEntity user) {

        List<DoorOrder> orders;

        if (user != null) {
            orders = dAO.getOrdersByUser(user);
        } else {
            orders = new ArrayList<>();
        }

        return orders;
    }

    public DoorOrder getOrder(String id) {

        int intId = Integer.parseInt(id);
        return getOrder(intId);
    }

    public DoorOrder getOrder(int id) {

        DoorOrder order;
        if (id == 0) {
            order = new DoorOrder();
        } else {
            order = dAO.getOrder(id);
        }
        order.setStatusList(OrderStatus.statusList(order.getStatus()));

        return order;
    }

    public DoorOrder checkStatusAndSave(@NonNull DoorOrder order) {
        OrderStatus baseOrderStatus = statusOrderBaseByOrderId(order.getOrderId());
        if (OrderStatus.CALC == baseOrderStatus || OrderStatus.READY == baseOrderStatus) {
            order.setSeller(userService.getCurrentUser());
            return saveAndCalc(order);
        } else {
            return null;
        }
    }

    public OrderResponse checkAccessAndSave(@NonNull DoorOrder order) {
        if ("admin".equals(userService.getCurrentUser().getUsername())) {
            return new OrderResponse(false, "!save is not available for admin");
        } else {
            return new OrderResponse(checkStatusAndSave(order));
        }
    }

    private DoorOrder saveAndCalc(@NonNull DoorOrder order) {

        order.calculateTotal(
                userService.getUserSetting(),
                orderDiscountService.getOrderDiscounts(String.valueOf(order.getOrderId())));
        return dAO.saveOrder(order);
    }

    public String deleteOrder(String orderId) {
        DoorOrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        orderDiscountService.deleteOrderDiscountByOrderId(orderId);
        return String.valueOf(order.getOrderId());
    }

    public static DoorOrder clearNonSerializingFields(DoorOrder order) {

        List<DoorEntity> doors = order.getDoors();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
        }
        return order;
    }

    public List<PrintAppToTheOrder> getPrintAppList(String orderId) {

        DoorOrder order = dAO.getOrder(Integer.parseInt(orderId));
        List<DoorEntity> doors = order.getDoors();

        List<PrintAppToTheOrder> printAppList = new ArrayList<>();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
            printAppList.add(new PrintAppToTheOrder(door, order));
        }

        return printAppList;

    }

    public boolean setStatus(@NonNull int id,
                             @NonNull OrderStatus status) {

        DoorOrder order = dAO.getOrder(id);
        order.setStatus(status);
        save(order);

        return true;
    }

    public boolean setReleaseDate(@NonNull int id,
                                  @NonNull Date releaseDate) {

        DoorOrder order = dAO.getOrder(id);
        order.setReleasDate(releaseDate);
        save(order);

        return true;
    }

    private DoorOrder save(DoorOrder order) {
        return dAO.saveOrder(order);
    }

    private OrderStatus statusOrderBaseByOrderId(int id) {
        if (id > 0) {
            DoorOrder orderInBase = dAO.getOrder(id);
            return orderInBase.getStatus();
        } else {
            return OrderStatus.CALC;
        }
    }
}
