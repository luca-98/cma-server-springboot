package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.PrintFormDTO;
import com.github.cmateam.cmaserver.entity.PrintFormEntity;
import com.github.cmateam.cmaserver.repository.PrintFormRepositority;

@Service
public class PrintFormServiceImpl {

	private PrintFormRepositority printFormRepositority;

	@Autowired
	public PrintFormServiceImpl(PrintFormRepositority printFormRepositority) {
		this.printFormRepositority = printFormRepositority;
	}

	public List<PrintFormDTO> getAllPrintForm() {
		List<PrintFormEntity> listFormEntity = printFormRepositority.findAllByOrderByPrintNameAsc();
		List<PrintFormDTO> listFormDto = new ArrayList<>();
		for (PrintFormEntity pf : listFormEntity) {
			PrintFormDTO printFormDTO = new PrintFormDTO();
			printFormDTO.setId(pf.getId());
			printFormDTO.setPrintCode(pf.getPrintCode());
			printFormDTO.setPrintName(pf.getPrintName());
			printFormDTO.setStatus(pf.getStatus());
			printFormDTO.setCreatedAt(pf.getCreatedAt());
			printFormDTO.setUpdatedAt(pf.getUpdatedAt());
			listFormDto.add(printFormDTO);
		}
		return listFormDto;
	}

	public PrintFormDTO findPrintFormById(UUID id) {
		PrintFormEntity printFormEntity = printFormRepositority.findPrintFormById(id);
		PrintFormDTO printFormDTO = new PrintFormDTO();
		if (printFormEntity == null) {
			return printFormDTO;
		}
		printFormDTO.setId(printFormEntity.getId());
		printFormDTO.setPrintName(printFormEntity.getPrintName());
		printFormDTO.setPrintCode(printFormEntity.getPrintCode());
		printFormDTO.setTemplateHTML(printFormEntity.getTemplateHTML());
		printFormDTO.setCreatedAt(printFormEntity.getCreatedAt());
		printFormDTO.setUpdatedAt(printFormEntity.getUpdatedAt());
		printFormDTO.setStatus(printFormEntity.getStatus());

		return printFormDTO;
	}

	public PrintFormEntity update(PrintFormEntity entity) {
		return printFormRepositority.save(entity);
	}
}
