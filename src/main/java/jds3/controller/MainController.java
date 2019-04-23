package jds3.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jds3.dao.DoorClassDAO;
import jds3.entity.DoorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    DoorClassDAO doorClassDAO;

    @PostMapping(value = "/XML", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String controllerXML(@RequestParam("request") String dataJson) {

        //doorClassDAO.addDoorClass();

        List<DoorClass> doorClass = doorClassDAO.getDoorClass();

        return "or";

    }

    @PostMapping(value = "/update/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDoorClass(@RequestParam("request") String dataJson) {


        StringReader reader = new StringReader(dataJson);
        ObjectMapper mapper = new ObjectMapper();

        DoorClass doorClass;
        try {
            doorClass = mapper.readValue(reader, DoorClass.class);
        } catch (IOException e) {
            System.out.println("ERROR: JSON DOWN!!!");
        }


        return "jr";
    }

    @GetMapping("/")
    public String getXML() {


        return "/hello3";

    }
}
