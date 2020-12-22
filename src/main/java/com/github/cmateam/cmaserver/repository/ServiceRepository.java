package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ServiceEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {

	@Query("FROM ServiceEntity s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode='CLINICAL_EXAMINATION'")
	ServiceEntity findServiceClinicExam();

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where gs.groupServiceCode = ?1 and s.status = 1 order by s.createdAt DESC")
	List<ServiceEntity> getAllServiceByGroupServiceCode(String groupServiceCode);

	@Query("select s from ServiceEntity s where s.status = 1 order by s.createdAt DESC")
	List<ServiceEntity> getAllService();

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where gs.groupServiceCode = ?1 and lower(s.serviceNameSearch) like ?2 and s.status = 1 order by s.createdAt DESC")
	List<ServiceEntity> searchNameServiceByInGroup(String groupServiceCode, String serviceName);

	@Query("select s from ServiceEntity s where s.serviceNameSearch like ?1 and s.status = 1")
	List<ServiceEntity> autoSearchByName(String serviceName, Pageable pageable);

	@Query("select s from ServiceEntity s where s.status <> 0 order by s.serviceNameSearch ASC")
	List<ServiceEntity> getAllServicePagging(Pageable pageable);

	@Query("select count(s.id) from ServiceEntity s where s.status <> 0")
	Integer countAllService();

	@Query("select s.serviceName from ServiceEntity s where s.status <> 0 order by s.createdAt DESC")
	List<String> getAllServiceName();

	@Query("select s from ServiceEntity s where s.serviceNameSearch like ?1 and s.status <> 0 order by s.serviceNameSearch ASC")
	List<ServiceEntity> getAllServiceByNamePagging(String serviceNameSearch, Pageable pageable);

	@Query("select count(s.id) from ServiceEntity s where s.serviceNameSearch like ?1 and s.status <> 0")
	Integer countAllServiceByNamePagging(String serviceNameSearch);

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where s.serviceNameSearch like ?1 and gs.id = ?2 and s.status <> 0 order by s.serviceNameSearch ASC")
	List<ServiceEntity> getAllServiceByNameAndIdGroupPagging(String serviceNameSearch, UUID groupId, Pageable pageable);

	@Query("select count(s.id) from ServiceEntity s join s.groupServiceByGroupServiceId gs where s.serviceNameSearch like ?1 and gs.id = ?2 and s.status <> 0")
	Integer countAllServiceByNameAndIdGroupPagging(String serviceNameSearch, UUID groupId);

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs join gs.staffsById staff where lower(s.serviceNameSearch) like ?1 and staff.id=?2 and s.status = 1 and gs.groupServiceCode <> 'CLINICAL_EXAMINATION' order by s.createdAt DESC")
	List<ServiceEntity> getServiceByStaffId(String serviceName, UUID id);

	@Query("select s from ServiceEntity s join s.groupServiceByGroupServiceId gs where lower(s.serviceNameSearch) like ?1 and s.status = 1 and gs.groupServiceCode <> 'CLINICAL_EXAMINATION' order by s.createdAt DESC")
	List<ServiceEntity> getServiceByManager(String serviceName);
}
