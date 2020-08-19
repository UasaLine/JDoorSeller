package com.jds.controller;

import com.jds.dao.entity.*;
import com.jds.model.Specification;
import com.jds.service.MaineService;
import com.jds.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {


    @Autowired
    private MaineService service;
    @Autowired
    private UserServ userService;

    @GetMapping(value = "/")
    public String updateDoorClass() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity principal = (UserEntity) authentication.getPrincipal();

        if (principal.isAdmin()) {
            return "setting";
        }
        return "redirect:orders";
    }

    @GetMapping(value = "/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout, Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping(value = "/setting")
    public String setting(Model model) throws Exception {
        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "setting";
    }

    @GetMapping(value = "/doorclasslist")
    public String getDoorClassList(Model model) throws Exception {
        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "doorClassList";
    }

    @GetMapping(value = "/class/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DoorClass> getDoorClassis(Model model) throws Exception {
        return service.getDoorClass();
    }

    @GetMapping(value = "/specification")
    public String getSpecificationPage(Model model) throws Exception {
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());
        return "specification";
    }

    @GetMapping(value = "/specificationbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Specification getSpecification(@RequestParam(required = false) String typeId,
                                          Model model) throws Exception {

        return service.getSpecification(typeId);
    }

    @PostMapping(value = "/specification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LineSpecification> saveSpecification(@RequestBody Specification templateJSON) throws Exception {

        return service.saveSpecification(templateJSON);

    }

    @GetMapping(value = "/availableGroups")
    @ResponseBody
    public List<DoorClass> getAavailableDoorClass() throws Exception {

        return service.getDoorClass();

    }

    @GetMapping(value = "/materials")
    public String getMaterialPage() {
        return "materialList";
    }

    @GetMapping(value = "/materialsList")
    @ResponseBody
    public List<RawMaterials> getAllMaterials() {
        return service.getAllMaterials();
    }

}
