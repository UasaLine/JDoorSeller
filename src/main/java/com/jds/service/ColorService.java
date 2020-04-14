package com.jds.service;

import com.jds.dao.repository.ColorRepository;
import com.jds.dao.entity.ImageEntity;
import com.jds.model.image.TypeOfDoorColor;
import com.jds.model.image.TypeOfImage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.EnumSet;
import java.util.List;

@Service
public class ColorService {

    @Autowired
    private ColorRepository dAO;

    public List<ImageEntity> getColors() {
        return dAO.getImages();
    }

    public ImageEntity getColor(@NonNull String id) {

        if("0".equals(id)){
            return new ImageEntity();
        }

        return dAO.getColorById(Integer.parseInt(id)).clearNonSerializingFields();
    }

    public String saveColor(@NonNull ImageEntity colors) {
        if (colors.getPicturePath()!=null){
            colors.setPicturePath("images/Door/AColor1/" + colors.getPicturePath() + ".jpg");
        }
        dAO.saveColors(colors);
        return "ok";
    }

    public String deleteColor(@NonNull String id) {
        ImageEntity color = getColor(id);
        return dAO.deleteColor(color);
    }

    public EnumSet<TypeOfImage> getImageTypeList() {
        return EnumSet.allOf( TypeOfImage.class );
    }

    public EnumSet<TypeOfDoorColor> getImageTypeDoorColor() {
        return EnumSet.allOf( TypeOfDoorColor.class );
    }
}
