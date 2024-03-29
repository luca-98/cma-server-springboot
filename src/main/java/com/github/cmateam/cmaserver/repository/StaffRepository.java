package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.cmateam.cmaserver.entity.StaffEntity;

public interface StaffRepository extends JpaRepository<StaffEntity, UUID> {
	@Query("from StaffEntity s where s.status <> 0 order by s.updatedAt")
	List<StaffEntity> findAll();

	@Query("select s from StaffEntity s join s.groupServicesById gs where gs.status = 1 and gs.groupServiceCode = ?1 ")
	List<StaffEntity> getAllStaffByGroupService(String groupServiceCode);

	@Query("FROM StaffEntity s JOIN s.groupServicesById groupService JOIN groupService.servicesById service WHERE s.status=1 AND service.id=?1 AND service.status=1")
	List<StaffEntity> getAllStaffByService(UUID serviceId);

	@Query("SELECT g.groupServiceCode FROM StaffEntity s JOIN s.groupServicesById g WHERE s.id=?1 AND s.status=1")
	List<String> getGroupServiceCodeByStaff(UUID staffId);

	@Query("SELECT g.groupServiceCode FROM StaffEntity s JOIN s.groupServicesById g WHERE s.status=1")
	List<String> getAllGroupServiceCode();
}
