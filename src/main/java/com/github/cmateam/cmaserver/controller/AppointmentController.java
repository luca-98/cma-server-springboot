package com.github.cmateam.cmaserver.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.AppointmentDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.service.AppointmentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	private AppointmentServiceImpl appointmentServiceImpl;

	@Autowired
	public AppointmentController(AppointmentServiceImpl appointmentServiceImpl) {
		this.appointmentServiceImpl = appointmentServiceImpl;
	}

	@GetMapping("/get-appointment-by-day")
	public List<AppointmentDTO> findAppointmentByDay(
			@RequestParam("dayOfExamination") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dayOfExamination) {
		return appointmentServiceImpl.findAppointmentByDay(dayOfExamination);
	}

	@PostMapping("/add-patient-appointment")
	public AppointmentDTO addNewAppointment(@RequestParam(value = "patientCode", required = false) String patientCode,
			@RequestParam("patientName") String patientName, @RequestParam("phone") String phone,
			@RequestParam("dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam("gender") Integer gender, @RequestParam("address") String address,
			@RequestParam("staffId") UUID staffId, @RequestParam("debt") Long debt,
			@RequestParam("appointmentDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date appointmentDate,
			@RequestParam("appointmentTime") String appointmentTime) {

		return appointmentServiceImpl.createAppointment(patientCode, patientName, phone, dateOfBirth, gender, address,
				debt, appointmentDate, staffId, appointmentTime);
	}

	@GetMapping("/get-all-appointment")
	public ResponseEntity<?> getAllAppointment(@RequestParam(value = "pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize") Integer pageSize) {
		return appointmentServiceImpl.getAppointmentPagging(pageIndex, pageSize);
	}

	@GetMapping("/search-all-appointment")
	public ResponseEntity<?> getAllSearchAppointment(@RequestParam(value = "pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "patientName") String patientName,
			@RequestParam(value = "patientCode") String patientCode, @RequestParam(value = "phone") String phone,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
		return appointmentServiceImpl.getAppointmentSearchPagging(patientCode, patientName, phone, status, startDate,
				endDate, pageIndex, pageSize);
	}

	@GetMapping("/change-status")
	public Boolean changeStatus(@RequestParam(value = "id") UUID id, @RequestParam(value = "status") Integer status) {
		return appointmentServiceImpl.changeStatusAppointment(id, status);
	}

	@GetMapping("/search-by-name")
	public List<PatientDTO> searchByName(@RequestParam("name") String name) {
		return appointmentServiceImpl.searchByName(name);
	}

	@GetMapping("/search-by-phone")
	public List<PatientDTO> searchByPhone(@RequestParam("phone") String phone) {
		return appointmentServiceImpl.searchByPhone(phone);
	}

	@GetMapping("/search-by-code")
	public List<PatientDTO> searchByPatientCode(@RequestParam("patientCode") String patientCode) {
		return appointmentServiceImpl.searchByPatientCode(patientCode);
	}

	@PutMapping("/edit-appointment-created")
	public AppointmentDTO editAppointmentCreated(@RequestParam("appointmentId") UUID appointmentId,
			@RequestParam("appointmentDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date appointmentDate,
			@RequestParam("staffId") UUID staffId, @RequestParam("appointmentTime") String appointmentTime) {
		return appointmentServiceImpl.editAppointmentCreated(appointmentId, appointmentDate, staffId, appointmentTime);
	}

}
