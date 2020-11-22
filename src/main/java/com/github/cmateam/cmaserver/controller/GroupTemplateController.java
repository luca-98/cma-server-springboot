package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.GroupTemplateDTO;
import com.github.cmateam.cmaserver.service.GroupTemplateServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("group-template")
public class GroupTemplateController {

    private GroupTemplateServiceImpl groupTemplateServiceImpl;

    @Autowired
    public GroupTemplateController(GroupTemplateServiceImpl groupTemplateServiceImpl) {
        this.groupTemplateServiceImpl = groupTemplateServiceImpl;
    }

    @GetMapping
    public List<GroupTemplateDTO> getAll() {
        return groupTemplateServiceImpl.getAll();
    }

    @PostMapping
    public boolean addNewGroup(@RequestParam String name) {
        return groupTemplateServiceImpl.addGroup(name);
    }

    @PutMapping(value = "{id}")
    public boolean editGroup(@PathVariable UUID id, @RequestParam String name) {
        return groupTemplateServiceImpl.updateGroup(id, name);
    }

    @DeleteMapping(value = "{id}")
    public void editGroup(@PathVariable UUID id) {
        groupTemplateServiceImpl.deleteGroup(id);
    }
}
