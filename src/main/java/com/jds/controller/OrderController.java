package com.jds.controller;

import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.BackResponse.OrderResponse;
import com.jds.model.BackResponse.Response;
import com.jds.model.Exeption.ResponseException;
import com.jds.model.modelEnum.OrderStatus;
import com.jds.service.OrderService;
import com.jds.service.UserServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class OrderController {


    @Autowired
    private UserServ userService;
    @Autowired
    private OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(value = "/orders/page-list")
    public String getOrdersPage(Model model,
                                @RequestParam(required = false, defaultValue = "0") String userId) {

        List<DoorOrder> list;
        boolean report = false;
        if (!"0".equals(userId)) {
            list = orderService.getOrders(userId);
            report = true;
        } else {
            list = orderService.getOrders();
        }

        model.addAttribute("report", report);
        model.addAttribute("orders", list);
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());

        return "orders";
    }

    @GetMapping(value = "/orders/{orderId}/page")
    public String getOrderPage(Model model, @PathVariable String orderId) {

        UserEntity user = userService.getCurrentUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("orderId", (orderId == null) ? 0 : orderId);
        return "order";
    }

    @GetMapping(value = "/getOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorOrder getOrder(@RequestParam(required = false) String orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderResponse saveOrder(@RequestBody DoorOrder order) {

        return orderService.checkAccessAndSave(order);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteOrder(@PathVariable String id) {

        return orderService.deleteOrder(id);
    }

    @Secured("ROLE_ONE_C")
    @PostMapping(value = "/loading/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DoorOrder> getOrders() {

        return orderService.getOrders(OrderStatus.TO_WORK);
    }

    @Secured("ROLE_ONE_C")
    @PostMapping(value = "/orders/{orderId}/statuses/{status}")
    @ResponseBody
    public Response setOrdersStatus(@PathVariable String orderId,
                                    @PathVariable String status,
                                    @RequestParam() String releaseDate) throws ResponseException {

        try {
            boolean result = orderService.setStatus(
                    Integer.parseInt(orderId),
                    OrderStatus.parseForFactory(status),
                    new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate));

            return new Response(result, "ok");
        } catch (ParseException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage());
        }
    }
}
