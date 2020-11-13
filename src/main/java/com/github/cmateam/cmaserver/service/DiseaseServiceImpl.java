package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.DiseaseDTO;
import com.github.cmateam.cmaserver.entity.DiseaseEntity;
import com.github.cmateam.cmaserver.repository.DiseaseRepository;

@Service
public class DiseaseServiceImpl {
	private DiseaseRepository diseaseRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public DiseaseServiceImpl(DiseaseRepository diseaseRepository, VNCharacterUtils vNCharacterUtils) {
		this.diseaseRepository = diseaseRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

	public List<DiseaseDTO> getAllDiseaseByCode(String icd10Code) {
		Pageable top10 = PageRequest.of(0, 10);
		icd10Code = '%' + icd10Code.toLowerCase() + '%';
		List<DiseaseEntity> lstDiseaseEntities = diseaseRepository.getAllDiseaseByCode(icd10Code, top10);
		List<DiseaseDTO> lstdiseaseDtos = new ArrayList<>();
		for (DiseaseEntity de : lstDiseaseEntities) {
			DiseaseDTO diseaseDTO = new DiseaseDTO();
			diseaseDTO.setId(de.getId());
			diseaseDTO.setIcd10Code(de.getIcd10Code());
			diseaseDTO.setDiseaseName(de.getDiseaseName());
			diseaseDTO.setStatus(de.getStatus());
			diseaseDTO.setCreatedAt(de.getCreatedAt());
			diseaseDTO.setUpdatedAt(de.getUpdatedAt());
			lstdiseaseDtos.add(diseaseDTO);

		}

		return lstdiseaseDtos;
	}

	public List<DiseaseDTO> getAllDiseaseByDiseaseNameSearch(String diseaseNameSearch) {
		Pageable top10 = PageRequest.of(0, 10);
		diseaseNameSearch = '%' + vNCharacterUtils.removeAccent(diseaseNameSearch).toLowerCase() + '%';
		List<DiseaseEntity> lstDiseaseEntities = diseaseRepository.getAllDiseaseByDiseaseNameSearch(diseaseNameSearch, top10);
		List<DiseaseDTO> lstdiseaseDtos = new ArrayList<>();
		for (DiseaseEntity de : lstDiseaseEntities) {
			DiseaseDTO diseaseDTO = new DiseaseDTO();
			diseaseDTO.setId(de.getId());
			diseaseDTO.setIcd10Code(de.getIcd10Code());
			diseaseDTO.setDiseaseName(de.getDiseaseName());
			diseaseDTO.setStatus(de.getStatus());
			diseaseDTO.setCreatedAt(de.getCreatedAt());
			diseaseDTO.setUpdatedAt(de.getUpdatedAt());
			lstdiseaseDtos.add(diseaseDTO);

		}

		return lstdiseaseDtos;
	}
}
