package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.PermissionEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepository appUserRepository;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUserEntity appUserEntity = appUserRepository.findByUserName(userName);
        if (appUserEntity == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        UserGroupEntity currentGroup = appUserEntity.getUserGroupByUserGroupId();
        String userGroupName = currentGroup.getUserGroupName();

        List<GrantedAuthority> grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority(userGroupName));

        Collection<PermissionEntity> listPermission = currentGroup.getPermissionsById();
        if (listPermission != null) {
            for (PermissionEntity ele : listPermission) {
                grantList.add(new SimpleGrantedAuthority(ele.getPermissionCode()));
            }
        }

        return new User(appUserEntity.getUserName(), appUserEntity.getEncryptedPassword(), grantList);
    }
}
