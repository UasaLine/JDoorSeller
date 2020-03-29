package com.jds.service;

import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ColorEntity;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    public List<ColorEntity> getColors() {
        return dAO.getColors();
    }

    public ColorEntity getColor(@NonNull String id) {

        if("0".equals(id)){
            return new ColorEntity();
        }

        return dAO.getColorById(Integer.parseInt(id));
    }

    public String saveColor(@NonNull ColorEntity colors) {
        colors.setPicturePath("images/Door/AColor1/" + colors.getPicturePath() + ".jpg");
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {
        ColorEntity color = getColor(id);
        return dAO.deleteColor(color);
    }

}
