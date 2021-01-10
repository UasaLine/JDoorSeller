package com.jds.controller;

import com.jds.dao.entity.*;
import com.jds.model.ResponseAction;
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

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/specificationList")
    public String getSpecificationListPage(Model model) throws Exception {

        List<SpecificationEntity> list = service.getLineSpecification();
        model.addAttribute("List", list);
        return "specificationList";
    }

    @GetMapping(value = "/specification/{id}")
    public String getSpecificationPage(@PathVariable String id) throws Exception {

        return "specificationEntity";
    }

    @PutMapping(value = "/specification/item", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SpecificationEntity saveSpecificationEntity(@RequestBody SpecificationEntity specificationEntity) {

        service.saveSpecificationEntity(specificationEntity);

        return specificationEntity;
    }

    @GetMapping(value = "/specification/item/{id}")
    @ResponseBody
    public SpecificationEntity getSpecificationEntity (@PathVariable String id) {

        return service.getSpecificationEtity(id);
    }

    @DeleteMapping(value = "/specification/{id}")
    @ResponseBody
    public ResponseAction deleteColor(@PathVariable String id) {

        return new ResponseAction(service.deleteSpecificationEntity(id));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/specification/addSpecificationLine")
    public String getAddSpecificationLinePage() throws Exception {

        return "addSpecificationLine";
    }

    @PostMapping(value = "/specification/line", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LineSpecification saveSpecificationLine(@RequestBody LineSpecification lineSpecification) throws Exception {

        return service.saveSpecificationLine(lineSpecification);
    }

}
