package com.jds.controller;

import com.jds.dao.entity.DoorFurniture;
import com.jds.model.AvailableFieldsForSelection;
import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.backResponse.ResponseModel;
import com.jds.model.enumModels.TypeOfFurniture;
import com.jds.model.image.ColorPicture;
import com.jds.model.image.TypeView;
import com.jds.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FurnitureController {

    @Autowired
    private FurnitureService service;


    @GetMapping(value = "/furniture")
    @Secured("ROLE_ADMIN")
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

    @PutMapping(value = "/furniture/item")
    @ResponseBody
    public ResponseMassage saveFurniture(@RequestBody DoorFurniture furniture) {

        if (furniture.getName() == null || "".equals(furniture.getName())) {
            return new ResponseMassage(false, "the name must not be empty");
        } else if (furniture.getIdManufacturerProgram().length() > 12) {
            return new ResponseMassage(false, "idManufacturer must not exceed 12 characters");
        }
        service.saveFurniture(furniture);
        return new ResponseMassage(true, "ok");
    }


    @GetMapping(value = "/furniture/types")
    @ResponseBody
    public List<TypeView> getTypes() {
        return service.getTypesFurniture();
    }

    @GetMapping(value = "/furniture/available-fields/{door_type_id}")
    @ResponseBody
    public AvailableFieldsForSelection getAvailableFields(@PathVariable String door_type_id) {

        return service.getAvailableFields(door_type_id);

    }

    @DeleteMapping(value = "/furniture/{id}")
    @ResponseBody
    public ResponseModel deleteFurniture(@PathVariable String id) {

        return new ResponseModel(service.deleteFurniture(id));

    }

    @GetMapping(value = "/furniture/pic/path/{type}")
    @ResponseBody
    public List<ColorPicture> getPicFilePathByType(@PathVariable TypeOfFurniture type) {

        return service.getPicByType(type);

    }

    @GetMapping(value = "/furniture/sketch/path/{type}")
    @ResponseBody
    public List<ColorPicture> getSketchFilePathByType(@PathVariable TypeOfFurniture type) {

        return service.getSketchByType(type);

    }

}
