package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ReceiptDetailTableDTO;
import com.github.cmateam.cmaserver.dto.ReceiptSaveDTO;
import com.github.cmateam.cmaserver.entity.MaterialEntity;
import com.github.cmateam.cmaserver.entity.MedicineEntity;
import com.github.cmateam.cmaserver.entity.ReceiptDetailEntity;
import com.github.cmateam.cmaserver.entity.ReceiptEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.GroupMaterialRepository;
import com.github.cmateam.cmaserver.repository.GroupMedicineRepository;
import com.github.cmateam.cmaserver.repository.MaterialRepository;
import com.github.cmateam.cmaserver.repository.MedicineRepository;
import com.github.cmateam.cmaserver.repository.ReceiptDetailRepository;
import com.github.cmateam.cmaserver.repository.ReceiptRepository;
import com.github.cmateam.cmaserver.repository.SupplierRepository;

@Service
public class ReceiptServiceImpl {
	private ReceiptRepository receiptRepository;
	private SupplierRepository supplierRepository;
	private MedicineRepository medicineRepository;
	private MaterialRepository materialRepository;
	private ReceiptDetailRepository receiptDetailRepository;
	private GroupMaterialRepository groupMaterialRepository;
	private GroupMedicineRepository groupMedicineRepository;
	private VNCharacterUtils vNCharacterUtils;
	private StaffServiceImpl staffServiceImpl;

	@Autowired
	public ReceiptServiceImpl(ReceiptRepository receiptRepository, SupplierRepository supplierRepository,
			MedicineRepository medicineRepository, MaterialRepository materialRepository,
			GroupMaterialRepository groupMaterialRepository, ReceiptDetailRepository receiptDetailRepository,
			GroupMedicineRepository groupMedicineRepository, VNCharacterUtils vNCharacterUtils,
			StaffServiceImpl staffServiceImpl) {
		this.receiptRepository = receiptRepository;
		this.supplierRepository = supplierRepository;
		this.medicineRepository = medicineRepository;
		this.materialRepository = materialRepository;
		this.receiptDetailRepository = receiptDetailRepository;
		this.groupMaterialRepository = groupMaterialRepository;
		this.groupMedicineRepository = groupMedicineRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.staffServiceImpl = staffServiceImpl;
	}

