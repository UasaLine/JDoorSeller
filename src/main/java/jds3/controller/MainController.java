package jds3.controller;


import jds3.dao.DoorClassDAO;
import jds3.entity.DoorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

    @Autowired
    DoorClassDAO doorClassDAO;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int controllerXML(@RequestParam("request") String dataXML) {

        doorClassDAO.addDoorClass();

        DoorClass doorClass = doorClassDAO.getDoorClass();

        return doorClass.getId();

    }

    @GetMapping("/")
    public String getXML() {


        return "/hello3";

    }
}
