package com.jds.controller;

import com.jds.entity.UserEntity;

import com.jds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String getUser(Model model,@RequestParam(required = false) String userId) throws Exception {

        model.addAttribute("userId", (userId == null) ? "0" : userId);
        model.addAttribute("user",service.getUser((userId == null) ? "0" : userId));
        return "doorUser";

    }

    @PostMapping(value = "/user")
    public String saveUser(Model model,@RequestParam(required = false) String username,
                           @RequestParam(required = false) String userId,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) boolean enabledСheckbox) throws Exception {

        service.saveUser(userId,username,password,enabledСheckbox);

        List<UserEntity> list = service.getUsers();
        model.addAttribute("users", list);
        return "doorUserList";

    }

    @GetMapping(value = "usersetting")
    public String getUsersSettingPage(Model model) throws Exception {


        return "userSetting";
    }

}
