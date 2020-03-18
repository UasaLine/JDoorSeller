package com.jds.controller;

import com.jds.entity.DoorFurniture;
import com.jds.service.ComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ComponentsController {

    @Autowired
    private ComponentsService service;

    @GetMapping(value = "/furniture")
    public String getFurnitureListPage(Model model) {

        model.addAttribute("furnitureList", service.getFurnitureList());
        return "furnitureList";
    }

    @GetMapping(value = "/furniture/{id}")
    public String getFurniturePage(@PathVariable String id) {

        return "furniture";
    }

    @GetMapping(value = "/furniture/item/{id}")
    @ResponseBody
    public DoorFurniture getFurniture(@PathVariable String id) {


        return service.getDoorFurniture(id);

    }

}
