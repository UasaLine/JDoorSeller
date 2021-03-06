package com.jds.controller;

import com.jds.model.DoorTemplate;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.service.MaineService;
import com.jds.service.TemplateService;
import com.jds.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TemplateController {

    @Autowired
    private TemplateService service;
    @Autowired
    private UserServ userService;
    @Autowired
    private MaineService maineService;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/templates/page-list")
    public String getTemplateList(Model model) {

        model.addAttribute("accountInfos", service.getTemplateList());
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());
        return "templateList";
    }

    @GetMapping(value = "/templates/{typeId}/page")
    public String getTemplate(@PathVariable String typeId,
                              Model model) {

        model.addAttribute("typeId", typeId);
        model.addAttribute("classId", maineService.getClassId(typeId));
        return "template";
    }

    @GetMapping(value = "/templates/{typeId}")
    @ResponseBody
    public DoorTemplate getTemplate(@PathVariable String typeId) {

        return service.getDoorTemplate(typeId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveTemplate(@RequestBody RestrictionOfSelectionFields template) {

        service.saveDoorTemplate(template);
    }
}
