package com.jds.controller;

import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.*;
import com.jds.model.backResponse.ResponseModel;
import com.jds.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.jds.model.image.TypeOfImage.*;

@Controller
public class ColorController {
    @Autowired
    private ColorService service;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/color")
    public String getMetalListPage(Model model) throws Exception {
        List<ImageEntity> list = service.getColors();

        list = list.stream()
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList());

        model.addAttribute("List", list);
        return "colorList";
    }

    @GetMapping(value = "color/doorColors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ImageEntity> getDoorColorsList() {
        List<ImageEntity> list = service.getColorsType(DOOR_COLOR);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).clearNonSerializingFields();
        }
        return list;
    }

    @GetMapping(value = "/color/{id}")
    public String getMetalPage(@PathVariable String id) throws Exception {

        return "color";
    }

    @GetMapping(value = "/color/item/{id}")
    @ResponseBody
    public ImageEntity getColor(@PathVariable String id) {

        return service.getColor(id);

    }

    @PutMapping(value = "/color/item")
    @ResponseBody
    public ResponseModel saveColor(@RequestBody ImageEntity color) {

        return new ResponseModel(service.saveColor(color));

    }

    @GetMapping(value = "/color/image-file-list/{imageType}")
    @ResponseBody
    public List<ColorPicture> getColorList(@PathVariable String imageType) {

        return service.getImageFileList(imageType);

    }

    @DeleteMapping(value = "/color/{id}")
    @ResponseBody
    public ResponseModel deleteColor(@PathVariable String id) {

        return new ResponseModel(service.deleteColor(id));

    }

    @GetMapping(value = "/image/types")
    @ResponseBody
    public EnumSet<TypeOfImage> getImageTypeList() {

        return service.getImageTypeList();

    }

    @GetMapping(value = "/doorType/{doorTypeId}/colors/types/buttons")
    @ResponseBody
    public List<TypeView> getImageTypeDoorColor(@PathVariable int doorTypeId) {

        return service.getImageTypeDoorColor();

    }

    @GetMapping(value = "/colors/shield-designs/types/buttons")
    @ResponseBody
    public List<TypeView> getAllTypeShieldDesign() {

        return service.getImageTypeShieldDesign();

    }

    @GetMapping(value = "/color/shieldDesign")
    @ResponseBody
    public List<ImageEntity> getColorShieldDesign() {

        List<ImageEntity> list = service.getColorsTypeIfContainsGlassTrue(SHIELD_DESIGN);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).clearNonSerializingFields();
        }
        return list;
    }

    @GetMapping(value = "/doorType/{doorTypeId}/{type}/colors")
    @ResponseBody
    public List<ImageEntity> getColorByType(@PathVariable int doorTypeId, @PathVariable TypeOfDoorColor type) {

        return service.getColorsByType(doorTypeId, type);
    }

    @GetMapping(value = "/doorType/{doorTypeId}/{type}/shield-design")
    @ResponseBody
    public List<ImageEntity> getShieldDesignByType(@PathVariable int doorTypeId, @PathVariable TypeOfShieldDesign type) {

        return service.getShieldDesignByType(doorTypeId, type);
    }

    @GetMapping(value = "/color/types/color")
    @ResponseBody
    public List<TypeView> getAllTypeDoorColor() {

        return service.getImageTypeDoorColor();

    }


}
