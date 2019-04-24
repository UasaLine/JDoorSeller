package com.jds.controller;


import com.jds.dao.DoorClassDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.entity.LimitationDoor;
import com.jds.entity.SizeOfDoorParts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class UpdateSettingsController {

    @Autowired
    DoorClassDAO doorClassDAO;

    @PostMapping(value = "/update/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorClass(@RequestParam("request") String data,@RequestBody DoorClass dataJson) {

        return "jr";
    }

    @PostMapping(value = "/update/limitationdoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateLimitationDoor(@RequestParam("request") String data,@RequestBody LimitationDoor dataJson) {



        return "jr";
    }

    @PostMapping(value = "/update/doortype", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorType(@RequestParam("request") String data,@RequestBody DoorType dataJson) {



        return "jr";
    }

    @PostMapping(value = "/update/sizeofdoorparts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSizeOfDoorParts(@RequestParam("request") String data,@RequestBody SizeOfDoorParts dataJson) {



        return "jr";
    }


}
