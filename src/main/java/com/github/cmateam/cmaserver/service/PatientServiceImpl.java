package com.github.cmateam.cmaserver.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.HistoryDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.PatientSearchDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;

@Service
public class PatientServiceImpl {

	private PatientRepository patientRepository;
	private CountIdRepository countIdRepository;
	private VNCharacterUtils vNCharacterUtils;
	private MedicalExaminationRepository medicalExaminationRepository;
	private ServiceRepository serviceRepository;

	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository, CountIdRepository countIdRepository,
			VNCharacterUtils vNCharacterUtils, MedicalExaminationRepository medicalExaminationRepository,
			ServiceRepository serviceRepository) {
		this.patientRepository = patientRepository;
		this.countIdRepository = countIdRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.medicalExaminationRepository = medicalExaminationRepository;
		this.serviceRepository = serviceRepository;
	}

	public List<PatientDTO> searchByName(String name) {
		Pageable top10 = PageRequest.of(0, 10);
		name = '%' + vNCharacterUtils.removeAccent(name).toLowerCase() + '%';
		List<PatientEntity> listPatient = patientRepository.findByNameSearch(name, top10);
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
		List<PatientEntity> listPatient = patientRepository.findByPhoneSearch(phone, top10);
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
		patientCode = "%" + patientCode + "%";
		List<PatientEntity> listPatient = patientRepository.findByPatientCodeSearch(patientCode, top10);
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

	public PatientDTO findByPatientCode(String patientCode) {
		PatientDTO patientDTO = new PatientDTO();
		PatientEntity p = patientRepository.findByPatientCode(patientCode);
		if (p == null) {
			return patientDTO;
		}
		patientDTO.setId(p.getId());
		patientDTO.setPatientName(p.getPatientName());
		patientDTO.setPatientCode(p.getPatientCode());
		patientDTO.setDateOfBirth(p.getDateOfBirth());
		patientDTO.setGender(p.getGender());
		patientDTO.setAddress(p.getAddress());
		patientDTO.setPhone(p.getPhone());
		patientDTO.setDebt(p.getDebt());
		return patientDTO;
	}

	public PatientDTO findByPhone(String phone) {
		PatientDTO patientDTO = new PatientDTO();
		PatientEntity p = patientRepository.findByPhone(phone);
		if (p == null) {
			return patientDTO;
		}
		patientDTO.setId(p.getId());
		patientDTO.setPatientName(p.getPatientName());
		patientDTO.setPatientCode(p.getPatientCode());
		patientDTO.setDateOfBirth(p.getDateOfBirth());
		patientDTO.setGender(p.getGender());
		patientDTO.setAddress(p.getAddress());
		patientDTO.setPhone(p.getPhone());
		patientDTO.setDebt(p.getDebt());
		return patientDTO;
	}

	public List<String> getAutoAddress(String address) {
		address = '%' + vNCharacterUtils.removeAccent(address).toLowerCase() + '%';
		Pageable top10 = PageRequest.of(0, 10);
		return patientRepository.searchByAddress(address, top10);
	}

	public PatientEntity updatePatient(String patientCode, String patientName, Date dateOfBirth, Integer gender,
			String address, String phone, Long debt) {
		PatientEntity patientEntity = new PatientEntity();
		boolean isAddNew = false;
		if (patientCode == null) {
			isAddNew = true;
		} else {
			patientEntity = patientRepository.findByPatientCode(patientCode);
			if (patientEntity == null) {
				isAddNew = true;
			} else {
				patientEntity.setUpdatedAt(new Date());
			}
		}
		if (isAddNew) {
			patientEntity = new PatientEntity();
			CountIdEntity countIdEntity = countIdRepository.findByCountName("PATIENT_CODE");
			Integer index = countIdEntity.getCountValue();
			countIdEntity.setCountValue(index + 1);
			countIdRepository.save(countIdEntity);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
			String strDate = formatter.format(date);
			patientCode = String.format("BN%s%03d", strDate, index);
			patientEntity.setPatientCode(patientCode);
			patientEntity.setStatus(1);
			patientEntity.setUpdatedAt(new Date());
			patientEntity.setCreatedAt(new Date());
			patientEntity.setDebt(0L);
		}
		patientEntity.setPatientName(patientName);
		patientEntity.setPatientNameSearch(vNCharacterUtils.removeAccent(patientName).toLowerCase());
		patientEntity.setDateOfBirth(dateOfBirth);
		patientEntity.setGender(gender);
		patientEntity.setAddress(address);
		patientEntity.setAddressSearch(vNCharacterUtils.removeAccent(address).toLowerCase());
		patientEntity.setPhone(phone);
		if (debt != null) {
			patientEntity.setDebt(debt);
		}
		return patientRepository.save(patientEntity);
	}

	public ResponseEntity<?> getAllPatientWithPage(Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		PatientSearchDTO patientSearchDTO = new PatientSearchDTO();
		patientSearchDTO.setPageIndex(pageIndex);
		patientSearchDTO.setPageSize(pageSize);
		patientSearchDTO.setTotalRecord(patientRepository.countAll());

		List<PatientEntity> lstEntities = patientRepository.findAllByPagging(pageable);
		List<PatientDTO> lstPatientDTOs = new ArrayList<>();
		for (PatientEntity p : lstEntities) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			lstPatientDTOs.add(patientDTO);
		}
		patientSearchDTO.setPatientEntityList(lstPatientDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(patientSearchDTO);
	}

	public Boolean checkRowEmpty(XSSFRow row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();
		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}

	public ResponseEntity<List<PatientEntity>> importExcelFile(MultipartFile files) throws IOException, ParseException {
		HttpStatus status = HttpStatus.OK;
		List<PatientEntity> patientList = new ArrayList<>();
		String patientCode;
		String patientCodeOld = '%' + "BNC" + '%';
		Integer numberPatientCodeOld = patientRepository.countAllPatientOld(patientCodeOld);
		String genderImport;
		Integer genderSave;

		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
			XSSFRow row = worksheet.getRow(index);
			genderImport = row.getCell(2).getStringCellValue();
			if (vNCharacterUtils.removeAccent(genderImport).toLowerCase().equalsIgnoreCase("nu")) {
				genderSave = 1;
			} else {
				genderSave = 0;
			}

			if (index > 0) {
				if (!checkRowEmpty(row)) {
					PatientEntity patient = new PatientEntity();
					patientCode = String.format("BNC%08d", numberPatientCodeOld + index);
					patient.setPatientCode(patientCode);
					patient.setPatientName(row.getCell(0).getStringCellValue());
					patient.setPatientNameSearch(
							vNCharacterUtils.removeAccent(row.getCell(0).getStringCellValue()).toLowerCase());
					patient.setDateOfBirth(row.getCell(1).getDateCellValue());
					patient.setGender(genderSave);
					patient.setAddress(row.getCell(3).getStringCellValue());
					patient.setAddressSearch(
							vNCharacterUtils.removeAccent(row.getCell(3).getStringCellValue()).toLowerCase());
					patient.setPhone(row.getCell(4).getStringCellValue());
					patient.setDebt((long) (row.getCell(5).getNumericCellValue()));
					patient.setStatus(1);
					patient.setCreatedAt(new Date());
					patient.setUpdatedAt(new Date());
					patientList.add(patient);
				}

			}
		}
		workbook.close();
		patientRepository.saveAll(patientList);

		return new ResponseEntity<>(patientList, status);
	}

	public ByteArrayInputStream contactListToExcelFile() {
		List<PatientEntity> patients = patientRepository.findAllByNoPagging();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Patients");
			sheet.createFreezePane(0, 1);
			Row row = sheet.createRow(0);
			CellStyle headerCellStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			font.setFontName("Arial");
			font.setBold(true);
			font.setColor(IndexedColors.WHITE.getIndex());
			headerCellStyle.setFont(font);

			Cell cell = row.createCell(0);
			cell.setCellValue("Họ và tên");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Mã bệnh nhân");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Ngày sinh");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Giới tính");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Địa chỉ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(5);
			cell.setCellValue("Số điện thoại");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(6);
			cell.setCellValue("Công nợ");
			cell.setCellStyle(headerCellStyle);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String genderExport;
			for (int i = 0; i < patients.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				dataRow.createCell(0).setCellValue(patients.get(i).getPatientName());
				dataRow.createCell(1).setCellValue(patients.get(i).getPatientCode());
				dataRow.createCell(2).setCellValue(formatter.format(patients.get(i).getDateOfBirth()));
				if (patients.get(i).getGender() == 1) {
					genderExport = "Nữ";
				} else {
					genderExport = "Nam";
				}
				dataRow.createCell(3).setCellValue(genderExport);
				dataRow.createCell(4).setCellValue(patients.get(i).getAddress());
				dataRow.createCell(5).setCellValue(patients.get(i).getPhone());
				dataRow.createCell(6).setCellValue(patients.get(i).getDebt());
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PatientDTO getById(UUID id) {
		PatientDTO ret = new PatientDTO();
		PatientEntity p = patientRepository.getOne(id);
		if (p == null || p.getStatus() != 1) {
			return ret;
		} else {
			return convertDTO(p);
		}
	}

	public PatientDTO convertDTO(PatientEntity patient) {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(patient.getId());
		patientDTO.setPatientName(patient.getPatientName());
		patientDTO.setPatientCode(patient.getPatientCode());
		patientDTO.setDateOfBirth(patient.getDateOfBirth());
		patientDTO.setGender(patient.getGender());
		patientDTO.setAddress(patient.getAddress());
		patientDTO.setPhone(patient.getPhone());
		patientDTO.setDebt(patient.getDebt());
		return patientDTO;
	}

	public ResponseEntity<?> getAllByPatientWithoutGenderAndYearOfBirthPagging(String patientCode,
			String patientNameSearch, String phone, String addressSearch, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		phone = '%' + phone + '%';
		patientCode = '%' + patientCode + '%';
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		addressSearch = '%' + vNCharacterUtils.removeAccent(addressSearch).toLowerCase() + '%';
		pageable = PageRequest.of(pageIndex, pageSize);
		PatientSearchDTO patientSearchDTO = new PatientSearchDTO();
		patientSearchDTO.setPageIndex(pageIndex);
		patientSearchDTO.setPageSize(pageSize);
		patientSearchDTO.setTotalRecord(patientRepository.countAllByPatientWithoutGenderAndYearOfBirthPagging(
				patientCode, patientNameSearch, phone, addressSearch));
		List<PatientEntity> lstEntities = patientRepository.searchAllByPatientWithoutGenderAndYearOfBirthPagging(
				patientCode, patientNameSearch, phone, addressSearch, pageable);
		List<PatientDTO> lstPatientDTOs = new ArrayList<>();
		for (PatientEntity p : lstEntities) {
			lstPatientDTOs.add(convertDTO(p));
		}
		patientSearchDTO.setPatientEntityList(lstPatientDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(patientSearchDTO);
	}

	public ResponseEntity<?> getAllByPatientWithGenderPagging(String patientCode, String patientNameSearch,
			String phone, String addressSearch, Integer gender, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		phone = '%' + phone + '%';
		patientCode = '%' + patientCode + '%';
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		addressSearch = '%' + vNCharacterUtils.removeAccent(addressSearch).toLowerCase() + '%';
		pageable = PageRequest.of(pageIndex, pageSize);
		PatientSearchDTO patientSearchDTO = new PatientSearchDTO();
		patientSearchDTO.setPageIndex(pageIndex);
		patientSearchDTO.setPageSize(pageSize);
		patientSearchDTO.setTotalRecord(patientRepository.countAllByPatientWithGenderPagging(patientCode,
				patientNameSearch, phone, addressSearch, gender));
		List<PatientEntity> lstEntities = patientRepository.searchAllByPatientWithGenderPagging(patientCode,
				patientNameSearch, phone, addressSearch, gender, pageable);
		List<PatientDTO> lstPatientDTOs = new ArrayList<>();
		for (PatientEntity p : lstEntities) {
			lstPatientDTOs.add(convertDTO(p));
		}
		patientSearchDTO.setPatientEntityList(lstPatientDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(patientSearchDTO);
	}

	public ResponseEntity<?> getAllByPatientWithYearOfBirthPagging(String patientCode, String patientNameSearch,
			String phone, String addressSearch, Integer yearOfBirth, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		phone = '%' + phone + '%';
		patientCode = '%' + patientCode + '%';
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		addressSearch = '%' + vNCharacterUtils.removeAccent(addressSearch).toLowerCase() + '%';
		pageable = PageRequest.of(pageIndex, pageSize);
		PatientSearchDTO patientSearchDTO = new PatientSearchDTO();
		patientSearchDTO.setPageIndex(pageIndex);
		patientSearchDTO.setPageSize(pageSize);
		patientSearchDTO.setTotalRecord(patientRepository.countAllByPatientWithYearOfBirthPagging(patientCode,
				patientNameSearch, phone, addressSearch, yearOfBirth));
		List<PatientEntity> lstEntities = patientRepository.searchAllByPatientWithYearOfBirthPagging(patientCode,
				patientNameSearch, phone, addressSearch, yearOfBirth, pageable);
		List<PatientDTO> lstPatientDTOs = new ArrayList<>();
		for (PatientEntity p : lstEntities) {
			lstPatientDTOs.add(convertDTO(p));
		}
		patientSearchDTO.setPatientEntityList(lstPatientDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(patientSearchDTO);
	}

	public ResponseEntity<?> getAllByPatientWithAllPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer yearOfBirth, Integer gender, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		phone = '%' + phone + '%';
		patientCode = '%' + patientCode + '%';
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		addressSearch = '%' + vNCharacterUtils.removeAccent(addressSearch).toLowerCase() + '%';
		pageable = PageRequest.of(pageIndex, pageSize);
		PatientSearchDTO patientSearchDTO = new PatientSearchDTO();
		patientSearchDTO.setPageIndex(pageIndex);
		patientSearchDTO.setPageSize(pageSize);
		patientSearchDTO.setTotalRecord(patientRepository.countAllByPatientWithAllPagging(patientCode,
				patientNameSearch, phone, addressSearch, yearOfBirth, gender));
		List<PatientEntity> lstEntities = patientRepository.searchAllByPatientWithAllPagging(patientCode,
				patientNameSearch, phone, addressSearch, yearOfBirth, gender, pageable);
		List<PatientDTO> lstPatientDTOs = new ArrayList<>();
		for (PatientEntity p : lstEntities) {
			lstPatientDTOs.add(convertDTO(p));
		}
		patientSearchDTO.setPatientEntityList(lstPatientDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(patientSearchDTO);
	}

	public ResponseEntity<?> searchPatient(String patientNameSearch, Integer yearOfBirth, String addressSearch,
			Integer gender, String patientCode, String phone, Integer pageSize, Integer pageIndex) {

		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (yearOfBirth == null && gender == null) {
			return getAllByPatientWithoutGenderAndYearOfBirthPagging(patientCode, patientNameSearch, phone,
					addressSearch, pageIndex, pageSize);
		} else if (gender != null && yearOfBirth == null) {
			return getAllByPatientWithGenderPagging(patientCode, patientNameSearch, phone, addressSearch, gender,
					pageIndex, pageSize);
		} else if (gender == null && yearOfBirth != null) {
			return getAllByPatientWithYearOfBirthPagging(patientCode, patientNameSearch, phone, addressSearch,
					yearOfBirth, pageIndex, pageSize);
		}
		return getAllByPatientWithAllPagging(patientCode, patientNameSearch, phone, addressSearch, yearOfBirth, gender,
				pageIndex, pageSize);
	}

	public Boolean deletePatient(UUID id) {
		PatientEntity patientEntity = patientRepository.getOne(id);
		if (patientRepository.getAllPatientInReceiveWithStatus().contains(id)) {
			return false;
		} else {
			patientEntity.setStatus(0);
			patientEntity = patientRepository.save(patientEntity);
		}
		if (patientEntity.getStatus() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public PatientDTO editPatientInformation(UUID id, String patientName, Date dateOfBirth, Integer gender,
			String address, String phone) {
		PatientEntity patientEntity = patientRepository.getOne(id);
		if (patientEntity != null) {
			patientEntity.setPatientName(patientName);
			patientEntity.setPatientNameSearch(vNCharacterUtils.removeAccent(patientName).toLowerCase());
			patientEntity.setDateOfBirth(dateOfBirth);
			patientEntity.setGender(gender);
			patientEntity.setAddress(address);
			patientEntity.setAddressSearch(vNCharacterUtils.removeAccent(address).toLowerCase());
			patientEntity.setPhone(phone);
			// patientEntity.get().setDebt(debt);
			patientEntity.setUpdatedAt(new Date());
			patientRepository.save(patientEntity);
		}
		return convertDTO(patientEntity);
	}

	public List<HistoryDTO> getHistory(UUID patientId) {
		List<HistoryDTO> ret = new ArrayList<>();
		List<MedicalExaminationEntity> listMedical = medicalExaminationRepository.findByPatientId(patientId);
		ServiceEntity clinicalExam = serviceRepository.findServiceClinicExam();
		for (MedicalExaminationEntity m : listMedical) {
			HistoryDTO h = new HistoryDTO();
			if (m.getStatus() == 5 || m.getStatus() == 6) {
				if (m.getSummary() != null) {
					h.setType(1);
					h.setId(m.getId());
					h.setCode(m.getMedicalExaminationCode());
					h.setService(clinicalExam.getServiceName());
					h.setQuanity(1);
					h.setSummary(m.getSummary());
					h.setMainDisease(m.getMainDisease());
					h.setExtraDisease(m.getExtraDisease());
					h.setStaff(convertToStaffDTO(m.getStaffByStaffId()));
					h.setCreatedAt(m.getCreatedAt());
					ret.add(h);
				}
				List<ServiceReportEntity> listServiceReport = m.getServiceReportsById();
				if (listServiceReport != null) {
					for (ServiceReportEntity sr : listServiceReport) {
						if (sr.getStatus() == 3) {
							h = new HistoryDTO();
							h.setType(2);
							h.setId(sr.getId());
							h.setCode(m.getMedicalExaminationCode());
							h.setService(sr.getServiceByServiceId().getServiceName());
							h.setQuanity(1);
							h.setSummary(null);
							h.setMainDisease(null);
							h.setExtraDisease(null);
							h.setStaff(convertToStaffDTO(sr.getStaffByStaffId()));
							h.setCreatedAt(sr.getCreatedAt());
							ret.add(h);
						}
					}
				}
				List<PrescriptionEntity> listPres = m.getPrescriptionsById();
				if (listPres != null) {
					for (PrescriptionEntity pres : listPres) {
						h = new HistoryDTO();
						h.setType(3);
						h.setId(pres.getId());
						h.setCode(m.getMedicalExaminationCode());
						h.setService("Kê đơn thuốc");
						h.setQuanity(1);
						h.setSummary(null);
						h.setMainDisease(null);
						h.setExtraDisease(null);
						h.setStaff(convertToStaffDTO(pres.getStaffByStaffId()));
						h.setCreatedAt(pres.getCreatedAt());
						ret.add(h);
					}
				}
			}
		}
		ret.sort(Comparator.comparing(o -> o.getCreatedAt()));
		Collections.reverse(ret);
		return ret;
	}

	public StaffDTO convertToStaffDTO(StaffEntity se) {
		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setId(se.getId());
		staffDTO.setFullName(se.getFullName());
		staffDTO.setEmail(se.getEmail());
		staffDTO.setPhone(se.getPhone());
		staffDTO.setAddress(se.getAddress());
		staffDTO.setDateOfBirth(se.getDateOfBirth());
		return staffDTO;
	}
}
