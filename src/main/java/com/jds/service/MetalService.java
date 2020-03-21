package com.jds.service;

import com.jds.dao.MainDAO;
import com.jds.dao.MetalRepository;
import com.jds.entity.Metal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetalService {

    @Autowired
    private MetalRepository dAO;

    public List<Metal> getMetals() {
        return dAO.getMetals();
    }

}
