package com.github.cmateam.cmaserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.repository.PrescriptionDetailRepository;

@Service
public class PrescriptionDetailServiceImpl {
	private PrescriptionDetailRepository prescriptionDetailRepository;

	@Autowired
	public PrescriptionDetailServiceImpl(PrescriptionDetailRepository prescriptionDetailRepository) {
		this.prescriptionDetailRepository = prescriptionDetailRepository;
	}

	public Boolean deleteAPrescriptionDetail(UUID prescriptionDetailId) {
		if (prescriptionDetailRepository.getOne(prescriptionDetailId) != null) {
			prescriptionDetailRepository.deleteById(prescriptionDetailId);
			return true;
		} else {
			return false;
		}
	}

}
