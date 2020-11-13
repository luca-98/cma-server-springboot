package com.github.cmateam.cmaserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubclinicalServiceImpl {

    private StaffRepository staffRepository;
    private ServiceReportRepository serviceReportRepository;

    @Autowired
    public SubclinicalServiceImpl(StaffRepository staffRepository, ServiceReportRepository serviceReportRepository) {
        this.staffRepository = staffRepository;
        this.serviceReportRepository = serviceReportRepository;
    }

    public StaffDTO getStaffMinByService(UUID serviceId) {
        List<StaffEntity> listStaffWithService = staffRepository.getAllStaffByService(serviceId);
        if (listStaffWithService.size() == 0) {
            return new StaffDTO();
        }
        HashMap<StaffEntity, Integer> hashCount = new HashMap<>();
        Integer min = 0;
        for (StaffEntity s : listStaffWithService) {
            Integer count = serviceReportRepository.countWaitByStaff(s.getId());
            if (count < min) {
                min = count;
            }
            hashCount.put(s, count);
        }
        for (StaffEntity s : hashCount.keySet()) {
            if (hashCount.get(s) == min) {
                return convertStaffEntityToDto(s);
            }
        }
        return convertStaffEntityToDto(listStaffWithService.get(0));
    }

    private StaffDTO convertStaffEntityToDto(StaffEntity s) {
        StaffDTO ret = new StaffDTO();
        if (s == null) {
            return ret;
        }
        ret.setId(s.getId());
        ret.setFullName(s.getFullName());
        ret.setEmail(s.getEmail());
        ret.setPhone(s.getPhone());
        ret.setAddress(s.getAddress());
        ret.setDateOfBirth(s.getDateOfBirth());
        return ret;
    }
}
