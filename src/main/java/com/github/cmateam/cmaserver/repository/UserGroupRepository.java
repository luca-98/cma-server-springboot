package com.github.cmateam.cmaserver.repository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.UserGroupEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity, UUID> {
    UserGroupEntity findByUserGroupName(String userGroupName);
}
