package com.jds.service;

import com.jds.dao.OrderDAO;
import com.jds.entity.DoorEntity;
import com.jds.entity.DoorsОrder;
import com.jds.entity.UserEntity;
import com.jds.model.PrintAppToTheOrder;
import com.jds.model.modelEnum.OrderStatus;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderDAO dAO;

    @Autowired
    private UserService userService;


    public List<DoorsОrder> getOrders() {

        UserEntity user = userService.getCurrentUser();
        List<DoorsОrder> orders;

        if (user.isAdmin()){
            orders = dAO.getOrders();
        }
        else {
            orders = dAO.getOrdersByUser(user);
        }

        orders.stream()
                .peek((order)->clearNonSerializingFields(order))
                .collect(Collectors.toList());

        return orders;
    }

    public List<DoorsОrder> getOrders(OrderStatus status) {

        UserEntity user = userService.getCurrentUser();
        List<DoorsОrder> orders;


        if (user.isAdmin() && status!=null){
            orders = dAO.getOrdersByStatus(status.name());
        }
        else {
            orders = dAO.getOrdersByUser(user);
        }

        orders.stream()
                .peek((order)->clearNonSerializingFields(order))
                .collect(Collectors.toList());

        return orders;
    }

    public List<DoorsОrder> getOrders(@NonNull String userId) {

        UserEntity user = userService.getUser(userId);
        List<DoorsОrder> orders;

        if (user!=null){
            orders = dAO.getOrdersByUser(user);
            orders.stream()
                    .peek((order)->clearNonSerializingFields(order))
                    .collect(Collectors.toList());
        }
        else {
            orders = new ArrayList<>();
        }

        return orders;
    }


    public DoorsОrder getOrder(String id) throws Exception{

        int intId = Integer.parseInt(id);

        if (intId == 0) {
            return new DoorsОrder();
        }
        DoorsОrder order =  dAO.getOrder(intId);

        return clearNonSerializingFields(order);
    }

    public DoorsОrder setCurrentUserAndSaveOrder(DoorsОrder order){
        order.setSeller(userService.getCurrentUser());
        return saveOrder(order);
    }

    private DoorsОrder saveOrder(DoorsОrder order) {

        order = dAO.saveOrder(order.calculateTotal());
        return order;

    }

    public String deleteOrder(String orderId) {
        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        return String.valueOf(order.getId());
    }

    public static DoorsОrder clearNonSerializingFields(DoorsОrder order) {

        order.getSeller().setOrders(null);


        List<DoorEntity> doors = order.getDoors();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
        }
        return order;
    }

    public List<PrintAppToTheOrder> getPrintAppList(String orderId) {

        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        List<DoorEntity> doors = order.getDoors();

        List<PrintAppToTheOrder> printAppList = new ArrayList<>();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
            printAppList.add(new PrintAppToTheOrder(door, order));
        }

        return printAppList;

    }

    public void setStatusAndSaveOrder(@NonNull int id, @NonNull String status){

       OrderStatus orderStatus = validationOrderStatus(status);
       if (orderStatus!=null){
           DoorsОrder order = dAO.getOrder(id);
           order.setStatus(orderStatus);
           saveOrder(order);
       }
    }

    private OrderStatus validationOrderStatus(@NonNull String status){
        if ("IN_WORK".equals(status)){
            return OrderStatus.IN_WORK;
        }
        else if ("READY".equals(status)){
            return OrderStatus.READY;
        }
        return null;
    }

}
