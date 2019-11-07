package com.jds.controller;

import com.jds.entity.UserEntity;

import com.jds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/users")
    public String getUsers(Model model) throws Exception {
        List<UserEntity> list = service.getUsers();
        model.addAttribute("users", list);
        return "doorUserList";
    }

    @GetMapping(value = "/user")
    public String getOrder(Model model,@RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("userId", (orderId == null) ? 0 : orderId);
        return "doorUser";

    }

}
