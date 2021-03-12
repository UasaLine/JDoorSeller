package com.jds.controller;

import com.jds.dao.entity.LineSpecification;
import com.jds.dao.entity.SpecificationEntity;
import com.jds.model.exeption.ResponseException;
import com.jds.model.backResponse.ResponseModel;
import com.jds.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SpecificationController {

    @Autowired
    private SpecificationService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/specifications")
    public String getSpecificationListPage(Model model) throws Exception {

        List<SpecificationEntity> list = service.getLineSpecification();
        model.addAttribute("List", list);
        return "specificationList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/specifications/{id}")
    public String getSpecificationPage(@PathVariable String id) throws Exception {

        return "specificationEntity";
    }

    @PostMapping(value = "/specifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SpecificationEntity save(@RequestBody SpecificationEntity specification) {

        String manufacturerId = specification.getManufacturerId();
        if (manufacturerId == null || "".equals(manufacturerId)) {
            throw new ResponseException("manufacturerId should not be empty");
        }

        return service.save(specification);
    }

    @GetMapping(value = "/specifications/{id}")
    @ResponseBody
    public SpecificationEntity getSpecificationEntity(@PathVariable String id) {

        return service.getSpecificationEntity(id);
    }

    @DeleteMapping(value = "/specifications/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseBody
    public ResponseModel deleteSpecification(@PathVariable String id) {

        return new ResponseModel(service.deleteSpecificationEntity(id));
    }

    @GetMapping(value = "/pages/specification/{id}/line/{line_id}")
    @Secured("ROLE_ADMIN")
    public String getSpecificationLinePage(Model model, @PathVariable String id, @PathVariable String line_id) {

        model.addAttribute("id", id);
        model.addAttribute("line_id", line_id);
        return "specificationLine";
    }

    @GetMapping(value = "/specification/{id}/line/{line_id}")
    @ResponseBody
    public LineSpecification getSpecificationLine(@PathVariable String id, @PathVariable String line_id) {

        return service.getLineSpecification(line_id);
    }

    @PostMapping(value = "/specification/line/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    @ResponseBody
    public LineSpecification saveSpecificationLine(@RequestBody LineSpecification lineSpecification, @PathVariable String id) throws Exception {

        return service.saveSpecificationLine(Integer.parseInt(id), lineSpecification);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/specification/line/{id}")
    @ResponseBody
    public String deleteLineSpecification(@PathVariable String id) {
        return service.deleteLineSpecification(id);
    }

}
