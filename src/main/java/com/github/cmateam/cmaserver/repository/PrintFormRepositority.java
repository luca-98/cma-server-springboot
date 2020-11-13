package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.cmateam.cmaserver.entity.PrintFormEntity;

import java.util.List;
import java.util.UUID;

public interface PrintFormRepositority extends JpaRepository<PrintFormEntity, UUID> {

	List<PrintFormEntity> findAll();

	@Query("from PrintFormEntity p where p.id = ?1")
	PrintFormEntity findPrintFormById(UUID id);
}
