package com.jds.service;

import com.jds.dao.entity.GlassPositionEntity;
import com.jds.dao.repository.GlassPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlassPositionService {
    @Autowired
    private GlassPositionRepository repository;

    public GlassPositionEntity getById(int id) {
        return repository.getById(id);
    }

    public List<GlassPositionEntity> get() {
        return repository.get();
    }

    public GlassPositionEntity save(GlassPositionEntity glassPositionEntity) {
        return repository.save(glassPositionEntity);
    }

    public String delete(int id) {
        return repository.delete(repository.getById(id));
    }
}
