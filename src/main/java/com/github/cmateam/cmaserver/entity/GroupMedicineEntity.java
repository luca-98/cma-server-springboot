package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_medicine")
public class GroupMedicineEntity extends BaseEntity {
    private String groupMedicineName;
    private List<MedicineEntity> medicinesById;

    @Basic
    @Column(name = "group_medicine_name")
    public String getGroupMedicineName() {
        return groupMedicineName;
    }

    public void setGroupMedicineName(String groupMedicineName) {
        this.groupMedicineName = groupMedicineName;
    }

    @OneToMany(mappedBy = "groupMedicineByGroupMedicineId")
    public List<MedicineEntity> getMedicinesById() {
        return medicinesById;
    }

    public void setMedicinesById(List<MedicineEntity> medicinesById) {
        this.medicinesById = medicinesById;
    }
}
