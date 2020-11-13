package com.github.cmateam.cmaserver.service;

import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.PermissionEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptorImpl implements ChannelInterceptor {

    private AppUserRepository appUserRepository;
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public AuthChannelInterceptorImpl(AppUserRepository appUserRepository,
            TokenAuthenticationService tokenAuthenticationService) {
        this.appUserRepository = appUserRepository;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null) {
                throw new UsernameNotFoundException("User not found.");
            }

            String username = tokenAuthenticationService.getUsernameFromJWT(token);
            if (username == null) {
                throw new UsernameNotFoundException("User not found.");
            }

            AppUserEntity userInfoOnDB = appUserRepository.findByUserName(username);
            if (userInfoOnDB == null) {
                throw new UsernameNotFoundException("User not found.");
            }

            UserGroupEntity currentGroup = userInfoOnDB.getUserGroupByUserGroupId();
            String userGroupName = currentGroup.getUserGroupName();
            List<GrantedAuthority> grantList = new ArrayList<>();
            grantList.add(new SimpleGrantedAuthority(userGroupName));
            Collection<PermissionEntity> listPermission = currentGroup.getPermissionsById();
            if (listPermission != null) {
                for (PermissionEntity ele : listPermission) {
                    grantList.add(new SimpleGrantedAuthority(ele.getPermissionCode()));
                }
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userInfoOnDB.getUserName(), null, grantList);
            accessor.setUser(authentication);
        }
        return message;
    }
}
