package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.GroupTemplateReportEntity;

public interface GroupTemplateReportRepository extends JpaRepository<GroupTemplateReportEntity, UUID>{

    @Query("FROM GroupTemplateReportEntity g ORDER BY g.groupTemplateName ASC")
    List<GroupTemplateReportEntity> getAll();
}
