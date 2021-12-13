package com.jds.controller;

import com.jds.dao.entity.DoorClass;
import com.jds.model.enumModels.PriceGroups;
import com.jds.dao.entity.DoorType;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class DoorTypeController {

    @Autowired
    private MaineService service;

    @GetMapping(value = "/doortype/price-group", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<PriceGroups> getAllPriceGroup() throws Exception {
        return service.getAllPriceGroup();
    }

    @PostMapping(value = "/doortype")
    public String saveDoorType(@RequestParam(required = false) String typeId,
                               @RequestParam(required = false) String classId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String namePicture,
                               @RequestParam(required = false) int doorLeaf,
                               @RequestParam(required = false) String nameForPrint,
                               @RequestParam(required = false) String nameForPrintInternalOpening,
                               @RequestParam(required = false) int daysToRelease,
                               @RequestParam(required = false) int markUp,
                               @RequestParam(required = false) int markUpGlassPackage,
                               @RequestParam(required = false) boolean priceList,
                               @RequestParam(required = false) double retailPrice,
                               @RequestParam(required = false) double wholesalePriceFromStock1,
                               @RequestParam(required = false) double wholesalePriceFromStock2,
                               @RequestParam(required = false) double wholesalePriceFromOrder,
                               Model model) throws Exception {


        model.addAttribute("typeId", typeId);
        model.addAttribute("classId", classId);
        model.addAttribute("type", service.getDoorType(
                service.saveOrUpdateDoorType(typeId, classId, name,
                        namePicture, doorLeaf,
                        nameForPrint, nameForPrintInternalOpening,
                        daysToRelease, markUp, markUpGlassPackage,
                        priceList, retailPrice,
                        wholesalePriceFromStock1, wholesalePriceFromStock2, wholesalePriceFromOrder)));

        return "doorType";
    }

    @GetMapping(value = "/pages/doortype/{typeId}")
    public String getDoorType(@PathVariable String typeId,
                              @RequestParam(required = false) String classId,
                              Model model) throws Exception {

        model.addAttribute("typeId", typeId);
        model.addAttribute("classId", classId);
        model.addAttribute("type", service.getDoorType(typeId));

        return "doorType";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/pages/doortype")
    public String getDoorTypeList(Model model, @RequestParam(required = false) String classId) throws Exception {

        List<DoorClass> doorClassList = service.getDoorClass();

        if (classId == null) {
            classId = "1";
        }

        model.addAttribute("classId", classId);
        model.addAttribute("doorClassList", doorClassList);
        model.addAttribute("doorTypeList", service.getTypesByClassId(classId));

        return "doorTypeList";
    }

    @GetMapping(value = "/doortype/item/{typeId}")
    @ResponseBody
        public DoorType getDoorType (@PathVariable String typeId) {
        return service.getDoorTypeclearNonSer(typeId);
    }
}
