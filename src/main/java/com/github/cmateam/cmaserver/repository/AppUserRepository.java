package com.github.cmateam.cmaserver.repository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.AppUserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUserEntity, UUID> {
    AppUserEntity findByUserName(String userName);
}
