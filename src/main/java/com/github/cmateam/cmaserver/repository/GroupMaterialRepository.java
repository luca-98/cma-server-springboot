package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.GroupMaterialEntity;

public interface GroupMaterialRepository extends JpaRepository<GroupMaterialEntity, UUID> {

}
