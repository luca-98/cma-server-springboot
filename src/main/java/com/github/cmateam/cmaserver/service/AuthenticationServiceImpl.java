package com.github.cmateam.cmaserver.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.UserLoginDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.ManagerEntity;
import com.github.cmateam.cmaserver.entity.PermissionEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;
import com.github.cmateam.cmaserver.repository.PermissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {

	private AuthenticationManager authenticationManager;
	private AppUserRepository appUserRepository;
	private TokenAuthenticationService tokenService;
	private PermissionRepository permissionRepository;

	@Autowired
	public AuthenticationServiceImpl(AuthenticationManager authenticationManager, AppUserRepository appUserRepository,
			TokenAuthenticationService tokenService, PermissionRepository permissionRepository) {
		this.authenticationManager = authenticationManager;
		this.appUserRepository = appUserRepository;
		this.tokenService = tokenService;
		this.permissionRepository = permissionRepository;
	}

	public UserLoginDTO login(String userName, String password) {
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userName, password);
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			if (isAuthenticated(authentication)) {
				AppUserEntity appUserEntity = appUserRepository.findByUserName(userName);
				List<ManagerEntity> listManager = appUserEntity.getManagersById();
				List<StaffEntity> listStaff = appUserEntity.getStaffById();
				if (listManager.size() == 0 && listStaff.size() == 0) {
					return null;
				}
				UserGroupEntity userGroupEntity = appUserEntity.getUserGroupByUserGroupId();
				Collection<PermissionEntity> permissionEntities;
				if (userGroupEntity.getUserGroupCode().equals("ROLE_MANAGER")) {
					permissionEntities = permissionRepository.findAll();
				} else {
					permissionEntities = userGroupEntity.getPermissionsById();
				}
				List<String> listPermissionCode = new ArrayList<>();
				for (PermissionEntity p : permissionEntities) {
					listPermissionCode.add(p.getPermissionCode());
				}
				String fullName = null;
				String roomName = null;
				UUID staffId = null;
				UUID roomId = null;

				if (listManager.size() != 0) {
					fullName = listManager.get(0).getFullName();
					roomName = "Không xác định";
				} else {
					List<RoomServiceEntity> listRoomService = listStaff.get(0).getRoomServicesById();
					if (listRoomService.size() == 0) {
						roomName = "Không xác định";
						roomId = null;
					} else {
						roomName = listRoomService.get(0).getRoomName();
						roomId = listRoomService.get(0).getId();
					}
					fullName = listStaff.get(0).getFullName();
					staffId = listStaff.get(0).getId();
				}

				UserLoginDTO userLoginDTO = new UserLoginDTO();
				userLoginDTO.setToken(tokenService.generateToken(userName));
				userLoginDTO.setUserName(userName);
				userLoginDTO.setFullName(fullName);
				userLoginDTO.setRoomName(roomName);
				userLoginDTO.setUserGroupCode(userGroupEntity.getUserGroupCode());
				userLoginDTO.setPermissionCode(listPermissionCode);
				userLoginDTO.setStaffId(staffId);
				userLoginDTO.setRoomId(roomId);

				return userLoginDTO;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}

	public ResponseEntity<?> changePassword(String oldPassword, String newPassword, Principal principal) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String username = principal.getName();

		AppUserEntity appUserEntity = appUserRepository.findByUserName(username);
		if (!passwordEncoder.matches(oldPassword, appUserEntity.getEncryptedPassword())) {
			return ResponseEntity.status(HttpStatus.OK).body(false);
		}
		String newEncryptPassword = passwordEncoder.encode(newPassword);
		appUserEntity.setEncryptedPassword(newEncryptPassword);
		appUserRepository.save(appUserEntity);
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
}
