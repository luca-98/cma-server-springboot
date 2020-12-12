package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.GroupServiceEntity;

public interface GroupServiceRepository extends JpaRepository<GroupServiceEntity, UUID> {

	@Query("select gs from GroupServiceEntity gs where gs.status = 1")
	List<GroupServiceEntity> findAllWithStatusActive();

	@Query("select gs.id from GroupServiceEntity gs where gs.status = 1 and gs.groupServiceCode != 'CLINICAL_EXAMINATION'")
	List<UUID> findAllWithStatusActiveAndWithoutCE();

	@Query("select gs.id from GroupServiceEntity gs where gs.status = 1")
	List<UUID> findAllWithStatusActiveUuids();

	@Query("select gs.id from StaffEntity s join s.groupServicesById gs where gs.status = 1 and s.id = ?1")
	List<UUID> findAllFollowStaffWithStatusActive(UUID id);

	@Query("select ug.userGroupCode from AppUserEntity au join au.userGroupByUserGroupId ug where au.userName like ?1")
	String checkGroupUser(String username);

	@Query("select s.id from StaffEntity s join s.appUserByAppUserId au where au.userName like ?1")
	UUID getStaffIDByUsername(String username);
	
	@Query("select gs.groupServiceName from GroupServiceEntity gs where gs.status <> 0 order by gs.createdAt DESC")
	List<String> getAllGroupServiceName();

}
