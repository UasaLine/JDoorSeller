package com.jds.controller;

import com.jds.entity.UserEntity;

import com.jds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "userList";
    }

    @GetMapping(value = "/user")
    public String getUser(Model model,@RequestParam(required = false) String userId) throws Exception {

        model.addAttribute("userId", (userId == null) ? "0" : userId);
        model.addAttribute("user",service.getUser((userId == null) ? "0" : userId));
        return "user";

    }

    @PostMapping(value = "/user")
    public String saveUser(Model model,@RequestParam(required = false) String username,
                           @RequestParam(required = false) String userId,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) int discount,
                           @RequestParam(required = false) boolean enabledСheckbox) throws Exception {

        service.saveUser(userId,username,password,discount,enabledСheckbox);

        List<UserEntity> list = service.getUsers();
        model.addAttribute("users", list);
        return "userList";

    }

    @GetMapping(value = "usersetting")
    public String getUsersSettingPage(Model model) throws Exception {

        model.addAttribute("setting", service.getUserSetting());
        return "userSetting";
    }

    @PostMapping(value = "usersetting")
    public String saveUsersSetting(@RequestParam(required = false) int retailMargin,
                                   @RequestParam(required = false) int salesTax,
                                   @RequestParam(required = false) boolean includesTax) throws Exception {

        service.saveUserSetting(retailMargin,salesTax,includesTax);
        return "redirect:usersetting";
    }

}
