package com.jds.controller;

import com.jds.dao.entity.OrderDiscount;
import com.jds.model.backResponse.ResponseModel;
import com.jds.service.OrderDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderDiscountController {
    @Autowired
    private OrderDiscountService orderDiscountService;

    @PostMapping(value = "/orderDiscount", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderDiscount> saveOrderDiscount(@RequestBody List<OrderDiscount> orderDiscount) throws Exception {

        return orderDiscountService.saveOrderDiscount(orderDiscount);
    }

    @GetMapping(value = "/orderDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderDiscount> getOrderDiscount(@RequestParam(required = false) String orderId) throws Exception {

        return orderDiscountService.getOrderDiscount(orderId);
    }

    @DeleteMapping(value = "/orderDiscount/{id}")
    @ResponseBody
    public ResponseModel deleteOrderDiscount(@PathVariable String id) {

        return new ResponseModel(orderDiscountService.deleteOrderDiscount(id));
    }
}
