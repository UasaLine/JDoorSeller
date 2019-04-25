package com.jds.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jds.dao.MainDAO;
import com.jds.entity.DoorClass;
import com.jds.entity.DoorType;
import com.jds.entity.LimitationDoor;
import com.jds.entity.SizeOfDoorParts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;


@Controller
public class UpdateSettingsController {

    @Autowired
    MainDAO mainDAO;

    @PostMapping(value = "/update/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorClass(@RequestParam(required = false) String kay,@RequestParam(required = false) String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorClass doorClass = mapper.readValue(reader, DoorClass.class);

        mainDAO.saveOrUpdateDoorClass(doorClass);
        return "jr";
    }

    @PostMapping(value = "/update/limitationdoor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateLimitationDoor(@RequestParam("request") String data,@RequestBody LimitationDoor dataJson) {

        mainDAO.saveOrUpdateLimitationDoor(dataJson);

        return "jr";
    }

    @PostMapping(value = "/update/doortype", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorType(@RequestParam("request") String data,@RequestBody DoorType dataJson) {

        mainDAO.saveOrUpdateDoorType(dataJson);

        return "jr";
    }

    @PostMapping(value = "/update/sizeofdoorparts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSizeOfDoorParts(@RequestParam("request") String data,@RequestBody SizeOfDoorParts dataJson) {

        mainDAO.saveOrUpdateSizeOfDoorParts(dataJson);

        return "jr";
    }


}
