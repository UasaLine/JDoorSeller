package com.jds.controller;

import com.jds.dao.entity.*;
import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.exeption.ResponseException;
import com.jds.service.MaterialsService;
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


    @GetMapping(value = "/pages/materials")
    @Secured("ROLE_ADMIN")
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

    @GetMapping(value = "/pages/materials/formulas")
    @Secured("ROLE_ADMIN")
    public String getFormulaPage(Model model) {
        model.addAttribute("formulaList", service.getAllFormulas());
        return "formulaList";
    }

    @GetMapping(value = "/materials/formulas")
    @ResponseBody
    public List<MaterialFormula> getAllFormula() {
        return service.getAllFormulas();
    }

    @GetMapping(value = "/materials/formulas/{id}")
    @ResponseBody
    public MaterialFormula getFormula(@PathVariable int id) {
        return service.fineFormula(id);
    }

    @PostMapping(value = "/materials/formulas")
    @ResponseBody
    public MaterialFormula saveFormula(@RequestBody MaterialFormula formula) {
        return service.saveFormula(formula);
    }


    @GetMapping(value = "/pages/materials/formulas/{id}")
    @Secured("ROLE_ADMIN")
    public String getFormulaPages(@PathVariable String id) {
        return "formula";
    }

    @DeleteMapping(value = "/materials/formulas/{id}")
    @ResponseBody
    public ResponseMassage deleteFormula(@PathVariable int id) {
        service.deleteFormula(id);
        return new ResponseMassage(true,"ok");
    }
}
