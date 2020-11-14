package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, UUID> {
	@Query("select m from MaterialEntity m where m.materialName like ?1")
	MaterialEntity getMaterialByName(String materialName);

}
