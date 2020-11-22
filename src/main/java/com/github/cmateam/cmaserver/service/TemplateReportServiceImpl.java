package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.TemplateReportDTO;
import com.github.cmateam.cmaserver.entity.GroupTemplateReportEntity;
import com.github.cmateam.cmaserver.entity.TemplateReportEntity;
import com.github.cmateam.cmaserver.repository.GroupTemplateReportRepository;
import com.github.cmateam.cmaserver.repository.TemplateReportRepository;

@Service
public class TemplateReportServiceImpl {
	private TemplateReportRepository templateReportRepository;
	private GroupTemplateReportRepository groupTemplateReportRepository;

	@Autowired
	public TemplateReportServiceImpl(TemplateReportRepository templateReportRepository,
			GroupTemplateReportRepository groupTemplateReportRepository) {
		this.templateReportRepository = templateReportRepository;
		this.groupTemplateReportRepository = groupTemplateReportRepository;
	}

	public List<TemplateReportDTO> getAllTemplateReport() {
		List<TemplateReportEntity> lstTemplate = templateReportRepository.getAllTemplateStatus();
		List<TemplateReportDTO> lstTemplateReportDTOs = new ArrayList<>();
		for (TemplateReportEntity tre : lstTemplate) {
			GroupTemplateReportEntity group = tre.getGroupTemplateReportByGroupTemplateReportId();
			TemplateReportDTO treDto = new TemplateReportDTO();
			treDto.setTemplateReportId(tre.getId());
			treDto.setTemplateName(tre.getTemplateName());
			treDto.setCreatedAt(tre.getCreatedAt());
			treDto.setUpdatedAt(tre.getUpdatedAt());
			if (group != null) {
				treDto.setGroupId(group.getId());
			}
			lstTemplateReportDTOs.add(treDto);
		}
		return lstTemplateReportDTOs;
	}

	public TemplateReportDTO getTemplateReport(UUID id) {
		TemplateReportDTO ret = new TemplateReportDTO();
		TemplateReportEntity e = templateReportRepository.getOne(id);
		if (e == null) {
			return ret;
		}
		GroupTemplateReportEntity group = e.getGroupTemplateReportByGroupTemplateReportId();
		ret.setTemplateReportId(e.getId());
		ret.setTemplateName(e.getTemplateName());
		ret.setHtmlReport(e.getHtmlReport());
		ret.setCreatedAt(e.getCreatedAt());
		ret.setUpdatedAt(e.getUpdatedAt());
		if (group != null) {
			ret.setGroupId(group.getId());
		}
		return ret;
	}

	public void deleteTemplateReport(UUID id) {
		TemplateReportEntity e = templateReportRepository.getOne(id);
		if (e == null) {
			return;
		}
		templateReportRepository.delete(e);
	}

	public TemplateReportDTO saveTemplateReport(UUID id, String templateName, String htmlReport, UUID groupId) {
		TemplateReportDTO ret = new TemplateReportDTO();
		TemplateReportEntity e = new TemplateReportEntity();
		GroupTemplateReportEntity group = null;
		if (groupId != null) {
			group = groupTemplateReportRepository.getOne(groupId);
		}
		boolean isExist = false;
		if (id != null) {
			e = templateReportRepository.getOne(id);
			if (e != null) {
				isExist = true;
			}
		}
		if (isExist) {
			e.setUpdatedAt(new Date());
			e.setTemplateName(templateName);
			e.setHtmlReport(htmlReport);
		} else {
			e.setTemplateName(templateName);
			e.setHtmlReport(htmlReport);
			e.setStatus(1);
			e.setCreatedAt(new Date());
			e.setUpdatedAt(new Date());
		}
		if (group != null) {
			e.setGroupTemplateReportByGroupTemplateReportId(group);
		}
		e = templateReportRepository.save(e);

		ret.setTemplateReportId(e.getId());
		ret.setTemplateName(e.getTemplateName());
		ret.setHtmlReport(e.getHtmlReport());
		ret.setCreatedAt(e.getCreatedAt());
		ret.setUpdatedAt(e.getUpdatedAt());
		if (group != null) {
			ret.setGroupId(group.getId());
		}
		return ret;
	}
}
