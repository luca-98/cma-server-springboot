package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ServiceReportEntity;

public interface ServiceReportRepository extends JpaRepository<ServiceReportEntity, UUID> {

    @Query("SELECT COUNT(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff WHERE staff.id=?1 AND staff.status=1 AND sr.status=1 AND date_trunc('day', sr.createdAt)=date_trunc('day', now())")
    Integer countWaitByStaff(UUID staffId);
}
