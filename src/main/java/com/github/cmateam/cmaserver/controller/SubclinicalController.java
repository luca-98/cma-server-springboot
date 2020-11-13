package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.service.SubclinicalServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("subclinical")
public class SubclinicalController {

    private SubclinicalServiceImpl subclinicalServiceImpl;

    @Autowired
    public SubclinicalController(SubclinicalServiceImpl subclinicalServiceImpl) {
        this.subclinicalServiceImpl = subclinicalServiceImpl;
    }

    @GetMapping(value = "/get-staff-min-service")
    public StaffDTO getStaffMinByService(@RequestParam UUID serviceId) {
        return subclinicalServiceImpl.getStaffMinByService(serviceId);
    }

}
