package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.DiseaseEntity;

public interface DiseaseRepository extends JpaRepository<DiseaseEntity, UUID> {
	@Query("select d from DiseaseEntity d where lower(d.icd10Code) like ?1 and d.status = 1 order by d.updatedAt ASC")
	List<DiseaseEntity> getAllDiseaseByCode(String icd10Code, Pageable top10);
	
	@Query("select d from DiseaseEntity d where d.diseaseNameSearch like ?1 and d.status = 1 order by d.updatedAt ASC")
	List<DiseaseEntity> getAllDiseaseByDiseaseNameSearch(String diseaseNameSearch, Pageable top10);
}
