package com.jds.controller;

import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.LineSpecification;
import com.jds.service.DoorService;
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

    @GetMapping(value = "/calculation")
    public String calculationPage(Model model,
                                  @RequestParam(required = false) String orderId,
                                  @RequestParam(required = false) String id,
                                  @RequestParam(required = false) String typid) {

        model.addAttribute("orderId", orderId);
        model.addAttribute("id", id);
        model.addAttribute("typid", typid);
        return "calculation";
    }

    @GetMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity getDoor(@RequestParam(required = false, defaultValue = "0") String id,
                              @RequestParam(required = false, defaultValue = "0") String orderId,
                              @RequestParam(required = false, defaultValue = "0") String typeId) {

        return service.getDoor(Integer.parseInt(id),
                Integer.parseInt(orderId),
                Integer.parseInt(typeId));
    }


    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity calculateTheDoor(@RequestBody DoorEntity door) {

        return service.calculateTheDoor(door);
    }

    @PostMapping(value = "/saveDoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity saveDoor(@RequestBody DoorEntity door) {

        return service.saveDoor(door);
    }

    @DeleteMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder deleteOrder(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String id) {

        return service.deleteDoorFromOrder(id, orderId);
    }

    @PostMapping(value = "/doorSpec", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LineSpecification> getSpecificationByDoorId(@RequestParam(required = false) String doorId) {

        return service.getSpecificationByDoorId(doorId);
    }

}
