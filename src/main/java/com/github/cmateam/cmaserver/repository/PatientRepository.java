package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PatientEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {

	@Query("select p from PatientEntity p where p.patientNameSearch like ?1 and p.status = 1")
	List<PatientEntity> findByNameSearch(String patientNameSearch, Pageable top10);

	@Query("select p from PatientEntity p where p.phone like ?1 and p.status = 1")
	List<PatientEntity> findByPhoneSearch(String phone, Pageable top10);

	@Query("select p from PatientEntity p where p.patientCode like ?1 and p.status = 1")
	List<PatientEntity> findByPatientCodeSearch(String patientCode, Pageable top10);

	@Query("select p.address from PatientEntity p where p.addressSearch like ?1 group by p.address")
	List<String> searchByAddress(String address, Pageable pageable);

	@Query("select p from PatientEntity p where p.patientCode = ?1 and p.status = 1")
	PatientEntity findByPatientCode(String patientCode);

	@Query("select p from PatientEntity p where p.phone = ?1 and p.status = 1")
	PatientEntity findByPhone(String phone);

	@Query("select p from PatientEntity p where p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> findAllByPagging(Pageable pageable);
	
	@Query("select p from PatientEntity p where p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> findAllByNoPagging();

	@Query("select count(p.id) from PatientEntity p where p.status = 1")
	Integer countAll();

	@Query("select count(p.id) from PatientEntity p where p.status = 1 and p.patientCode like ?1")
	Integer countAllPatientOld(String patientCodeOld);

	@Query("select p from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> searchAllByPatientWithoutGenderAndYearOfBirthPagging(String patientCode,
			String patientNameSearch, String phone, String addressSearch, Pageable pageable);

	@Query("select count(p.id) from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and p.status = 1")
	Integer countAllByPatientWithoutGenderAndYearOfBirthPagging(String patientCode, String patientNameSearch,
			String phone, String addressSearch);

	@Query("select p from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and p.gender = ?5 and p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> searchAllByPatientWithGenderPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer gender, Pageable pageable);

	@Query("select count(p.id) from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and p.gender = ?5 and p.status = 1")
	Integer countAllByPatientWithGenderPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer gender);

	@Query("select p from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and EXTRACT(year from p.dateOfBirth) = ?5 and p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> searchAllByPatientWithYearOfBirthPagging(String patientCode, String patientNameSearch,
			String phone, String addressSearch, Integer yearOfBirth, Pageable pageable);

	@Query("select count(p.id) from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and EXTRACT(year from p.dateOfBirth) = ?5 and p.status = 1")
	Integer countAllByPatientWithYearOfBirthPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer yearOfBirth);

	@Query("select p from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and EXTRACT(year from p.dateOfBirth) = ?5 and p.gender = ?6 and p.status = 1 order by p.createdAt DESC")
	List<PatientEntity> searchAllByPatientWithAllPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer yearOfBirth, Integer gender, Pageable pageable);

	@Query("select count(p.id) from PatientEntity p where p.patientCode like ?1 and p.patientNameSearch like ?2 and p.phone like ?3 and p.addressSearch like ?4 and EXTRACT(year from p.dateOfBirth) = ?5 and p.gender = ?6 and p.status = 1")
	Integer countAllByPatientWithAllPagging(String patientCode, String patientNameSearch, String phone,
			String addressSearch, Integer yearOfBirth, Integer gender);

	@Query("select r.patientByPatientId.id from ReceivePatientEntity r where r.status <> 0")
	List<UUID> getAllPatientInReceiveWithStatus();
}