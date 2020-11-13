package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.AppointmentEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

	@Query("FROM AppointmentEntity a WHERE a.dayOfExamination = ?1 ")
	List<AppointmentEntity> findAppointmentByDay(Date dayOfExamination);

	@Query("from AppointmentEntity a order by a.createdAt ASC")
	List<AppointmentEntity> getAppointmentPagging(Pageable pageable);

	@Query("select count(a.id) from AppointmentEntity a")
	Integer countAllAppointmentPagging();

	@Query("select a from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 order by a.createdAt ASC")
	List<AppointmentEntity> searchAllByPatientWithoutDateAndStatus(String patientCode, String patientNameSearch,
			String phone, Pageable pageable);

	@Query("select count(a.id) from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3")
	Integer countAllByPatientWithoutDateAndStatus(String patientCode, String patientNameSearch, String phone);

	@Query("select a from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.status = ?4 order by a.createdAt ASC")
	List<AppointmentEntity> searchAllByPatientWithoutDate(String patientCode, String patientNameSearch, String phone,
			Integer status, Pageable pageable);

	@Query("select count(a.id) from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.status = ?4")
	Integer countAllByPatientWithoutDate(String patientCode, String patientNameSearch, String phone, Integer status);

	@Query("select a from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.dayOfExamination between ?4 and ?5 order by a.createdAt ASC")
	List<AppointmentEntity> searchAllByPatientWithoutStatus(String patientCode, String patientNameSearch, String phone,
			Date startDate, Date endDate, Pageable pageable);

	@Query("select count(a.id) from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.dayOfExamination between ?4 and ?5")
	Integer countAllByPatientWithoutStatus(String patientCode, String patientNameSearch, String phone, Date startDate,
			Date endDate);

	@Query("select a from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.dayOfExamination between ?4 and ?5 and a.status = ?6")
	List<AppointmentEntity> searchAllByPatientFullSearch(String patientCode, String patientNameSearch, String phone,
			Date startDate, Date endDate, Integer status, Pageable pageable);

	@Query("select count(a.id) from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and a.dayOfExamination between ?4 and ?5 and a.status = ?6")
	Integer countAllByPatientFullSearch(String patientCode, String patientNameSearch, String phone, Date startDate,
			Date endDate, Integer status);

	@Query("select a.time from AppointmentEntity a where a.staffByStaffId.id = ?1")
	List<String> getAllTimeByStaffId(UUID staffId);

	@Query("select distinct a.dayOfExamination from AppointmentEntity a where a.staffByStaffId.id = ?1")
	List<Date> getAllDayOfExaminationByStaffId(UUID staffId);

	@Query("select distinct a.dayOfExamination from AppointmentEntity a join a.patientByPatientId p where p.patientCode = ?1")
	List<Date> getAllDayOfExaminationByPatientCode(String patientCode);

	@Query("select distinct p from AppointmentEntity a join a.patientByPatientId p where p.patientNameSearch like ?1")
	List<PatientEntity> findByNameSearch(String name, Pageable top10);

	@Query("select distinct p from AppointmentEntity a join a.patientByPatientId p where p.phone like ?1")
	List<PatientEntity> findByPhoneSearch(String phone, Pageable top10);

	@Query("select distinct p from AppointmentEntity a join a.patientByPatientId p where p.patientCode like ?1")
	List<PatientEntity> findByCodeSearch(String patientCode, Pageable top10);
}
