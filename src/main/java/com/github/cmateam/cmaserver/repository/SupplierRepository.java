package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.SupplierEntity;

public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {
	@Query("select s from SupplierEntity s where s.supplierName like ?1 and s.status <> 0")
	List<SupplierEntity> findByNameSearch(String supplierNameSearch, Pageable top10);

	@Query("select s from SupplierEntity s where s.address like ?1 and s.status <> 0")
	List<SupplierEntity> findByAddressSearch(String address, Pageable top10);
	
	@Query("select s from SupplierEntity s where s.phone like ?1 and s.status <> 0")
	List<SupplierEntity> findByPhone(String phone, Pageable top10);
	
	@Query("select s from SupplierEntity s where s.email like ?1 and s.status <> 0")
	List<SupplierEntity> findByEmail(String email, Pageable top10);
	
	@Query("select s from SupplierEntity s where s.accountNumber like ?1 and s.status <> 0")
	List<SupplierEntity> findByAccountNumber(String accountNumber, Pageable top10);

}
