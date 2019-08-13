package com.jds.controller;

import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrintController {

    @Autowired
    private MaineService service;

    @GetMapping(value = "/orderprint")
    public String getPrintOrder(Model model,
                                @RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("order", service.getOrder(orderId));
        return "orderPrint";
    }

}
