package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class VoucherTypeDTO {
	private UUID voucherTypeId;
	private String voucherTypeName;

	public UUID getVoucherTypeId() {
		return voucherTypeId;
	}

	public void setVoucherTypeId(UUID voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}

	public String getVoucherTypeName() {
		return voucherTypeName;
	}

	public void setVoucherTypeName(String voucherTypeName) {
		this.voucherTypeName = voucherTypeName;
	}

}
