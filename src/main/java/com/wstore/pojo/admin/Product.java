package com.wstore.pojo.admin;


import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Product {
    @Id
    private Long id;

    private String name;

    private String subName;

    private String code;

    private String mainPic;

    private Integer category;

    private Integer minPrice;

    private Integer maxPrice;

    private Date onsaleTime;

    private Date offsaleTime;

    private Integer status;

    private Integer saleCount;

    private Integer viewCount;

    private Integer commentCount;

    private Integer commentScore;

    private Integer goodCommentcount;

    private Integer commonCommentCount;

    private Integer badCommentCount;

    private Integer brandId;

    private Date createTime;

    private Date updateTime;

    /*数据库连表查询封装*/

    @Transient
    private List<ProductImage> productImages;

    @Transient
    private List<ProductExtend> productExtends;

    @Transient
    private List<ProductProperty> productProperties;

    private String categoryName;

    /*前台发送到后台数据封装字段*/
    //图片集封装字段
    private String images;

    //扩展信息
    private String extend;

    //扩展参数
    private String param;

    //复选框属性集
    private String properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName == null ? null : subName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic == null ? null : mainPic.trim();
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Date getOnsaleTime() {
        return onsaleTime;
    }

    public void setOnsaleTime(Date onsaleTime) {
        this.onsaleTime = onsaleTime;
    }

    public Date getOffsaleTime() {
        return offsaleTime;
    }

    public void setOffsaleTime(Date offsaleTime) {
        this.offsaleTime = offsaleTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public Integer getGoodCommentcount() {
        return goodCommentcount;
    }

    public void setGoodCommentcount(Integer goodCommentcount) {
        this.goodCommentcount = goodCommentcount;
    }

    public Integer getCommonCommentCount() {
        return commonCommentCount;
    }

    public void setCommonCommentCount(Integer commonCommentCount) {
        this.commonCommentCount = commonCommentCount;
    }

    public Integer getBadCommentCount() {
        return badCommentCount;
    }

    public void setBadCommentCount(Integer badCommentCount) {
        this.badCommentCount = badCommentCount;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductExtend> getProductExtends() {
        return productExtends;
    }

    public void setProductExtends(List<ProductExtend> productExtends) {
        this.productExtends = productExtends;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductProperty> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(List<ProductProperty> productProperties) {
        this.productProperties = productProperties;
    }
}