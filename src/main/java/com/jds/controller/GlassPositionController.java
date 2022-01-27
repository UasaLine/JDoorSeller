package com.jds.controller;


import com.jds.dao.entity.GlassPositionEntity;
import com.jds.model.backResponse.ResponseList;
import com.jds.model.enumModels.GlassPositionType;
import com.jds.service.GlassPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class GlassPositionController {

    @Autowired
    private GlassPositionService service;

    @GetMapping(value = "/glass-position")
    @ResponseBody
    public ResponseList<GlassPositionEntity> get() {
        return new ResponseList<>(service.get());
    }

    @GetMapping(value = "/glass-position/types")
    @ResponseBody
    public List<GlassPositionType> getTypes() {
        return Arrays.asList(GlassPositionType.values());
    }
}
