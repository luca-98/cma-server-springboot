package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class MedicalExamPaggingDTO {
    Integer pageIndex;
    Integer pageSize;
    Integer totalRecord;
    List<MedicalExamTableDTO> listData;

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

    public List<MedicalExamTableDTO> getListData() {
        return this.listData;
    }

    public void setListData(List<MedicalExamTableDTO> listData) {
        this.listData = listData;
    }

}
