package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.MedicineSaleDetailEntity;
import com.github.cmateam.cmaserver.entity.MedicineSaleEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;

public interface MedicineSaleRepository extends JpaRepository<MedicineSaleEntity, UUID> {
	@Query("select p2 from PatientEntity p join p.medicalExaminationsById me join me.prescriptionsById p2 where p.id = ?1")
	PrescriptionEntity getPrescriptionByPatientId(UUID patientId);

	@Query("from MedicineSaleEntity ms where ms.status <> 0 order by ms.createdAt DESC")
	List<MedicineSaleEntity> getMedicineSalePagging(Pageable pageable);

	@Query("select count(ms.id) from MedicineSaleEntity ms where ms.status <> 0")
	Integer countAllMedicineSalePagging();

	@Query("select msd from MedicineSaleDetailEntity msd where msd.medicineSaleByMedicineSaleId.id = ?1 order by msd.createdAt DESC")
	List<MedicineSaleDetailEntity> getMedicineSaleDetailByMedicineSale(UUID medicineSaleDetailId);

	@Query("select distinct s from StaffEntity s join s.medicineSalesById ms where s.staffNameSearch like ?1")
	List<StaffEntity> autoSearchByNameSearchStaff(String staffNameSearch, Pageable top10);

	@Query("select distinct p2 from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where p2.patientNameSearch like ?1")
	List<PatientEntity> autoSearchByNameSearchPatient(String patientNameSearch, Pageable top10);

	@Query("select distinct p2 from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where lower(p2.patientCode) like ?1")
	List<PatientEntity> autoSearchByPatientCode(String patientCode, Pageable top10);
	
	@Query("select ms from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where p2.patientNameSearch like ?1 and s.staffNameSearch like ?2 and lower(p2.patientCode) like ?3 order by ms.createdAt DESC")
	List<MedicineSaleEntity> searchAllMedicineSaleWithoutDate(String patientNameSearch, String staffNameSearch,String patientCode,
			Pageable pageable);

	@Query("select count(ms.id) from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where p2.patientNameSearch like ?1 and s.staffNameSearch like ?2 and lower(p2.patientCode) like ?3")
	Integer countAllMedicineSaleWithoutDate(String patientNameSearch, String staffNameSearch,String patientCode);

	@Query("select ms from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where p2.patientNameSearch like ?1 and s.staffNameSearch like ?2 and ms.status <> 0 and date_trunc('day', ms.createdAt) between ?3 and ?4 and lower(p2.patientCode) like ?5 order by ms.createdAt DESC")
	List<MedicineSaleEntity> searchAllMedicineSaleWithDate(String patientNameSearch, String staffNameSearch,
			Date startDate, Date endDate,String patientCode, Pageable pageable);

	@Query("select count(ms.id) from StaffEntity s join s.medicineSalesById ms join ms.patientByPatientId p2 where p2.patientNameSearch like ?1 and s.staffNameSearch like ?2 and ms.status <> 0 and date_trunc('day', ms.createdAt) between ?3 and ?4 and lower(p2.patientCode) like ?5")
	Integer countAllMedicineSaleWithDate(String patientNameSearch, String staffNameSearch, Date startDate,
			Date endDate,String patientCode);

}
