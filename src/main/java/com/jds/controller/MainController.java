package com.jds.controller;

import com.jds.Application;
import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.entity.Metal;
import com.jds.entity.SizeOfDoorParts;
import com.jds.model.Door;
import com.jds.model.DoorPart;
import com.jds.model.FireproofDoor;
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
    private MaineService Service;

    @Autowired //test
    MainDAO mainDAO; //test

    @GetMapping(value = "/")
    public String updateDoorClass(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson) throws Exception {

        return "MinePage";
    }

    @GetMapping(value = "/setting")
    public String setting(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = Service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass.html";
    }
    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<DoorClass> list = Service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "settingPage_doorclass";
    }
    @GetMapping(value = "/doortype")
    public String getDoorType(@RequestParam(required = false) String kay,
                               @RequestParam(required = false) String dataJson, Model model) throws Exception {


        List<DoorType> list = Service.getDoorType();
        model.addAttribute("accountInfos", list);
        return "settingPage_doortype";
    }

    @GetMapping(value = "/metal")
    public String getMetal(@RequestParam(required = false) String kay,
                              @RequestParam(required = false) String dataJson, Model model) throws Exception {

        List<Metal> list = Service.getMetals();
        model.addAttribute("accountInfos", list);
        return "settingPage_metal";
    }


    @GetMapping(value = "/calculation")
    public String calculation(@RequestParam(required = false) String kay,
                              @RequestParam(required = false) String dataJson, Model model) throws Exception {


        List<FireproofDoor> list = Service.getlistDoor();
        model.addAttribute("doors", list);

        return "calculation";
    }

    @GetMapping(value = "/data",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Door data(@RequestParam(required = false) String kay,
                     @RequestParam(required = false) String dataJson,
                     Model model,
                     @RequestParam(required = false) String id) throws Exception {

        return FireproofDoor.createNewDoorOrGetById(id);
    }

    @PostMapping(value = "/data", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Door calculateTheDoor(@RequestParam(required = false) String kay,
                     @RequestParam(required = false) String dataJson,
                     Model model,
                     @RequestBody FireproofDoor door) throws Exception {

        Service.calculateTheDoor(door);
        return door;
    }
    @PostMapping(value = "/cutting", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Sheet> cuttingTheDoor(@RequestParam(required = false) String kay,
                                      @RequestParam(required = false) String dataJson,
                                      Model model,
                                      @RequestBody FireproofDoor door) throws Exception {

        List<Sheet> sheets = Application.testDelete();
        //return sheets.get(0).getContainsParts();
        return sheets;
    }

    @PostMapping(value = "/saveDoor", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FireproofDoor saveDoor(@RequestParam(required = false) String kay,
                                      @RequestParam(required = false) String dataJson,
                                      Model model,
                                      @RequestBody FireproofDoor door) throws Exception {


        return Service.saveDoor(door);
    }
}
