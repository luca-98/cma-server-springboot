package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.MedicineSaleDetailEntity;

public interface MedicineSaleDetailRepository extends JpaRepository<MedicineSaleDetailEntity, UUID> {

}
