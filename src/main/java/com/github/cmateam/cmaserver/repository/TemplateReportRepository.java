package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.TemplateReportEntity;

public interface TemplateReportRepository extends JpaRepository<TemplateReportEntity, UUID> {
	@Query("from TemplateReportEntity tr where tr.status <> 0 ORDER BY tr.templateName ASC")
	List<TemplateReportEntity> getAllTemplateStatus();
}
