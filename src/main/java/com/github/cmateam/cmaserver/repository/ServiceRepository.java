package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ServiceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {

	@Query("FROM ServiceEntity s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode='CLINICAL_EXAMINATION'")
	ServiceEntity findServiceClinicExam();

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where gs.groupServiceCode = ?1 and s.status = 1")
	List<ServiceEntity> getAllServiceByGroupServiceCode(String groupServiceCode);

	@Query("select s from ServiceEntity s where s.status = 1")
	List<ServiceEntity> getAllService();

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where gs.groupServiceCode = ?1 and lower(s.serviceNameSearch) like ?2 and s.status = 1")
	List<ServiceEntity> searchNameServiceByInGroup(String groupServiceCode, String serviceName);
}
