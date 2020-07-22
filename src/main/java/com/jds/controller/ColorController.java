package com.jds.controller;

import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.ColorPicture;
import com.jds.model.ResponseAction;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import com.jds.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;

@Controller
public class ColorController {
    @Autowired
    private ColorService service;

    @GetMapping(value = "/color")
    public String getMetalListPage(Model model) throws Exception {

        List<ImageEntity> list = service.getColors();
        model.addAttribute("List", list);
        return "colorList";
    }

    @GetMapping(value = "/color/{id}")
    public String getMetalPage(@PathVariable String id) throws Exception {
        return "color";
    }

    @GetMapping(value = "/color/item/{id}")
    @ResponseBody
    public ImageEntity getColor(@PathVariable String id) {

        return service.getColor(id);

    }

    @PutMapping(value = "/color/item")
    @ResponseBody
    public ResponseAction saveColor(@RequestBody ImageEntity color) {

        return new ResponseAction(service.saveColor(color));

    }

    @GetMapping(value = "/color/image-type/{imageType}")
    @ResponseBody
    public List<ColorPicture> getColorList(@PathVariable String imageType) {

        return  service.getImageList(imageType);

    }

    @DeleteMapping(value = "/color/{id}")
    @ResponseBody
    public ResponseAction deleteColor(@PathVariable String id) {

        return new ResponseAction(service.deleteColor(id));

    }

    @GetMapping(value = "/image/types")
    @ResponseBody
    public EnumSet<TypeOfImage> getImageTypeList() {

        return service.getImageTypeList();

    }

    @GetMapping(value = "/door-color/types")
    @ResponseBody
    public EnumSet<TypeOfDoorColor> getImageTypeDoorColor() {

        return service.getImageTypeDoorColor();

    }
}
