package com.jds.service;

import com.jds.dao.OrderDAO;
import com.jds.entity.DoorEntity;
import com.jds.entity.DoorsОrder;
import com.jds.entity.UserEntity;
import com.jds.model.PrintAppToTheOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


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

        for (DoorsОrder order : orders) {
            clearNonSerializingFields(order);
        }
        return orders;
    }

    public DoorsОrder getOrder(String id) throws Exception{

        int intId = Integer.parseInt(id);

        if (intId == 0) {
            return new DoorsОrder();
        }
        DoorsОrder order =  dAO.getOrder(intId);
        order.setSeller(null);
        return clearNonSerializingFields(order);
    }

    public DoorsОrder saveOrder(DoorsОrder order) {

        order.setSeller(userService.getCurrentUser());
        order = dAO.saveOrder(order.calculateTotal());

        return order;
    }

    public String deleteOrder(String orderId) {
        DoorsОrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        return String.valueOf(order.getId());
    }

    public static DoorsОrder clearNonSerializingFields(DoorsОrder order) {

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

}
