package jds3.controller;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

    @PostMapping(value = "/",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String controllerXML(@RequestParam("request") String dataXML) {

        return "ок";

    }
    @GetMapping("/")
    public String getXML() {

        return "/hello3";

    }
}