	public Boolean saveReceipt(ReceiptSaveDTO receiptSaveDTO) {
		ReceiptEntity receiptEntity = null;
		SupplierEntity supplierEntity = null;
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(receiptSaveDTO.getUsername());
		boolean isAddNew = true;
		if (receiptSaveDTO.getSupplierId() != null) {
			supplierEntity = supplierRepository.getOne(receiptSaveDTO.getSupplierId());
			if (supplierEntity != null) {
				isAddNew = false;
			}
		}
		if (isAddNew) {
			supplierEntity = new SupplierEntity();
			supplierEntity.setStatus(1);
			supplierEntity.setCreatedAt(new Date());
			supplierEntity.setUpdatedAt(new Date());
		} else {
			supplierEntity.setUpdatedAt(new Date());
		}
		supplierEntity.setSupplierName(receiptSaveDTO.getSupplierName());
		supplierEntity
				.setSupplierNameSearch(vNCharacterUtils.removeAccent(receiptSaveDTO.getSupplierName()).toLowerCase());
		supplierEntity.setEmail(receiptSaveDTO.getEmail());
		supplierEntity.setAddress(receiptSaveDTO.getAddress());
		supplierEntity.setAccountNumber(receiptSaveDTO.getAccountNumber());
		supplierEntity.setPhone(receiptSaveDTO.getPhone());
		supplierEntity = supplierRepository.save(supplierEntity);

		receiptEntity = new ReceiptEntity();
		receiptEntity.setCreatedAt(new Date());
		receiptEntity.setUpdatedAt(new Date());
		receiptEntity.setStatus(1);
		receiptEntity.setSupplierBySupplierId(supplierEntity);
		receiptEntity.setTotalAmount(receiptSaveDTO.getTotalAmount());
		receiptEntity.setAmountPaid(receiptSaveDTO.getAmountPaid());
		receiptEntity.setStaffByStaffId(staffEntity);
		receiptRepository.save(receiptEntity);

		List<ReceiptDetailEntity> lstReciptDetails = new ArrayList<>();
		ReceiptDetailTableDTO receiptDetailTableDTO = new ReceiptDetailTableDTO();
		MedicineEntity medicineEntity = null;
		MaterialEntity materialEntity = null;
		for (int index = 0; index < receiptSaveDTO.getLstReceiptDetails().size(); index++) {
			boolean isAddNewMedicine = true;
			boolean isAddNewMaterial = true;
			ReceiptDetailEntity receiptDetailEntity = new ReceiptDetailEntity();
			receiptDetailTableDTO = receiptSaveDTO.getLstReceiptDetails().get(index);
			receiptDetailEntity.setQuantity(receiptDetailTableDTO.getQuantityDetail());
			receiptDetailEntity.setAmount(receiptDetailTableDTO.getAmountDetail());

			if (receiptDetailTableDTO.getReceiptName() != null) {
				materialEntity = materialRepository.getMaterialByName(receiptDetailTableDTO.getReceiptName());
				medicineEntity = medicineRepository.getMedicineByName(receiptDetailTableDTO.getReceiptName());
				if (receiptDetailTableDTO.getGroupMedicineId() != null) {
					if (medicineEntity != null) {
						isAddNewMedicine = false;
					}
					isAddNewMaterial = false;
				}
				if (receiptDetailTableDTO.getGroupMaterialId() != null) {
					if (materialEntity != null) {
						isAddNewMaterial = false;
					}
					isAddNewMedicine = false;
				}

			}
			if (isAddNewMedicine) {
				medicineEntity = new MedicineEntity();
				medicineEntity.setStatus(1);
				medicineEntity.setCreatedAt(new Date());
				medicineEntity.setUpdatedAt(new Date());
				medicineEntity.setQuantity(receiptDetailTableDTO.getQuantityDetail());
			} else {
				if (medicineEntity != null) {
					medicineEntity.setUpdatedAt(new Date());
					medicineEntity.setQuantity(
							(short) (receiptDetailTableDTO.getQuantityDetail() + medicineEntity.getQuantity()));
				}
			}
			if (medicineEntity != null) {
				medicineEntity.setMedicineName(receiptDetailTableDTO.getReceiptName());
				medicineEntity.setMedicineNameSearch(
						vNCharacterUtils.removeAccent(receiptDetailTableDTO.getReceiptName()).toLowerCase());
				medicineEntity.setPrice(receiptDetailTableDTO.getPrice());
				medicineEntity.setUnitName(receiptDetailTableDTO.getUnitName());
				medicineEntity.setGroupMedicineByGroupMedicineId(
						groupMedicineRepository.getOne(receiptDetailTableDTO.getGroupMedicineId()));
				medicineRepository.save(medicineEntity);
			}

			if (isAddNewMaterial) {
				materialEntity = new MaterialEntity();
				materialEntity.setStatus(1);
				materialEntity.setCreatedAt(new Date());
				materialEntity.setUpdatedAt(new Date());
				materialEntity.setQuantity((int) receiptDetailTableDTO.getQuantityDetail());
			} else {
				if (materialEntity != null) {
					materialEntity.setUpdatedAt(new Date());
					materialEntity.setQuantity(
							(int) receiptDetailTableDTO.getQuantityDetail() + materialEntity.getQuantity());
				}
			}
			if (materialEntity != null) {
				materialEntity.setMaterialName(receiptDetailTableDTO.getReceiptName());
				materialEntity.setMaterialNameSearch(
						vNCharacterUtils.removeAccent(receiptDetailTableDTO.getReceiptName()).toLowerCase());
				materialEntity.setUnitName(receiptDetailTableDTO.getUnitName());
				materialEntity.setGroupMaterialByGroupMaterialId(
						groupMaterialRepository.getOne(receiptDetailTableDTO.getGroupMaterialId()));
				materialRepository.save(materialEntity);
			}

			receiptDetailEntity.setMedicineByMedicineId(medicineEntity);
			receiptDetailEntity.setMaterialByMaterialId(materialEntity);
			receiptDetailEntity.setReceiptByReceiptId(receiptEntity);
			receiptDetailEntity.setStatus(1);
			receiptDetailEntity.setCreatedAt(new Date());
			receiptDetailEntity.setUpdatedAt(new Date());

			lstReciptDetails.add(receiptDetailEntity);
		}
		lstReciptDetails = receiptDetailRepository.saveAll(lstReciptDetails);
		return true;
	}

	public List<String> searchAutoNameReceipt(String receiptName) {
		receiptName = '%' + vNCharacterUtils.removeAccent(receiptName).toLowerCase() + '%';
		List<String> autoSearchMedicine = medicineRepository.getListMedicinByName(receiptName);
		List<String> autoSearchMaterial = materialRepository.getListMaterialByName(receiptName);
		for (int i = 0; i < autoSearchMaterial.size(); i++) {
			autoSearchMedicine.add(autoSearchMaterial.get(i));
		}
		return autoSearchMedicine;
	}

	public List<String> searchAutoNameUnitName(String unitName) {
		unitName = '%' + unitName.toLowerCase() + '%';
		List<String> autoSearchUnitNameMedicine = medicineRepository.getListMedicinByUnitName(unitName);
		List<String> autoSearchUnitNameMaterial = materialRepository.getListMaterialByUnitName(unitName);
		for (int i = 0; i < autoSearchUnitNameMaterial.size(); i++) {
			autoSearchUnitNameMedicine.add(autoSearchUnitNameMaterial.get(i));
		}
		return autoSearchUnitNameMedicine;
	}

}
