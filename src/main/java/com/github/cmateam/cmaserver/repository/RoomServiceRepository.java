package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.cmateam.cmaserver.entity.RoomServiceEntity;

public interface RoomServiceRepository extends JpaRepository<RoomServiceEntity, UUID> {
	@Query("From RoomServiceEntity r where r.status <> 0")
	List<RoomServiceEntity> findAll();

	@Query("FROM RoomServiceEntity r JOIN r.servicesByServiceId s WHERE s.id = ?1 and r.status <> 0")
	List<RoomServiceEntity> findRoomByService(UUID serviceId);

	@Query("FROM RoomServiceEntity r JOIN r.servicesByServiceId s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode='CLINICAL_EXAMINATION' and r.status <> 0")
	List<RoomServiceEntity> findRoomServiceClinicalExam();

	@Query("SELECT distinct r FROM RoomServiceEntity r JOIN r.servicesByServiceId s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode= ?1 and r.status <> 0 and s.status <> 0 and g.status <> 0")
	List<RoomServiceEntity> findRoomServiceByGroupService(String groupServiceCode);
}
