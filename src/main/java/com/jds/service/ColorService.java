package com.jds.service;

import com.jds.dao.ColorRepository;
import com.jds.entity.DoorColors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    public List<DoorColors> getColors() {
        return dAO.getColors();
    }

    public DoorColors getColor(@NonNull String id) {

        if("0".equals(id)){
            return new DoorColors();
        }

        return dAO.getColorById(Integer.parseInt(id));
    }

    public String saveColor(@NonNull DoorColors colors) {
        colors.setPicturePath("images/Door/AColor1/" + colors.getPicturePath() + ".jpg");
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {
        DoorColors color = getColor(id);
        return dAO.deleteColor(color);
    }

}
