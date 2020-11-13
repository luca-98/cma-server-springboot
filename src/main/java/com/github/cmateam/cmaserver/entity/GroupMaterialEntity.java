package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_material", schema = "cma", catalog = "postgres")
public class GroupMaterialEntity extends BaseEntity {
    private String groupMaterialName;
    private List<MaterialEntity> materialsById;

    @Basic
    @Column(name = "group_material_name")
    public String getGroupMaterialName() {
        return groupMaterialName;
    }

    public void setGroupMaterialName(String groupMaterialName) {
        this.groupMaterialName = groupMaterialName;
    }

    @OneToMany(mappedBy = "groupMaterialByGroupMaterialId")
    public List<MaterialEntity> getMaterialsById() {
        return materialsById;
    }

    public void setMaterialsById(List<MaterialEntity> materialsById) {
        this.materialsById = materialsById;
    }
}
