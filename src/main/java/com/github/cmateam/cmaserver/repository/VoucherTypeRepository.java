package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.VoucherTypeEntity;

public interface VoucherTypeRepository extends JpaRepository<VoucherTypeEntity, UUID> {
	@Query("from VoucherTypeEntity vt where vt.typeName like ?1 and vt.status <> 0")
	public VoucherTypeEntity getVoucherTypeEntityByName(String name);

	@Query("select v from VoucherTypeEntity v where v.status <> 0 order by v.createdAt desc")
	public List<VoucherTypeEntity> lstVoucherTypes();
}
