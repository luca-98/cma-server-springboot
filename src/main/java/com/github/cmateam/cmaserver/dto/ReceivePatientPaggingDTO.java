package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class ReceivePatientPaggingDTO {
    Integer pageIndex;
    Integer pageSize;
    Integer totalRecord;
    List<ReceivePatientDTO> listData;

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return this.totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<ReceivePatientDTO> getListData() {
        return this.listData;
    }

    public void setListData(List<ReceivePatientDTO> listData) {
        this.listData = listData;
    }

}
