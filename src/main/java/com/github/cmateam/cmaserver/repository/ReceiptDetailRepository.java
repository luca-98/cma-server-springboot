package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ReceiptDetailEntity;

public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetailEntity, UUID> {

}
