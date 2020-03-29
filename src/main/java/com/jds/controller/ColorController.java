package com.jds.controller;

import com.jds.dao.entity.ColorEntity;
import com.jds.model.ResponseAction;
import com.jds.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ColorController {
    @Autowired
    private ColorService service;

    @GetMapping(value = "/color")
    public String getMetalListPage(Model model) throws Exception {

        List<ColorEntity> list = service.getColors();
        model.addAttribute("List", list);
        return "colorList";
    }

    @GetMapping(value = "/color/{id}")
    public String getMetalPage(@PathVariable String id) throws Exception {
        return "color";
    }

    @GetMapping(value = "/color/item/{id}")
    @ResponseBody
    public ColorEntity getFurniture(@PathVariable String id) {

        return service.getColor(id);

    }

    @PutMapping(value = "/color/item")
    @ResponseBody
    public ResponseAction saveMetal(@RequestBody ColorEntity color) {

        return new ResponseAction(service.saveColor(color));

    }

    @DeleteMapping(value = "/color/{id}")
    @ResponseBody
    public ResponseAction deleteFurniture(@PathVariable String id) {

        return new ResponseAction(service.deleteColor(id));

    }
}
