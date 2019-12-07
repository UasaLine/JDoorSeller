package com.jds.controller;

import com.jds.entity.DoorsОrder;
import com.jds.entity.UserEntity;
import com.jds.service.MaineService;
import com.jds.service.OrderService;
import com.jds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {


    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    @GetMapping(value = "/orders")
    public String getOrdersPage(Model model) throws Exception {
        List<DoorsОrder> list = orderService.getOrders();
        model.addAttribute("accountInfos", list);
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());
        return "orders";
    }

    @GetMapping(value = "/order")
    public String getOrderPage(Model model, @RequestParam(required = false) String orderId) throws Exception {

        UserEntity user = userService.getCurrentUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("orderId", (orderId == null) ? 0 : orderId);
        return "order";
    }

    @GetMapping(value = "/getOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder getOrderObject(@RequestParam(required = false) String orderId) throws Exception {

        return orderService.getOrder(orderId);
    }

    @PostMapping(value = "/saveOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder saveOrder(@RequestBody DoorsОrder order) throws Exception {

        return orderService.saveOrder(order);
    }

    @DeleteMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteOrder(@RequestParam(required = false) String orderId) throws Exception {

        return orderService.deleteOrder(orderId);
    }


}
