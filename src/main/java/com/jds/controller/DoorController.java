package com.jds.controller;

import com.jds.entity.DoorEntity;
import com.jds.entity.DoorsОrder;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DoorController {

    @Autowired
    private MaineService service;

    @GetMapping(value = "/calculation")
    public String calculation(Model model,
                              @RequestParam(required = false) String orderId,
                              @RequestParam(required = false) String id,
                              @RequestParam(required = false) String doorGroup) throws Exception {
        model.addAttribute("orderId", orderId);
        model.addAttribute("id", id);
        model.addAttribute("doorGroup", doorGroup);
        return "calculation";
    }

    @GetMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity data(Model model,
                           @RequestParam(required = false) String id,
                           @RequestParam(required = false) String orderId,
                           @RequestParam(required = false) String doorGroup) throws Exception {

        return service.getDoor(id, orderId, doorGroup);
    }

    @GetMapping(value = "/doorlimit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(Model model,
                                                                        @RequestParam String idDoorType) throws Exception {

        return service.getTemplateFromLimits(idDoorType);
    }

    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity calculateTheDoor(Model model, @RequestBody DoorEntity door) throws Exception {

        return service.calculateTheDoor(door);

    }

    @PostMapping(value = "/saveDoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity saveDoor(Model model, @RequestBody DoorEntity door) throws Exception {

        return service.saveDoor(door);

    }

    @DeleteMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder deleteOrder(Model model,
                                  @RequestParam(required = false) String orderId,
                                  @RequestParam(required = false) String id) throws Exception {

        return service.deleteDoorFromOrder(id, orderId);
    }

}
