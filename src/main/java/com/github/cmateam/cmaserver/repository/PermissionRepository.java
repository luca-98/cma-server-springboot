package com.github.cmateam.cmaserver.repository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PermissionEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    
}
