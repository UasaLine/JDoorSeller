package com.jds.service;

import com.jds.dao.entity.OrderDiscount;
import com.jds.dao.repository.OrderDiscountRepository;
import com.jds.model.OrderDiscounts;
import com.jds.model.ResponseAction;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDiscountService {

    @Autowired
    private OrderDiscountRepository orderDiscountRepository;

    public List<OrderDiscount> saveOrderDiscount(@NonNull List<OrderDiscount> orderDiscountList) {

        for (OrderDiscount orderdiscount:orderDiscountList){
            orderdiscount = orderDiscountRepository.saveOrderDiscount(orderdiscount);
        }

        return orderDiscountList;
    }

    public List<OrderDiscount> getOrderDiscount() {
        return orderDiscountRepository.getOrderDiscount();
    }

    public List<OrderDiscount> getOrderDiscount(String orderId) {
        int intId = Integer.parseInt(orderId);
        return orderDiscountRepository.getOrderDiscountByOrderId(intId);
    }

    public OrderDiscounts getOrderDiscounts(String orderId) {
        int intId = Integer.parseInt(orderId);
        return new OrderDiscounts(orderDiscountRepository.getOrderDiscountByOrderId(intId));
    }

    public String deleteOrderDiscount(String id) {
        OrderDiscount orderDiscount = orderDiscountRepository.getOrderDiscountById(Integer.parseInt(id));
        return orderDiscountRepository.deleteOrderDiscount(orderDiscount);
    }
}
