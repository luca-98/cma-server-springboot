package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "count_id", schema = "cma", catalog = "postgres")
public class CountIdEntity extends BaseEntity {
    private String countName;
    private Integer countValue;

    @Basic
    @Column(name = "count_name")
    public String getCountName() {
        return this.countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    @Basic
    @Column(name = "count_value")
    public Integer getCountValue() {
        return this.countValue;
    }

    public void setCountValue(Integer countValue) {
        this.countValue = countValue;
    }

}
