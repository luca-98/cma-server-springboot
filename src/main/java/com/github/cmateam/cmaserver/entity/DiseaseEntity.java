package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "disease", schema = "cma", catalog = "postgres")
public class DiseaseEntity extends BaseEntity {
    private String icd10Code;
    private String diseaseName;
    private String diseaseNameSearch;
    @Basic
    @Column(name = "icd10_code")
    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    @Basic
    @Column(name = "disease_name")
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Basic
    @Column(name = "disease_name_search")
    public String getDiseaseNameSearch() {
        return diseaseNameSearch;
    }

    public void setDiseaseNameSearch(String diseaseNameSearch) {
        this.diseaseNameSearch = diseaseNameSearch;
    }
}
