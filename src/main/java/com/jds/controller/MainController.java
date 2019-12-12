package com.jds.controller;

import com.jds.entity.*;
import com.jds.model.DoorTemplate;
import com.jds.model.RestrictionOfSelectionFields;
import com.jds.model.Specification;
import com.jds.service.MaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MaineService service;


    @GetMapping(value = "/")
    public String updateDoorClass() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity principal = (UserEntity) authentication.getPrincipal();

        if (principal.isAdmin()) {
            return "minePageBootstr";
        }
        return "redirect:orders";
    }

    @GetMapping(value = "/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout, Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping(value = "/setting")
    public String setting(Model model) throws Exception {
        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "setting";
    }

    @GetMapping(value = "/doorclasslist")
    public String getDoorClassList(Model model) throws Exception {
        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "doorClassList";
    }

    @GetMapping(value = "/doorclassis", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DoorClass> getDoorClassis(Model model) throws Exception {
        return service.getDoorClass();
    }

    @GetMapping(value = "/doorclass")
    public String getDoorClass(@RequestParam(required = false) String classId, Model model) throws Exception {

        DoorClass doorClass = service.getDoorClass(classId);
        model.addAttribute("classId", classId);
        model.addAttribute("name", doorClass.getName());
        model.addAttribute("description", doorClass.getDescription());
        model.addAttribute("fireproof", doorClass.getFireproof());
        model.addAttribute("hot", doorClass.getHot());
        model.addAttribute("namePicture",doorClass.getNamePicture());

        return "doorClass";
    }

    @PostMapping(value = "/doorclass")
    public String saveDoorClass(@RequestParam(required = false) String classId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) boolean fireproofcheckbox,
                                @RequestParam(required = false) boolean hotcheckbox,
                                @RequestParam(required = false) String namePicture,
                                Model model) throws Exception {

        service.saveOrUpdateDoorClass(classId, name, description,
                fireproofcheckbox, hotcheckbox, namePicture);

        List<DoorClass> list = service.getDoorClass();
        model.addAttribute("accountInfos", list);
        return "doorClassList";
    }

    @DeleteMapping(value = "/doorclass", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int deleteDoorclass(Model model, @RequestParam(required = false) String classId) throws Exception {

        service.deleteDoorClass(classId);

        return 1;
    }

    @GetMapping(value = "/doortypelist")
    public String getDoorTypeList(Model model, @RequestParam(required = false) String classId) throws Exception {

        List<DoorClass> DoorClassList = service.getDoorClass();

        model.addAttribute("classId", classId);
        model.addAttribute("doorClassList", DoorClassList);
        model.addAttribute("doorTypeList", service.getTypeFromListById(DoorClassList, classId));

        return "doorTypeList";
    }

    @GetMapping(value = "/doortype")
    public String getDoorType(@RequestParam(required = false) String typeId,
                              @RequestParam(required = false) String classId,
                              Model model) throws Exception {

        model.addAttribute("typeId", typeId);
        model.addAttribute("classId", classId);
        model.addAttribute("type", service.getDoorType(typeId));

        return "doorType";
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


    @GetMapping(value = "/creattemplate")
    public String getTemplate(@RequestParam(required = false) String typeId,
                              @RequestParam(required = false) String classId,
                              Model model) throws Exception {

        return "creatTemplate";
    }

    @GetMapping(value = "/specification")
    public String getSpecificationPage(Model model) throws Exception {
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


    @GetMapping(value = "/metal")
    public String getMetal(Model model) throws Exception {

        List<Metal> list = service.getMetals();
        model.addAttribute("accountInfos", list);
        return "settingPage_metal";
    }


    @GetMapping(value = "/getTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorTemplate getTemplate(Model model,
                                    @RequestParam String idDoorType) throws Exception {

        return service.getDoorTemplate(idDoorType);
    }

    @PostMapping(value = "/saveTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveTemplate(Model model, @RequestBody RestrictionOfSelectionFields templateJSON) throws Exception {

        service.saveDoorTemplate(templateJSON);

    }


    @GetMapping(value = "/availableGroups")
    @ResponseBody
    public List<DoorClass> getAavailableDoorClass(Model model) throws Exception {

        return service.getDoorClass();

    }

}
