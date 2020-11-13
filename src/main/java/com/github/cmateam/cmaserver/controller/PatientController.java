package com.github.cmateam.cmaserver.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cmateam.cmaserver.configuration.CmaConfig;
import com.github.cmateam.cmaserver.dto.HistoryDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.service.PatientServiceImpl;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patient")
public class PatientController {

	private PatientServiceImpl patientServiceImpl;
	private CmaConfig cmaConfig;
	private ResourceLoader resourceLoader;

	@Autowired
	public PatientController(PatientServiceImpl patientServiceImpl, CmaConfig cmaConfig,
			ResourceLoader resourceLoader) {
		this.patientServiceImpl = patientServiceImpl;
		this.cmaConfig = cmaConfig;
		this.resourceLoader = resourceLoader;
	}

	@GetMapping("/search-by-name")
	public List<PatientDTO> searchByName(@RequestParam("name") String name) {
		return patientServiceImpl.searchByName(name);
	}

	@GetMapping("/search-by-phone")
	public List<PatientDTO> searchByPhone(@RequestParam("phone") String phone) {
		return patientServiceImpl.searchByPhone(phone);
	}

	@GetMapping("/search-by-code")
	public List<PatientDTO> searchByPatientCode(@RequestParam("patientCode") String patientCode) {
		return patientServiceImpl.searchByPatientCode(patientCode);
	}

	@GetMapping("/search-auto-by-address")
	public List<String> searchAutoByAddress(@RequestParam("address") String address) {
		return patientServiceImpl.getAutoAddress(address);
	}

	@GetMapping("/find-by-patientCode")
	public PatientDTO findByPatientCode(@RequestParam("patientCode") String patientCode) {
		return patientServiceImpl.findByPatientCode(patientCode.toUpperCase());
	}

	@GetMapping("/find-by-phone")
	public PatientDTO findByPhone(@RequestParam("phone") String phone) {
		return patientServiceImpl.findByPhone(phone);
	}

	@GetMapping("/get-all-patient")
	public ResponseEntity<?> getAllPatient(@RequestParam(value = "pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize") Integer pageSize) {
		return patientServiceImpl.getAllPatientWithPage(pageIndex, pageSize);
	}

	@GetMapping("/search-patient")
	public ResponseEntity<?> searchPatient(@RequestParam("patientNameSearch") String patientNameSearch,
			@RequestParam(value = "yearOfBirth", required = false) Integer yearOfBirth,
			@RequestParam("addressSearch") String addressSearch, @RequestParam("patientCode") String patientCode,
			@RequestParam("phone") String phone, @RequestParam(value = "gender", required = false) Integer gender,
			@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "pageIndex") Integer pageIndex) {
		return patientServiceImpl.searchPatient(patientNameSearch, yearOfBirth, addressSearch, gender, patientCode,
				phone, pageSize, pageIndex);
	}

	@RequestMapping(value = "/import-excel", method = RequestMethod.POST)
	public ResponseEntity<List<PatientEntity>> importExcel(@RequestParam("fileImport") MultipartFile files)
			throws IOException, ParseException {
		return patientServiceImpl.importExcelFile(files);
	}

	@GetMapping("/download/danhSachBenhNhan.xlsx")
	public void downloadCsv(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=danhSachBenhNhan.xlsx");
		ByteArrayInputStream stream = patientServiceImpl.contactListToExcelFile();
		IOUtils.copy(stream, response.getOutputStream());
		response.flushBuffer();
	}

	@PutMapping("/delete-patient")
	public Boolean deletePatient(@RequestParam(value = "id") UUID id) {
		return patientServiceImpl.deletePatient(id);
	}

	@PutMapping("/edit-patient-information")
	public PatientDTO editPatientInformation(@RequestParam(value = "id", required = false) UUID id,
			@RequestParam(value = "patientName", required = false) String patientName,
			@RequestParam(value = "dateOfBirth", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam(value = "gender", required = false) Integer gender,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "phone", required = false) String phone) {
		return patientServiceImpl.editPatientInformation(id, patientName, dateOfBirth, gender, address, phone);
	}

	@GetMapping("/download/template")
	public void downloadTemplateImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resource resource = resourceLoader.getResource(cmaConfig.getTemplatePatients());
		if (resource.exists()) {
			InputStream inputStream = resource.getInputStream();
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"Mẫu danh sách bệnh nhân.xlsx\""));
			response.setContentLength((int) resource.contentLength());
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	@GetMapping("get-by-id")
	public PatientDTO getById(@RequestParam("id") UUID id) {
		return patientServiceImpl.getById(id);
	}

	@GetMapping("get-history")
	public List<HistoryDTO> getHistory(@RequestParam("id") UUID patientId) {
		return patientServiceImpl.getHistory(patientId);
	}
}
