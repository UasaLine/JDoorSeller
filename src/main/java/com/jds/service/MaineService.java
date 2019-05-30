package com.jds.service;

import com.jds.dao.MainDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaineService {

    @Autowired
    private MainDAO mainDAO;

}
