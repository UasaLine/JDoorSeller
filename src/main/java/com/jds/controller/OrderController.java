package com.jds.controller;

import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.Pagination;
import com.jds.model.exeption.ResponseException;
import com.jds.model.backResponse.ResponseList;
import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.backResponse.ResponseModel;
import com.jds.model.enumClasses.OrderStatus;
import com.jds.model.enumClasses.SideSqlSorting;
import com.jds.model.image.TypeView;
import com.jds.model.orders.OrderParamsDto;
import com.jds.model.orders.filter.OrderFilterFactory;
import com.jds.model.orders.sort.OrderSortFactory;
import com.jds.model.orders.sort.OrderSortField;
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
    @Autowired
    private OrderSortFactory sortFactory;
    @Autowired
    private OrderFilterFactory filterFactory;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(value = "/pages/orders")
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

    @GetMapping(value = "/pages/orders/{orderId}")
    public String getOrderPage(Model model, @PathVariable String orderId) {

        UserEntity user = userService.getCurrentUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("orderId", (orderId == null) ? 0 : orderId);
        return "order";
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseBody
    public ResponseList<DoorOrder> getOrders(@RequestParam(required = false, defaultValue = "DATE") OrderSortField sort_field,
                                             @RequestParam(required = false, defaultValue = "DESC") SideSqlSorting sort_side,
                                             @RequestParam(required = false) OrderStatus status,
                                             @RequestParam(required = false) String partner,
                                             @RequestParam(required = false) String ofDate,
                                             @RequestParam(required = false) String toDate,
                                             @RequestParam(required = false, defaultValue = "10") String limit,
                                             @RequestParam(required = false, defaultValue = "0") String offset) {

        OrderParamsDto params = OrderParamsDto.builder()
                .sorter(sortFactory.sorter(sort_field, sort_side))
                .filter(filterFactory.filter(status, partner, ofDate, toDate))
                .pagination(new Pagination(limit, offset))
                .build();

        return orderService.getOrders(params);
    }

    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseBody
    public DoorOrder getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseModel<DoorOrder> saveOrder(@RequestBody DoorOrder order) {

        return orderService.checkAccessAndSave(order);
    }

    @PostMapping(value = "/to-work/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ONE_C")
    @ResponseBody
    public List<DoorOrder> getToWorkOrders() {

        return orderService.getAllOrders(OrderStatus.TO_WORK);
    }

    @PostMapping(value = "/orders/{orderId}/statuses/{status}")
    @Secured("ROLE_ONE_C")
    @ResponseBody
    public ResponseMassage setOrdersStatus(@PathVariable String orderId,
                                           @PathVariable String status) throws ResponseException {

        try {
            boolean result = orderService.setStatus(
                    Integer.parseInt(orderId),
                    OrderStatus.parseForFactory(status));

            return new ResponseMassage(result, "ok");

        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    @PostMapping(value = "/orders/{orderId}/release/{date}")
    @Secured("ROLE_ONE_C")
    @ResponseBody
    public ResponseMassage setOrdersReleaseDate(@PathVariable String orderId,
                                                @PathVariable String date) throws ResponseException {

        try {
            boolean result = orderService.setReleaseDate(
                    Integer.parseInt(orderId),
                    new SimpleDateFormat("yyyy-MM-dd").parse(date));

            return new ResponseMassage(result, "ok");

        } catch (ParseException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    @DeleteMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseBody
    public ResponseMassage deleteOrder(@PathVariable String id) {

        return orderService.deleteOrder(id);
    }

    @GetMapping(value = "/orders/statuses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TypeView> allStatuses() {
        return orderService.allStatuses();
    }
}
