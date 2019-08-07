package com.jds.controller;

import com.jds.Application;
import com.jds.dao.MainDAO;
import com.jds.entity.*;
import com.jds.model.Door;
import com.jds.model.DoorPart;
import com.jds.model.FireproofDoor;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.cutting.Sheet;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MaineService service;


    @GetMapping(value = "/")
    public String updateDoorClass(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson) throws Exception {

        return "MinePage";
    }

    @GetMapping(value = "/setting")
    public String setting(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass.html";
    }
    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doortype")
    public String getDoorType(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {


        List<DoorType> list = service.getDoorType();
        model.addAttribute("accountInfos", list);
        return "settingPage_doortype";
    }

    @GetMapping(value = "/metal")
    public String getMetal(@RequestParam(required = false) String kay,
                              @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<Metal> list = service.getMetals();
        model.addAttribute("accountInfos", list);
        return "settingPage_metal";
    }


    @GetMapping(value = "/calculation")
    public String calculation(Model model,
                              @RequestParam(required = false) String orderId,
                              @RequestParam(required = false) String id) throws Exception {
        model.addAttribute("orderId", orderId);
        model.addAttribute("id", id);
        return "calculation";
    }

    @GetMapping(value = "/door",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity data(Model model,
                         @RequestParam(required = false) String id,
                         @RequestParam(required = false) String orderId) throws Exception {

        return service.getDoor(id,orderId);
    }

    @PostMapping(value = "/data", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity calculateTheDoor(Model model,
                     @RequestBody DoorEntity door) throws Exception {

        return service.calculateTheDoor(door);
    }
    @PostMapping(value = "/cutting", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Sheet> cuttingTheDoor(@RequestParam(required = false) String kay,
                                      @RequestParam(required = false) String dataJson,
                                      Model model,
                                      @RequestBody DoorEntity door) throws Exception {

        List<Sheet> sheets = Application.testDelete();
        //return sheets.get(0).getContainsParts();
        return sheets;
    }

    @PostMapping(value = "/saveDoor", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorEntity saveDoor(@RequestParam(required = false) String kay,
                                      @RequestParam(required = false) String dataJson,
                                      Model model,
                                      @RequestBody DoorEntity door) throws Exception {


        return service.saveDoor(door);
    }

    @GetMapping(value = "/doorlimit", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestrictionOfSelectionFields getRestrictionOfSelectionFields(Model model,
                               @RequestParam String idDoorType) throws Exception {

        return service.getRestrictionOfSelectionFields(idDoorType);
    }

    @GetMapping(value = "/getOrder", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder saveOrder(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson,
                                  Model model,
                                  @RequestParam(required = false) String orderId) throws Exception {


        return service.getOrder(orderId);
    }

    @PostMapping(value = "/saveOrder", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder saveOrder(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson,
                                  Model model,
                                  @RequestBody DoorsОrder order) throws Exception {


        return service.saveOrder(order);
    }

    @GetMapping(value = "/order")
    public String getOrder(@RequestParam(required = false) String kay,
                           @RequestParam(required = false) String dataJson, Model model,
                           @RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("orederId", (orderId==null)? 0:orderId);
        return "order";
    }

    @DeleteMapping(value = "/door", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder deleteOrder(@RequestParam(required = false) String kay,
                           @RequestParam(required = false) String dataJson, Model model,
                           @RequestParam(required = false) String orderId,
                           @RequestParam(required = false) String id ) throws Exception {


        return service.deleteDoorFromOrder(id,orderId);
    }

    @GetMapping(value = "/orders")
    public String getOrders(Model model) throws Exception {
        List<DoorsОrder> list = service.getOrders();
        model.addAttribute("accountInfos", list);
        return "orders";
    }

    @DeleteMapping(value = "/order", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder deleteOrder( Model model,
                                  @RequestParam(required = false) String orderId) throws Exception {

        return service.deleteOrder(orderId);
    }

    @GetMapping(value = "/orderprint")
    public String getPrintOrder(Model model,
                                @RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("order", service.getOrder(orderId));
        return "orderPrint";
    }
}
