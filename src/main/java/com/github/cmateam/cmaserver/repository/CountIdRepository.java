package com.github.cmateam.cmaserver.repository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.CountIdEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountIdRepository extends JpaRepository<CountIdEntity, UUID> {

    CountIdEntity findByCountName(String countName);
}
