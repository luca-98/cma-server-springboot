package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.UserDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;
import com.github.cmateam.cmaserver.repository.UserGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private AppUserRepository appUserRepository;
    private StaffRepository staffRepository;
    private UserGroupRepository userGroupRepository;
    private VNCharacterUtils vNCharacterUtils;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, StaffRepository staffRepository,
            UserGroupRepository userGroupRepository, VNCharacterUtils vNCharacterUtils) {
        this.appUserRepository = appUserRepository;
        this.staffRepository = staffRepository;
        this.userGroupRepository = userGroupRepository;
        this.vNCharacterUtils = vNCharacterUtils;
    }

    public List<UserDTO> getAll() {
        List<UserDTO> ret = new ArrayList<>();
        List<StaffEntity> listStaff = staffRepository.findAll();
        for (StaffEntity s : listStaff) {
            if (s.getStatus() == 0) {
                continue;
            }
            AppUserEntity appUser = s.getAppUserByAppUserId();
            UserGroupEntity userGroup = appUser.getUserGroupByUserGroupId();
            UserDTO u = new UserDTO();
            u.setStaffId(s.getId());
            u.setAppUserId(appUser.getId());
            u.setUserGroupId(userGroup.getId());
            u.setUserName(appUser.getUserName());
            u.setFullName(s.getFullName());
            u.setEmail(s.getEmail());
            u.setPhone(s.getPhone());
            u.setAddress(s.getAddress());
            u.setDateOfBirth(s.getDateOfBirth());
            u.setUserGroupName(userGroup.getUserGroupName());
            u.setUserGroupCode(userGroup.getUserGroupCode());
            u.setStatus(s.getStatus());
            ret.add(u);
        }
        ret.sort(Comparator.comparing(o -> o.getUserName()));
        return ret;
    }

    public UserDTO addNewUser(UserDTO newUser) {
        UserGroupEntity userGroupEntity = userGroupRepository.getOne(newUser.getUserGroupId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUserEntity appUser = new AppUserEntity();
        appUser.setUserName(newUser.getUserName());
        appUser.setEncryptedPassword(encoder.encode(newUser.getPassword()));
        appUser.setUserGroupByUserGroupId(userGroupEntity);
        appUser.setStatus(1);
        appUser.setCreatedAt(new Date());
        appUser.setUpdatedAt(new Date());
        appUser = appUserRepository.save(appUser);

        StaffEntity staff = new StaffEntity();
        staff.setFullName(newUser.getFullName());
        staff.setEmail(newUser.getEmail());
        staff.setPhone(newUser.getPhone());
        staff.setAddress(newUser.getAddress());
        staff.setDateOfBirth(newUser.getDateOfBirth());
        staff.setStatus(1);
        staff.setCreatedAt(new Date());
        staff.setUpdatedAt(new Date());
        staff.setAppUserByAppUserId(appUser);
        staff.setStaffNameSearch(vNCharacterUtils.removeAccent(newUser.getFullName()));
        staff = staffRepository.save(staff);

        UserDTO u = new UserDTO();
        u.setStaffId(staff.getId());
        u.setAppUserId(appUser.getId());
        u.setUserGroupId(userGroupEntity.getId());
        u.setUserName(appUser.getUserName());
        u.setFullName(staff.getFullName());
        u.setEmail(staff.getEmail());
        u.setPhone(staff.getPhone());
        u.setAddress(staff.getAddress());
        u.setDateOfBirth(staff.getDateOfBirth());
        u.setUserGroupName(userGroupEntity.getUserGroupName());
        u.setUserGroupCode(userGroupEntity.getUserGroupCode());
        u.setStatus(staff.getStatus());
        return u;
    }

    public UserDTO updateUser(UserDTO newUser) {
        UserGroupEntity userGroupEntity = userGroupRepository.getOne(newUser.getUserGroupId());

        StaffEntity staff = staffRepository.getOne(newUser.getStaffId());
        staff.setFullName(newUser.getFullName());
        staff.setEmail(newUser.getEmail());
        staff.setPhone(newUser.getPhone());
        staff.setAddress(newUser.getAddress());
        staff.setDateOfBirth(newUser.getDateOfBirth());
        staff.setStaffNameSearch(vNCharacterUtils.removeAccent(newUser.getFullName()));
        staff = staffRepository.save(staff);

        AppUserEntity appUser = staff.getAppUserByAppUserId();
        appUser.setUserName(newUser.getUserName());
        appUser.setUserGroupByUserGroupId(userGroupEntity);
        appUser = appUserRepository.save(appUser);

        UserDTO u = new UserDTO();
        u.setStaffId(staff.getId());
        u.setAppUserId(appUser.getId());
        u.setUserGroupId(userGroupEntity.getId());
        u.setUserName(appUser.getUserName());
        u.setFullName(staff.getFullName());
        u.setEmail(staff.getEmail());
        u.setPhone(staff.getPhone());
        u.setAddress(staff.getAddress());
        u.setDateOfBirth(staff.getDateOfBirth());
        u.setUserGroupName(userGroupEntity.getUserGroupName());
        u.setUserGroupCode(userGroupEntity.getUserGroupCode());
        u.setStatus(staff.getStatus());
        return u;
    }

    public Boolean changePassword(UUID id, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUserEntity appUser = appUserRepository.getOne(id);
        appUser.setEncryptedPassword(encoder.encode(password));
        appUserRepository.save(appUser);
        return true;
    }

    public void delete(UUID staffId) {
        StaffEntity staff = staffRepository.getOne(staffId);
        AppUserEntity appUser = staff.getAppUserByAppUserId();
        staff.setStatus(0);
        appUser.setStatus(0);
        staffRepository.save(staff);
        appUserRepository.save(appUser);
    }

    public Boolean changeStatusStaff(UUID id, Integer status) {
        StaffEntity staff = staffRepository.getOne(id);
        staff.setStatus(status);
        staffRepository.save(staff);
        return true;
    }
}
