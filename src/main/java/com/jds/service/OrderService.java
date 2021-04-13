package com.jds.service;

import com.jds.dao.repository.OrderDAO;
import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;

import com.jds.model.DoorPrintView;
import com.jds.model.backResponse.ResponseList;
import com.jds.model.backResponse.ResponseModel;
import com.jds.model.enumClasses.OrderStatus;
import com.jds.model.orders.OrderParamsDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    public ResponseList<DoorOrder> getOrders(OrderParamsDto params) {

        UserEntity user = userService.getCurrentUser();
        int sellerId = 0;
        if (!user.isAdmin()) {
            sellerId = user.getId();
        }
        //@TODO add filter to getOrders
        List<DoorOrder> list = dAO.getOrders(params.getSorter(), params.getLimit(), params.getOffset(), sellerId);

        long total = dAO.orderCountRows(params.getSorter(), params.getLimit(), params.getOffset(), sellerId);

        return new ResponseList<>(true, "ok", list, total);
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

        if (OrderStatus.CALC == baseOrderStatus
                || OrderStatus.READY == baseOrderStatus) {

            order.setSeller(userService.getCurrentUser());
            return saveAndCalc(order);
        } else {
            return null;
        }
    }

    public ResponseModel<DoorOrder> checkAccessAndSave(@NonNull DoorOrder order) {
        if ("admin".equals(userService.getCurrentUser().getUsername())) {
            return new ResponseModel<>(false, "!save is not available for admin", null);
        } else {
            return new ResponseModel<>(checkStatusAndSave(order));
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


        //CALC("расчет"),
        //TO_WORK("в работу"),
        //IN_WORK("в работе"),
        //READY("готов"),
        //CLOSED("закрыт");
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

    public List<DoorPrintView> getDoorPrintViews(int orderId) {

        DoorOrder order = dAO.getOrder(orderId);
        List<DoorEntity> doors = order.getDoors();

        List<DoorPrintView> doorPrintList = new ArrayList<>();
        for (DoorEntity door : doors) {
            doorPrintList.add(door.getPrintView(order));
        }

        return doorPrintList;

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
