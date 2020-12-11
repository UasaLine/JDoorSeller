package com.jds.controller;

import com.jds.dao.entity.LineSpecification;
import com.jds.dao.entity.RawMaterials;
import com.jds.model.Specification;
import com.jds.service.MaineService;
import com.jds.service.MaterialsService;
import com.jds.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MaterialsController {

    @Autowired
    private MaterialsService service;
    @Autowired
    private UserServ userService;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/materials")
    public String getMaterialPage() {
        return "materialList";
    }

    @GetMapping(value = "/materialsList")
    @ResponseBody
    public List<RawMaterials> getAllMaterials() {
        return service.getAllMaterials();
    }

    @Secured("ROLE_ADMIN")
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
}
