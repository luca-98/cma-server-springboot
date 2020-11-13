package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import com.github.cmateam.cmaserver.service.OrdinalNumberServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordinal-number")
public class OrdinalNumberController {

    private OrdinalNumberServiceImpl ordinalNumberServiceImpl;

    @Autowired
    public OrdinalNumberController(OrdinalNumberServiceImpl ordinalNumberServiceImpl) {
        this.ordinalNumberServiceImpl = ordinalNumberServiceImpl;
    }

    @GetMapping("/get-ordinal-number")
    public Short getOrdinalNumber(@RequestParam("roomServiceId") UUID roomServiceId) {
        return ordinalNumberServiceImpl.getOrdinalByRoom(roomServiceId);
    }
}
