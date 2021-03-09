package com.jds.controller;

import com.jds.dao.entity.*;
import com.jds.model.exeption.ResponseException;
import com.jds.service.MaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MaterialsController {

    @Autowired
    private MaterialsService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/materials")
    public String getMaterialPage() {
        return "materialList";
    }

    @PostMapping(value = "/materials", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MaterialEntity saveMaterialsEntity(@RequestBody MaterialEntity material) throws ResponseException {

        String manufacturerId = material.getManufacturerId();
        if (manufacturerId == null || "".equals(manufacturerId)) {
            throw new ResponseException("manufacturerId should not be empty");
        }

        return service.saveMaterialsEntity(material);

    }

    @GetMapping(value = "/materials")
    @ResponseBody
    public List<MaterialEntity> getMaterials() throws ResponseException {

        return service.getMaterials();
    }

    @GetMapping(value = "/materials/{id}")
    @ResponseBody
    public MaterialEntity getMaterial(@PathVariable String id) throws ResponseException {

        return service.getMaterial(Integer.parseInt(id));
    }

    @GetMapping(value = "/raw-materials")
    @ResponseBody
    public List<RawMaterials> getRawMaterials() {
        return service.getRawMaterials();
    }

}
