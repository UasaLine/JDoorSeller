package com.jds.controller;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.model.backResponse.ResponseList;
import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.backResponse.ResponseModel;
import com.jds.model.enumModels.GlassPositionType;
import com.jds.service.GlassPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class GlassPositionController {

    @Autowired
    private GlassPositionService service;

    @GetMapping(value = "/glass-position")
    @ResponseBody
    public ResponseList<GlassPositionEntity> get() {
        return new ResponseList<>(service.get());
    }

    @PutMapping(value = "/glass-positions")
    @ResponseBody
    public ResponseModel<GlassPositionEntity> save(@RequestBody GlassPositionEntity positionEntity) {
        return new ResponseModel<>(service.save(positionEntity));
    }

    @GetMapping(value = "/glass-position/{id}")
    @ResponseBody
    public ResponseModel<GlassPositionEntity> get(@PathVariable int id) {
        return new ResponseModel<>(service.getById(id));
    }

    @GetMapping(value = "/glass-position/types")
    @ResponseBody
    public List<GlassPositionType> getTypes() {
        return Arrays.asList(GlassPositionType.values());
    }

    @GetMapping(value = "/glass-position/types/value-names/{type}")
    @ResponseBody
    public Map<String, String> getTypes(@PathVariable GlassPositionType type) {
        return type.getNamesMap();
    }

    @GetMapping(value = "/pages/glass-position")
    @Secured("ROLE_ADMIN")
    public String getListPage(Model model) {

        model.addAttribute("List", service.get());
        return "glassPositionList";
    }

    @GetMapping(value = "/pages/glass-position/{id}")
    @Secured("ROLE_ADMIN")
    public String getPage(@PathVariable String id) {
        return "glassPosition";
    }

    @DeleteMapping(value = "/glass-positions/{id}")
    @ResponseBody
    public ResponseMassage deleteFurniture(@PathVariable int id) {
        return new ResponseMassage(true, service.delete(id));
    }
}
