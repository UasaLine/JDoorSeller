package com.jds.controller;

import com.jds.model.modelEnum.PriceGroups;
import com.jds.service.ColorService;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.EnumSet;

public class DoorTypeController {

    @Autowired
    private MaineService service;

    @GetMapping(value = "/doortype/price-group", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EnumSet<PriceGroups> getAllPriceGroup() throws Exception {

        return service.getAllPriceGroup();
    }
}
