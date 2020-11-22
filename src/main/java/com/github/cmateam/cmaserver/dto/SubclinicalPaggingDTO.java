package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class SubclinicalPaggingDTO {
    Integer pageIndex;
    Integer pageSize;
    Integer totalRecord;
    List<SubclinicalManage> listData;

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

    public List<SubclinicalManage> getListData() {
        return this.listData;
    }

    public void setListData(List<SubclinicalManage> listData) {
        this.listData = listData;
    }

}
