package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "material", schema = "cma", catalog = "postgres")
public class MaterialEntity extends BaseEntity {
    private String materialName;
    private String materialNameSearch;
    private String unitName;
    private Integer quantity;
    private GroupMaterialEntity groupMaterialByGroupMaterialId;
    private List<ReceiptDetailEntity> receiptDetailsById;

    @Basic
    @Column(name = "material_name")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Basic
    @Column(name = "material_name_search")
    public String getMaterialNameSearch() {
        return this.materialNameSearch;
    }

    public void setMaterialNameSearch(String materialNameSearch) {
        this.materialNameSearch = materialNameSearch;
    }

    @Basic
    @Column(name = "unit_name")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "group_material_id", referencedColumnName = "id")
    public GroupMaterialEntity getGroupMaterialByGroupMaterialId() {
        return groupMaterialByGroupMaterialId;
    }

    public void setGroupMaterialByGroupMaterialId(GroupMaterialEntity groupMaterialByGroupMaterialId) {
        this.groupMaterialByGroupMaterialId = groupMaterialByGroupMaterialId;
    }

    @OneToMany(mappedBy = "materialByMaterialId")
    public List<ReceiptDetailEntity> getReceiptDetailsById() {
        return receiptDetailsById;
    }

    public void setReceiptDetailsById(List<ReceiptDetailEntity> receiptDetailsById) {
        this.receiptDetailsById = receiptDetailsById;
    }
}
