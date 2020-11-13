package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.cmateam.cmaserver.entity.RoomServiceEntity;

public interface RoomServiceRepository extends JpaRepository<RoomServiceEntity, UUID> {
	List<RoomServiceEntity> findAll();

	@Query("FROM RoomServiceEntity r JOIN r.servicesByServiceId s WHERE s.id = ?1")
	List<RoomServiceEntity> findRoomByService(UUID serviceId);

	@Query("FROM RoomServiceEntity r JOIN r.servicesByServiceId s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode='CLINICAL_EXAMINATION'")
	List<RoomServiceEntity> findRoomServiceClinicalExam();

	@Query("Select distinct r FROM RoomServiceEntity r JOIN r.servicesByServiceId s JOIN s.groupServiceByGroupServiceId g WHERE g.groupServiceCode= ?1")
	List<RoomServiceEntity> findRoomServiceByGroupService(String groupServiceCode);
}
