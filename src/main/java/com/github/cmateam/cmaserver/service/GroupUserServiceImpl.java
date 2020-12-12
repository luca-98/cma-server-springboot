package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.GroupUserDTO;
import com.github.cmateam.cmaserver.dto.PermissionDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.PermissionEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.PermissionRepository;
import com.github.cmateam.cmaserver.repository.UserGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupUserServiceImpl {

    private UserGroupRepository groupUserRepository;
    private PermissionRepository permissionRepository;
    private WebSocketService webSocketService;

    @Autowired
    public GroupUserServiceImpl(UserGroupRepository groupUserRepository, PermissionRepository permissionRepository,
            WebSocketService webSocketService) {
        this.groupUserRepository = groupUserRepository;
        this.permissionRepository = permissionRepository;
        this.webSocketService = webSocketService;
    }

    public List<GroupUserDTO> getAll() {
        List<GroupUserDTO> ret = new ArrayList<>();
        List<UserGroupEntity> listGroupUser = groupUserRepository.findAll();
        for (UserGroupEntity u : listGroupUser) {
            if (u.getStatus() == 0) {
                continue;
            }
            GroupUserDTO g = new GroupUserDTO();
            g.setId(u.getId());
            g.setUserGroupCode(u.getUserGroupCode());
            g.setUserGroupName(u.getUserGroupName());
            g.setStatus(u.getStatus());
            g.setUpdatedAt(u.getUpdatedAt());
            g.setCreatedAt(u.getCreatedAt());
            ret.add(g);
        }
        return ret;
    }

    public List<UUID> getUserIdFromGroupUser(UUID id) {
        List<UUID> listUserId = new ArrayList<>();
        UserGroupEntity u = groupUserRepository.getOne(id);
        if (u != null) {
            List<AppUserEntity> listUser = u.getAppUsersById();
            if (listUser != null) {
                for (AppUserEntity a : listUser) {
                    listUserId.add(a.getId());
                }
            }
        }
        return listUserId;
    }

    public void deleteUser(UUID id) {
        UserGroupEntity u = groupUserRepository.getOne(id);
        if (u != null) {
            u.setStatus(0);
            groupUserRepository.save(u);
        }
    }

    public List<PermissionDTO> getAllPermission() {
        List<PermissionDTO> ret = new ArrayList<>();
        List<PermissionEntity> listP = permissionRepository.findAll();
        for (PermissionEntity p : listP) {
            PermissionDTO dto = new PermissionDTO();
            dto.setId(p.getId());
            dto.setPermissionCode(p.getPermissionCode());
            dto.setPermissionName(p.getPermissionName());
            ret.add(dto);
        }
        return ret;
    }

    public List<PermissionDTO> getAllPermissionByGroupUser(UUID groupUserId) {
        List<PermissionDTO> ret = new ArrayList<>();
        UserGroupEntity userGroup = groupUserRepository.getOne(groupUserId);
        if (userGroup == null) {
            return ret;
        }
        List<PermissionEntity> listP = userGroup.getPermissionsById();
        for (PermissionEntity p : listP) {
            PermissionDTO dto = new PermissionDTO();
            dto.setId(p.getId());
            dto.setPermissionCode(p.getPermissionCode());
            dto.setPermissionName(p.getPermissionName());
            ret.add(dto);
        }
        return ret;
    }

    public GroupUserDTO addNewGroupUser(String groupName, List<UUID> listPermission) {
        GroupUserDTO ret = new GroupUserDTO();

        List<PermissionEntity> listP = new ArrayList<>();
        for (UUID id : listPermission) {
            PermissionEntity permissionEntity = permissionRepository.getOne(id);
            if (permissionEntity != null) {
                listP.add(permissionEntity);
            }
        }

        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setUserGroupName(groupName);
        userGroupEntity.setUserGroupCode("OTHER");
        userGroupEntity.setStatus(1);
        userGroupEntity.setCreatedAt(new Date());
        userGroupEntity.setUpdatedAt(new Date());
        userGroupEntity.setPermissionsById(listP);
        userGroupEntity = groupUserRepository.save(userGroupEntity);

        ret.setId(userGroupEntity.getId());
        ret.setUserGroupCode(userGroupEntity.getUserGroupCode());
        ret.setUserGroupName(userGroupEntity.getUserGroupName());
        ret.setStatus(userGroupEntity.getStatus());
        ret.setUpdatedAt(userGroupEntity.getUpdatedAt());
        ret.setCreatedAt(userGroupEntity.getCreatedAt());
        return ret;
    }

    public GroupUserDTO updateGroupUser(UUID id, String groupName, List<UUID> listPermission) {
        GroupUserDTO ret = new GroupUserDTO();

        List<PermissionEntity> listP = new ArrayList<>();
        for (UUID perId : listPermission) {
            PermissionEntity permissionEntity = permissionRepository.getOne(perId);
            if (permissionEntity != null) {
                listP.add(permissionEntity);
            }
        }

        UserGroupEntity userGroupEntity = groupUserRepository.getOne(id);
        if (userGroupEntity == null) {
            return ret;
        }
        userGroupEntity.setUserGroupName(groupName);
        userGroupEntity.setUserGroupCode("OTHER");
        userGroupEntity.setStatus(1);
        userGroupEntity.setUpdatedAt(new Date());
        userGroupEntity.setPermissionsById(listP);
        userGroupEntity = groupUserRepository.save(userGroupEntity);

        ret.setId(userGroupEntity.getId());
        ret.setUserGroupCode(userGroupEntity.getUserGroupCode());
        ret.setUserGroupName(userGroupEntity.getUserGroupName());
        ret.setStatus(userGroupEntity.getStatus());
        ret.setUpdatedAt(userGroupEntity.getUpdatedAt());
        ret.setCreatedAt(userGroupEntity.getCreatedAt());
        webSocketService.updatePermission();
        return ret;
    }
}
