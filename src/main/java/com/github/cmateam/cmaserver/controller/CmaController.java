package com.github.cmateam.cmaserver.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CmaController {

    BuildProperties buildProperties;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public CmaController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping
    public Map<String, Object> Test() {
        Map<String, Object> ret = new LinkedHashMap<>();
        ret.put("app", appName);
        ret.put("name", buildProperties.getName());
        ret.put("group", buildProperties.getGroup());
        ret.put("version", buildProperties.getVersion());
        ret.put("status", "running");
        return ret;
    }
}
