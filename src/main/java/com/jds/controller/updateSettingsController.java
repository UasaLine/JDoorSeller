package com.jds.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jds.dao.MainDAO;
import com.jds.entity.*;
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
    public String updateDoorClass(@RequestParam(required = false) String kay,
                                  @RequestParam(required = false) String dataJson) throws Exception {


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
    public String updateDoorType(@RequestParam(required = false) String kay,
                                 @RequestParam(required = false) String dataJson) throws Exception {

        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorType doorType = mapper.readValue(reader, DoorType.class);
        int id = mainDAO.saveOrUpdateDoorType(doorType);

        return String.valueOf(id);
    }

    @PostMapping(value = "/update/sizeofdoorparts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateSizeOfDoorParts(@RequestParam(required = false) String kay,
                                        @RequestParam(required = false) String dataJson) throws Exception {

        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        SizeOfDoorParts sizeOfDoorParts = mapper.readValue(reader, SizeOfDoorParts.class);
        mainDAO.saveOrUpdateSizeOfDoorParts(sizeOfDoorParts);

        return "jr";
    }


    @PostMapping(value = "/update/metal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateMetal(@RequestParam(required = false) String kay,
                              @RequestParam(required = false) String dataJson) throws Exception {


            StringReader reader = new StringReader(dataJson);
            ObjectMapper mapper = new ObjectMapper();

            Metal metal = mapper.readValue(reader, Metal.class);
            mainDAO.saveMetal(metal);

        return "jr";
    }

    @PostMapping(value = "/update/Furniture", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateFurniture(@RequestParam("request") String data,@RequestBody String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorFurniture doorFurniture = mapper.readValue(reader, DoorFurniture.class);
        mainDAO.saveDoorFurniture(doorFurniture);

        return "jr";
    }

    @PostMapping(value = "/update/doorcolors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorColors(@RequestParam("request") String data,@RequestBody String dataJson) throws Exception {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorColors doorColors = mapper.readValue(reader, DoorColors.class);
        mainDAO.saveDoorColors(doorColors);

        return "jr";
    }

}
