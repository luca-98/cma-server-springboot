package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.SupplierDTO;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.SupplierRepository;

@Service
public class SupplierServiceServiceImpl {
	private SupplierRepository supplierRepository;

	@Autowired
	public SupplierServiceServiceImpl(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	public SupplierDTO convertSupplierDTO(SupplierEntity se) {
		SupplierDTO supplierDTO = new SupplierDTO();
		supplierDTO.setId(se.getId());
		supplierDTO.setSupplierName(se.getSupplierName());
		supplierDTO.setEmail(se.getEmail());
		supplierDTO.setAccountNumber(se.getAccountNumber());
		supplierDTO.setAddress(se.getAddress());
		supplierDTO.setDebt(se.getDebt());
		supplierDTO.setPhone(se.getPhone());
		return supplierDTO;
	}

	public List<SupplierDTO> searchByName(String supplierName) {
		Pageable top10 = PageRequest.of(0, 10);
		supplierName = '%' + supplierName + '%';
		List<SupplierEntity> listSupplier = supplierRepository.findByNameSearch(supplierName, top10);
		List<SupplierDTO> sdto = new ArrayList<>();
		for (SupplierEntity se : listSupplier) {
			sdto.add(convertSupplierDTO(se));

		}
		return sdto;
	}

	public List<SupplierDTO> searchByPhone(String phone) {
		Pageable top10 = PageRequest.of(0, 10);
		phone = '%' + phone + '%';
		List<SupplierEntity> listSupplier = supplierRepository.findByPhone(phone, top10);
		List<SupplierDTO> sdto = new ArrayList<>();
		for (SupplierEntity se : listSupplier) {
			sdto.add(convertSupplierDTO(se));

		}
		return sdto;
	}

	public List<SupplierDTO> searchByAddress(String address) {
		Pageable top10 = PageRequest.of(0, 10);
		address = '%' + address + '%';
		List<SupplierEntity> listSupplier = supplierRepository.findByAddressSearch(address, top10);
		List<SupplierDTO> sdto = new ArrayList<>();
		for (SupplierEntity se : listSupplier) {
			sdto.add(convertSupplierDTO(se));

		}
		return sdto;
	}

	public List<SupplierDTO> searchByEmail(String email) {
		Pageable top10 = PageRequest.of(0, 10);
		email = '%' + email + '%';
		List<SupplierEntity> listSupplier = supplierRepository.findByEmail(email, top10);
		List<SupplierDTO> sdto = new ArrayList<>();
		for (SupplierEntity se : listSupplier) {
			sdto.add(convertSupplierDTO(se));

		}
		return sdto;
	}

	public List<SupplierDTO> searchByAccountNumber(String accountNumber) {
		Pageable top10 = PageRequest.of(0, 10);
		accountNumber = '%' + accountNumber + '%';
		List<SupplierEntity> listSupplier = supplierRepository.findByAccountNumber(accountNumber, top10);
		List<SupplierDTO> sdto = new ArrayList<>();
		for (SupplierEntity se : listSupplier) {
			sdto.add(convertSupplierDTO(se));

		}
		return sdto;
	}
}
