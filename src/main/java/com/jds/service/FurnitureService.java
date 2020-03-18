package com.jds.service;

import com.jds.dao.FurnitureRepository;
import com.jds.entity.DoorFurniture;
import com.jds.model.modelEnum.TypeOfFurniture;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository repository;

    public List<DoorFurniture> getFurnitureList() {
        return repository.getFurniture();
    }

    public DoorFurniture getDoorFurniture(@NonNull String id) {
        if ("0".equals(id)){
            return new DoorFurniture();
        }

        DoorFurniture furniture = repository.getFurnitureById(Integer.parseInt(id));
        return furniture.clearNonSerializingFields();
    }

    public List<TypeOfFurniture> getTypesFurniture() {
        return new ArrayList<TypeOfFurniture>(Arrays.asList(TypeOfFurniture.values()));
    }

    public String deleteFurniture(@NonNull String id) {
        DoorFurniture furniture = repository.getFurnitureById(Integer.parseInt(id));
        return repository.deleteFurniture(furniture);
    }

    public String saveFurniture(@NonNull DoorFurniture furniture) {

        return repository.saveFurniture(furniture);
    }
}
