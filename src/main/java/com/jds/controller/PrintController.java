package com.jds.controller;

import com.jds.model.orderPrint.OrderPrint;
import com.jds.model.PrintAppToTheOrder;
import com.jds.service.OrderDiscountService;
import com.jds.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PrintController {

    @Autowired
    private OrderService service;
    @Autowired
    private OrderDiscountService orderDiscountService;

    @GetMapping(value = "/print/order/{orderId}")
    public String getPrintOrder(Model model, @PathVariable String orderId) {

        model.addAttribute("order", new OrderPrint(service.getOrder(orderId), orderDiscountService.getOrderDiscounts(orderId)));
        return "orderPrint";
    }

    @GetMapping(value = "/print/doors/{orderId}")
    public String getPrintDoors(Model model, @PathVariable String orderId) {

        model.addAttribute("orderId", orderId);
        return "doorsPrint";
    }

    @GetMapping(value = "/print/app/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PrintAppToTheOrder> getPrintAppToTheOrder(@PathVariable String orderId) {

        return service.getPrintAppList(orderId);
    }

}
