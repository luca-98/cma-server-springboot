package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.GroupMaterialEntity;

public interface GroupMaterialRepository extends JpaRepository<GroupMaterialEntity, UUID> {
	@Query("select gm from GroupMaterialEntity gm where gm.status <> 0")
	List<GroupMaterialEntity> getAllGroupMaterial();
}
