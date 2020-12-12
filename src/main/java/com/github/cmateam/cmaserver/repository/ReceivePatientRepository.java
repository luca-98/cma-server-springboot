package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ReceivePatientEntity;

public interface ReceivePatientRepository extends JpaRepository<ReceivePatientEntity, UUID> {

	@Query("SELECT count(r.id) FROM ReceivePatientEntity r WHERE date_trunc('day', r.createdAt)=date_trunc('day', now())")
	Integer countReceive();

	@Query("FROM ReceivePatientEntity r WHERE date_trunc('day', r.createdAt)=date_trunc('day', now()) ORDER BY r.createdAt DESC")
	List<ReceivePatientEntity> findAllWithPagging(Pageable pageable);

	@Query("select count(r.id) from ReceivePatientEntity r join r.ordinalNumberByOrdinalNumberId o join o.roomServiceByRoomServiceId rs join rs.staffsByStaffId s where s.id=?1 and r.status = 1 and date_trunc('day', r.createdAt)=date_trunc('day', now())")
	Integer countReceiveByStaffId(UUID id);

	@Query("select count(o.id) from OrdinalNumberEntity o join o.staffByStaffId staff where staff.id=?1 and o.status=2 and date_trunc('day', o.dayOfExamination)=date_trunc('day', now())")
	Integer countOrdinalByStaffId(UUID id);

	@Query("select distinct r from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and r.status = 1 order by r.createdAt DESC")
	List<ReceivePatientEntity> searchReceiveWithoutDateAndStatus(String roomName, String patientCode,
			String patientNameSearch, String phone, String fullName, Pageable pageable);

	@Query("select count(distinct r.id) from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and r.status = 1")
	Integer countReceiveWithoutDateAndStatus(String roomName, String patientCode, String patientNameSearch, String phone,
			String fullName);

	@Query("select distinct r from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and me.status = ?6 and r.status = 1 order by r.createdAt DESC")
	List<ReceivePatientEntity> searchReceiveWithStatus(String roomName, String patientCode, String patientNameSearch,
			String phone, String fullName, Integer status, Pageable pageable);

	@Query("select count(distinct r.id) from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and me.status = ?6 and r.status = 1")
	Integer countReceiveWithStatus(String roomName, String patientCode, String patientNameSearch, String phone,
			String fullName, Integer status);

	@Query("select distinct r from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and to_char( me.createdAt, 'DD/MM/YYYY') = ?6 and r.status = 1 order by r.createdAt DESC")
	List<ReceivePatientEntity> searchReceiveWithDate(String roomName, String patientCode, String patientNameSearch,
			String phone, String fullName, String date, Pageable pageable);

	@Query("select count(distinct r.id) from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and to_char( me.createdAt, 'DD/MM/YYYY') = ?6 and r.status = 1")
	Integer countReceiveWithDate(String roomName, String patientCode, String patientNameSearch, String phone, String fullName,
			String date);

	@Query("select distinct r from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and to_char( me.createdAt, 'DD/MM/YYYY') = ?6 and me.status = ?7 and r.status = 1 order by r.createdAt DESC")
	List<ReceivePatientEntity> searchReceiveWithDateAndStatus(String roomName, String patientCode, String patientNameSearch,
			String phone, String fullName, String date, Integer status, Pageable pageable);

	@Query("select count(distinct r.id) from ReceivePatientEntity r join r.patientByPatientId p join p.medicalExaminationsById me join me.staffByStaffId s join s.roomServicesById rs where rs.roomName like ?1 and p.patientCode like ?2 and p.patientNameSearch like ?3 and p.phone like ?4 and s.fullName like ?5 and to_char( me.createdAt, 'DD/MM/YYYY') = ?6 and me.status = ?7 and r.status = 1")
	Integer countReceiveWithDateAndStatus(String roomName, String patientCode, String patientNameSearch, String phone,
			String fullName, String date, Integer status);

	@Query("FROM ReceivePatientEntity r JOIN r.patientByPatientId p WHERE p.patientCode=?1 AND r.status NOT IN(0,3,5) AND date_trunc('day', r.createdAt)=date_trunc('day', now())")
	ReceivePatientEntity findReceiveByPatientCode(String patientCode);

	@Query("FROM ReceivePatientEntity r JOIN r.patientByPatientId p WHERE p.phone=?1 AND r.status NOT IN(0,3,5) AND date_trunc('day', r.createdAt)=date_trunc('day', now())")
	ReceivePatientEntity findReceiveByPhone(String phone);
}
