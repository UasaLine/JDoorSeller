package com.jds.service;

import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ImageEntity;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    public List<ImageEntity> getColors() {
        return dAO.getDoorColors();
    }

    public ImageEntity getColor(@NonNull String id) {

        if("0".equals(id)){
            return new ImageEntity();
        }

        return dAO.getColorById(Integer.parseInt(id));
    }

    public String saveColor(@NonNull ImageEntity colors) {
        colors.setPicturePath("images/Door/AColor1/" + colors.getPicturePath() + ".jpg");
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {
        ImageEntity color = getColor(id);
        return dAO.deleteColor(color);
    }

}
