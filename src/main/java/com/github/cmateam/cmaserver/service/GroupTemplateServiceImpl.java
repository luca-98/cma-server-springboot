package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.GroupTemplateDTO;
import com.github.cmateam.cmaserver.entity.GroupTemplateReportEntity;
import com.github.cmateam.cmaserver.entity.TemplateReportEntity;
import com.github.cmateam.cmaserver.repository.GroupTemplateReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupTemplateServiceImpl {

    private GroupTemplateReportRepository groupTemplateReportRepository;

    @Autowired
    public GroupTemplateServiceImpl(GroupTemplateReportRepository groupTemplateReportRepository) {
        this.groupTemplateReportRepository = groupTemplateReportRepository;
    }

    public List<GroupTemplateDTO> getAll() {
        List<GroupTemplateDTO> ret = new ArrayList<>();
        List<GroupTemplateReportEntity> listE = groupTemplateReportRepository.getAll();
        for (GroupTemplateReportEntity e : listE) {
            GroupTemplateDTO dto = new GroupTemplateDTO();
            dto.setId(e.getId());
            dto.setGroupTemplateName(e.getGroupTemplateName());
            dto.setCreatedAt(e.getCreatedAt());
            dto.setUpdatedAt(e.getUpdatedAt());
            dto.setStatus(e.getStatus());
            List<UUID> listTemplateId = new ArrayList<>();
            List<TemplateReportEntity> lisTemplate = e.getTemplateReportsById();
            for (TemplateReportEntity t : lisTemplate) {
                listTemplateId.add(t.getId());
            }
            dto.setListTemplateId(listTemplateId);
            ret.add(dto);
        }
        return ret;
    }

    public boolean addGroup(String name) {
        GroupTemplateReportEntity e = new GroupTemplateReportEntity();
        e.setGroupTemplateName(name);
        e.setStatus(1);
        e.setCreatedAt(new Date());
        e.setUpdatedAt(new Date());
        groupTemplateReportRepository.save(e);
        return true;
    }

    public boolean updateGroup(UUID id, String name) {
        GroupTemplateReportEntity e = groupTemplateReportRepository.getOne(id);
        if (e == null) {
            return false;
        }
        e.setGroupTemplateName(name);
        e.setUpdatedAt(new Date());
        groupTemplateReportRepository.save(e);
        return true;
    }

    public void deleteGroup(UUID id) {
        GroupTemplateReportEntity e = groupTemplateReportRepository.getOne(id);
        if (e == null) {
            return;
        }
        groupTemplateReportRepository.delete(e);
    }
}
