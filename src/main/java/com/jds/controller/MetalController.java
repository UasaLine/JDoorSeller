package com.jds.controller;

import com.jds.entity.Metal;
import com.jds.service.FurnitureService;
import com.jds.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MetalController {
    @Autowired
    private MetalService service;

    @GetMapping(value = "/metal")
    public String getMetal(Model model) throws Exception {

        List<Metal> list = service.getMetals();
        model.addAttribute("metalList", list);
        return "metalList";
    }
}
