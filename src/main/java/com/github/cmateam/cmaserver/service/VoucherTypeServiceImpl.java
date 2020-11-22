package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.VoucherTypeDTO;
import com.github.cmateam.cmaserver.entity.VoucherTypeEntity;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class VoucherTypeServiceImpl {
	private VoucherTypeRepository voucherTypeRepository;

	@Autowired
	public VoucherTypeServiceImpl(VoucherTypeRepository voucherTypeRepository) {
		this.voucherTypeRepository = voucherTypeRepository;
	}

	public VoucherTypeEntity getVoucherTypeByName(String name) {
		name = '%' + name + '%';
		return voucherTypeRepository.getVoucherTypeEntityByName(name);
	}

	public List<VoucherTypeDTO> getLstVoucherType() {
		List<VoucherTypeEntity> lstVoucherTypeEnties = voucherTypeRepository.lstVoucherTypes();
		List<VoucherTypeDTO> lstVoucherTypes = new ArrayList<>();
		for (VoucherTypeEntity vte : lstVoucherTypeEnties) {
			VoucherTypeDTO voucherTypeDTO = new VoucherTypeDTO();
			voucherTypeDTO.setVoucherTypeId(vte.getId());
			voucherTypeDTO.setVoucherTypeName(vte.getTypeName());
			lstVoucherTypes.add(voucherTypeDTO);
		}
		return lstVoucherTypes;
	}

}
