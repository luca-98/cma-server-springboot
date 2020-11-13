package com.github.cmateam.cmaserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.DiseaseDTO;
import com.github.cmateam.cmaserver.service.DiseaseServiceImpl;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
	private DiseaseServiceImpl diseaseServiceImpl;

	@Autowired
	public DiseaseController(DiseaseServiceImpl diseaseServiceImpl) {
		this.diseaseServiceImpl = diseaseServiceImpl;
	}

	@GetMapping("/get-disease-by-code")
	public List<DiseaseDTO> getAllDiseaseByCode(@RequestParam("icd10Code") String icd10Code) {
		return diseaseServiceImpl.getAllDiseaseByCode(icd10Code);
	}
	
	@GetMapping("/get-disease-by-name")
	public List<DiseaseDTO> getAllDiseaseByDiseaseNameSearch(@RequestParam("diseaseNameSearch") String diseaseNameSearch) {
		return diseaseServiceImpl.getAllDiseaseByDiseaseNameSearch(diseaseNameSearch);
	}


}
