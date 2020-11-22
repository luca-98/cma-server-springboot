package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ManageVoucherDTO {
    private UUID id;
    private Integer type;
    private String objectVoucher;
    private Date createdAt;
    private String staffName;
    private String note;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getObjectVoucher() {
		return objectVoucher;
	}

	public void setObjectVoucher(String objectVoucher) {
		this.objectVoucher = objectVoucher;
	}

	public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
