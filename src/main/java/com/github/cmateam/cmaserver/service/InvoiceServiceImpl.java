package com.github.cmateam.cmaserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.cmateam.cmaserver.dto.InvoiceDetailDTO;
import com.github.cmateam.cmaserver.dto.InvoiceSaveDTO;
import com.github.cmateam.cmaserver.dto.InvoiceShowDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicineSaleEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl {
	private InvoiceRepository invoiceRepository;
	private VNCharacterUtils vNCharacterUtils;
	private InvoiceDetailedRepository invoiceDetailedRepository;

	@Autowired
	public InvoiceServiceImpl(InvoiceRepository invoiceRepository, VNCharacterUtils vNCharacterUtils,
			InvoiceDetailedRepository invoiceDetailedRepository) {
		this.invoiceRepository = invoiceRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
	}

	public List<PatientDTO> searchByName(String name) {
		Pageable top10 = PageRequest.of(0, 10);
		name = '%' + vNCharacterUtils.removeAccent(name).toLowerCase() + '%';
		List<PatientEntity> listPatient = invoiceRepository.findByNameSearch(name, top10);
		List<PatientDTO> ret = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			ret.add(patientDTO);
		}
		return ret;
	}

	public List<PatientDTO> searchByPhone(String phone) {
		Pageable top10 = PageRequest.of(0, 10);
		phone = "%" + phone + "%";
		List<PatientEntity> listPatient = invoiceRepository.findByPhoneSearch(phone, top10);
		List<PatientDTO> ret = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			ret.add(patientDTO);
		}
		return ret;
	}

	public List<PatientDTO> searchByPatientCode(String patientCode) {
		Pageable top10 = PageRequest.of(0, 10);
		patientCode = "%" + patientCode.toLowerCase() + "%";
		List<PatientEntity> listPatient = invoiceRepository.findByPatientCodeSearch(patientCode, top10);
		List<PatientDTO> ret = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			ret.add(patientDTO);
		}
		return ret;
	}

	public InvoiceDetailDTO convertInvoiceDetailToDTO(InvoiceDetailedEntity invoiceDetailedEntity) {
		ServiceEntity serviceEntity = invoiceDetailedEntity.getServiceByServiceId();
		ServiceDTO serviceDto = new ServiceDTO();
		if (serviceEntity != null) {
			serviceDto.setId(serviceEntity.getId());
			serviceDto.setPrice(serviceEntity.getPrice());
			serviceDto.setServiceName(serviceEntity.getServiceName());
			serviceDto.setUnitName(serviceEntity.getUnitName());
		}

		MedicineSaleEntity medicineSaleEntity = invoiceDetailedEntity.getMedicineSaleByMedicineSaleId();
		MedicineSaleDTO medicineSaleDTO = new MedicineSaleDTO();
		if (medicineSaleEntity != null) {
			medicineSaleDTO.setId(medicineSaleEntity.getId());
			medicineSaleDTO.setTotalAmout(medicineSaleEntity.getTotalAmout());
			medicineSaleDTO.setNameMedicineSale("Phiếu Thuốc");
		}

		InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
		invoiceDetailDTO.setInvoiceDetailId(invoiceDetailedEntity.getId());
		invoiceDetailDTO.setAmount(invoiceDetailedEntity.getAmount());
		invoiceDetailDTO.setAmountPaid(invoiceDetailedEntity.getAmountPaid());
		invoiceDetailDTO.setQuantity(invoiceDetailedEntity.getQuantity());
		invoiceDetailDTO.setCreatedAt(invoiceDetailedEntity.getCreatedAt());
		invoiceDetailDTO.setMedicineSaleDto(medicineSaleDTO);
		invoiceDetailDTO.setServiceDto(serviceDto);

		return invoiceDetailDTO;
	}

	public InvoiceShowDTO convertInvoiceToDTO(InvoiceEntity invoiceEntity) {
		InvoiceShowDTO invoiceShowDTO = new InvoiceShowDTO();
		if (invoiceEntity != null) {
			PatientEntity patientEntity = invoiceEntity.getPatientByPatientId();
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(patientEntity.getId());
			patientDTO.setPatientName(patientEntity.getPatientName());
			patientDTO.setPatientCode(patientEntity.getPatientCode());
			patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());
			patientDTO.setGender(patientEntity.getGender());
			patientDTO.setAddress(patientEntity.getAddress());
			patientDTO.setPhone(patientEntity.getPhone());
			patientDTO.setDebt(patientEntity.getDebt());

			invoiceShowDTO.setInvoiceId(invoiceEntity.getId());
			invoiceShowDTO.setTotalAmount(invoiceEntity.getTotalAmount());
			invoiceShowDTO.setAmountPaid(invoiceEntity.getAmountPaid());

			List<InvoiceDetailedEntity> lstInvoiceDetails = invoiceEntity.getInvoiceDetailedsById();
			List<InvoiceDetailDTO> lstInvoiceDetailDTOs = new ArrayList<>();
			for (InvoiceDetailedEntity invoiceDetailedEntity : lstInvoiceDetails) {
				lstInvoiceDetailDTOs.add(convertInvoiceDetailToDTO(invoiceDetailedEntity));
			}
			invoiceShowDTO.setLstInvoiceDetails(lstInvoiceDetailDTOs);
			invoiceShowDTO.setPatientByPatient(patientDTO);
		}

		return invoiceShowDTO;
	}

	public InvoiceShowDTO getInvoiceByPatientId(UUID patientId) {
		InvoiceEntity invoiceEntity = invoiceRepository.findByPatientIdInNowDay(patientId);
		return convertInvoiceToDTO(invoiceEntity);
	}

	public Boolean updateInformationInvoice(InvoiceSaveDTO invoiceSaveDTO) {
		InvoiceEntity invoiceEntity = invoiceRepository.getOne(invoiceSaveDTO.getInvoiceId());

		invoiceEntity.setUpdatedAt(new Date());
		invoiceEntity.setStatus(2);
		invoiceEntity = invoiceRepository.save(invoiceEntity);

		for (int index = 0; index < invoiceSaveDTO.getLstInvoidDetailsSave().size(); index++) {
			if (invoiceSaveDTO.getLstInvoidDetailsSave().get(index).getInvoiceDetailId() != null) {
				InvoiceDetailedEntity invoiceDetailedEntity = invoiceDetailedRepository
						.getOne(invoiceSaveDTO.getLstInvoidDetailsSave().get(index).getInvoiceDetailId());
				invoiceDetailedEntity
						.setAmount(invoiceSaveDTO.getLstInvoidDetailsSave().get(index).getAmountInvoiceDetail());
				invoiceDetailedEntity.setAmountPaid(invoiceDetailedEntity.getAmountPaid()
						+ invoiceSaveDTO.getLstInvoidDetailsSave().get(index).getAmountPaidInvoiceDetail());
				invoiceDetailedEntity.setStatus(2);
				invoiceDetailedEntity.setUpdatedAt(new Date());
				invoiceDetailedEntity = invoiceDetailedRepository.save(invoiceDetailedEntity);

				InvoiceEntity invoice = invoiceDetailedEntity.getInvoiceByInvoiceId();
				invoice.setAmountPaid(invoice.getAmountPaid()
						+ invoiceSaveDTO.getLstInvoidDetailsSave().get(index).getAmountPaidInvoiceDetail());
				invoice = invoiceRepository.save(invoice);
			}
		}
		if (invoiceEntity == null) {
			return false;
		} else {
			return true;
		}
	}
}
