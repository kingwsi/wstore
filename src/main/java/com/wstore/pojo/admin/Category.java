package com.wstore.pojo.admin;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Category {

    @Id
    private Integer id;

    private String name;

    private Integer sort;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @Transient
    private List<CategorySecondary> categorySecondary;

    @Transient
    private List<CommonProperty> commonProperties;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<CategorySecondary> getCategorySecondary() {
        return categorySecondary;
    }

    public void setCategorySecondary(List<CategorySecondary> categorySecondary) {
        this.categorySecondary = categorySecondary;
    }

    public List<CommonProperty> getCommonProperties() {
        return commonProperties;
    }

    public void setCommonProperties(List<CommonProperty> commonProperties) {
        this.commonProperties = commonProperties;
    }
}