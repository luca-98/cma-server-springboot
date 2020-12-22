package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.cmateam.cmaserver.configuration.CmaConfig;
import com.github.cmateam.cmaserver.entity.AppointmentEntity;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.OrdinalNumberEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.AppointmentRepository;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.OrdinalNumberRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

	private Logger logger = LoggerFactory.getLogger(ScheduledService.class);
	private CmaConfig cmaConfig;
	private CountIdRepository countIdRepository;
	private RoomServiceRepository roomServiceRepository;
	private AppointmentRepository appointmentRepository;
	private OrdinalNumberServiceImpl ordinalNumberServiceImpl;
	private OrdinalNumberRepository ordinalNumberRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private InvoiceRepository invoiceRepository;
	private PatientRepository patientRepository;
	private InvoiceServiceImpl invoiceServiceImpl;

	@Autowired
	public ScheduledService(CmaConfig cmaConfig, CountIdRepository countIdRepository,
			RoomServiceRepository roomServiceRepository, AppointmentRepository appointmentRepository,
			OrdinalNumberServiceImpl ordinalNumberServiceImpl, InvoiceDetailedRepository invoiceDetailedRepository,
			InvoiceRepository invoiceRepository, PatientRepository patientRepository,
			InvoiceServiceImpl invoiceServiceImpl) {
		this.cmaConfig = cmaConfig;
		this.countIdRepository = countIdRepository;
		this.roomServiceRepository = roomServiceRepository;
		this.appointmentRepository = appointmentRepository;
		this.ordinalNumberServiceImpl = ordinalNumberServiceImpl;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.invoiceRepository = invoiceRepository;
		this.patientRepository = patientRepository;
		this.invoiceServiceImpl = invoiceServiceImpl;
	}

	// Send public ip to gateway server
	// At second :00, at minutes :00, :10, :20, :30, :40 and :50, of every hour
	@Scheduled(cron = "0 0,10,20,30,40,50 * ? * *")
	public void sendIpPublic() {
		if (cmaConfig.getGatewayServerUrl() != null) {
			try {
				final CloseableHttpClient httpClient = HttpClients.createDefault();

				String ip = null;
				HttpGet request = new HttpGet("https://api.myip.com/");
				request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
				try (CloseableHttpResponse response = httpClient.execute(request)) {
					HttpEntity entity = response.getEntity();

					if (entity != null) {
						String result = EntityUtils.toString(entity);
						JSONObject obj = new JSONObject(result);
						ip = obj.getString("ip");
						logger.info(ip);
					}
				}

				HttpPost post = new HttpPost(cmaConfig.getGatewayServerUrl());
				List<NameValuePair> urlParameters = new ArrayList<>();
				urlParameters.add(new BasicNameValuePair("ip", ip));
				post.setEntity(new UrlEncodedFormEntity(urlParameters));
				httpClient.execute(post);
			} catch (Exception e) {
				logger.error("Send Ip Public error: {}", e);
			}
		}
	}

	@Scheduled(cron = "0 0 0 ? * *")
	public void startNewDayJob() {
		System.out.println("Run cronjob");
		resetMedicalAndPatientCodeCount();
		resetRoomInformation();
		deleteOldAppointment();
		createOrdinalNumberAppointment();
		caculatePatientDebt();
	}

	private void resetMedicalAndPatientCodeCount() {
		List<CountIdEntity> listCountId = countIdRepository.findAll();
		for (CountIdEntity e : listCountId) {
			e.setCountValue(0);
			e.setUpdatedAt(new Date());
			countIdRepository.save(e);
		}
	}

	private void resetRoomInformation() {
		List<RoomServiceEntity> listRoomService = roomServiceRepository.findAll();
		for (RoomServiceEntity e : listRoomService) {
			e.setTotalDone((short) 0);
			e.setTotalReceive((short) 0);
			e.setUpdatedAt(new Date());
			roomServiceRepository.save(e);
		}
	}

	private void deleteOldAppointment() {
		List<AppointmentEntity> lstAppointmentBeforNow = appointmentRepository.deleteAllDayOfExamBeforeNow();
		for (AppointmentEntity ae : lstAppointmentBeforNow) {
			appointmentRepository.delete(ae);
		}
	}

	private void createOrdinalNumberAppointment() {
		List<AppointmentEntity> listAppointNow = appointmentRepository.getAllToday();
		for (AppointmentEntity ae : listAppointNow) {
			StaffEntity staff = ae.getStaffByStaffId();
			if (staff != null) {
				List<RoomServiceEntity> currentRoom = staff.getRoomServicesById();
				if (currentRoom.size() != 0) {
					Short newOrdinalNumber = ordinalNumberServiceImpl.getOrdinalByRoom(currentRoom.get(0).getId());
					OrdinalNumberEntity ordinal = new OrdinalNumberEntity();
					ordinal.setDayOfExamination(new Date());
					ordinal.setOrdinalNumber(newOrdinalNumber);
					ordinal.setRoomServiceByRoomServiceId(currentRoom.get(0));
					ordinal.setStaffByStaffId(staff);
					ordinal.setCreatedAt(new Date());
					ordinal.setUpdatedAt(new Date());
					ordinal.setStatus(2);
					ordinal = ordinalNumberRepository.save(ordinal);
					ae.setOrdinalNumberByOrdinalNumberId(ordinal);
					ae = appointmentRepository.save(ae);
				}
			}
		}

		List<InvoiceEntity> lstInvoiceDebtBeforePresent = invoiceRepository.getListInvoiceDebtBeforePresent();
		for (InvoiceEntity i : lstInvoiceDebtBeforePresent) {
			i.setStatus(2);
			i = invoiceRepository.save(i);
			List<InvoiceDetailedEntity> getListInvoiceDetails = invoiceDetailedRepository
					.getListInvoiceDetialDebtByInvoiceId(i.getId());
			for (InvoiceDetailedEntity ide : getListInvoiceDetails) {
				ide.setStatus(2);
				invoiceDetailedRepository.save(ide);
			}
		}
	}

	private void caculatePatientDebt() {
		List<PatientEntity> listPatient = patientRepository.findAll();
		for (PatientEntity p : listPatient) {
			invoiceServiceImpl.updateDebtPatient(p);
		}
	}
}
