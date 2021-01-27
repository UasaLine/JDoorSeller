package com.jds.controller;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.LineSpecification;
import com.jds.dao.entity.SpecificationEntity;
import com.jds.service.DoorService;
import com.jds.service.MaineService;
import com.jds.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoorController {

    @Autowired
    private DoorService service;
    @Autowired
    private SpecificationService specificationService;

    @GetMapping(value = "/doors/{id}/page")
    public String calculationPage(Model model,
                                  @RequestParam(required = false) String orderId,
                                  @PathVariable String id,
                                  @RequestParam(required = false) String typeid) {

        model.addAttribute("orderId", orderId);
        model.addAttribute("id", id);
        model.addAttribute("typid", typeid);
        return "calculation";
    }

    @GetMapping(value = "/doors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity getDoor(@PathVariable String id,
                              @RequestParam(required = false, defaultValue = "0") String orderId,
                              @RequestParam(required = false, defaultValue = "0") String typeId) {

        return service.getDoor(Integer.parseInt(id),
                Integer.parseInt(orderId),
                Integer.parseInt(typeId));
    }


    @PostMapping(value = "/doors/price", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity getPrice(@RequestBody DoorEntity door) {

        return service.calculate(door);
    }

    @PostMapping(value = "/doors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity saveDoor(@RequestBody DoorEntity door) {

        return service.saveDoor(door);
    }

    @DeleteMapping(value = "/doors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorOrder deleteOrder(
            @RequestParam(required = false) String orderId,
            @PathVariable String id) {

        return service.deleteDoorFromOrder(id, orderId);
    }

    @GetMapping(value = "/doors/{id}/specifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SpecificationEntity getDoor(@PathVariable String id) {

        return specificationService.getSpecificationByDoorId(Integer.parseInt(id));

    }

}
