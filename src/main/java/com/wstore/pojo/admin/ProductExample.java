package com.wstore.pojo.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSubNameIsNull() {
            addCriterion("sub_name is null");
            return (Criteria) this;
        }

        public Criteria andSubNameIsNotNull() {
            addCriterion("sub_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubNameEqualTo(String value) {
            addCriterion("sub_name =", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameNotEqualTo(String value) {
            addCriterion("sub_name <>", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameGreaterThan(String value) {
            addCriterion("sub_name >", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameGreaterThanOrEqualTo(String value) {
            addCriterion("sub_name >=", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameLessThan(String value) {
            addCriterion("sub_name <", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameLessThanOrEqualTo(String value) {
            addCriterion("sub_name <=", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameLike(String value) {
            addCriterion("sub_name like", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameNotLike(String value) {
            addCriterion("sub_name not like", value, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameIn(List<String> values) {
            addCriterion("sub_name in", values, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameNotIn(List<String> values) {
            addCriterion("sub_name not in", values, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameBetween(String value1, String value2) {
            addCriterion("sub_name between", value1, value2, "subName");
            return (Criteria) this;
        }

        public Criteria andSubNameNotBetween(String value1, String value2) {
            addCriterion("sub_name not between", value1, value2, "subName");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andMainPicIsNull() {
            addCriterion("main_pic is null");
            return (Criteria) this;
        }

        public Criteria andMainPicIsNotNull() {
            addCriterion("main_pic is not null");
            return (Criteria) this;
        }

        public Criteria andMainPicEqualTo(String value) {
            addCriterion("main_pic =", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicNotEqualTo(String value) {
            addCriterion("main_pic <>", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicGreaterThan(String value) {
            addCriterion("main_pic >", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicGreaterThanOrEqualTo(String value) {
            addCriterion("main_pic >=", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicLessThan(String value) {
            addCriterion("main_pic <", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicLessThanOrEqualTo(String value) {
            addCriterion("main_pic <=", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicLike(String value) {
            addCriterion("main_pic like", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicNotLike(String value) {
            addCriterion("main_pic not like", value, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicIn(List<String> values) {
            addCriterion("main_pic in", values, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicNotIn(List<String> values) {
            addCriterion("main_pic not in", values, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicBetween(String value1, String value2) {
            addCriterion("main_pic between", value1, value2, "mainPic");
            return (Criteria) this;
        }

        public Criteria andMainPicNotBetween(String value1, String value2) {
            addCriterion("main_pic not between", value1, value2, "mainPic");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(Integer value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(Integer value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(Integer value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(Integer value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<Integer> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<Integer> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(Integer value1, Integer value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andMinPriceIsNull() {
            addCriterion("min_price is null");
            return (Criteria) this;
        }

        public Criteria andMinPriceIsNotNull() {
            addCriterion("min_price is not null");
            return (Criteria) this;
        }

        public Criteria andMinPriceEqualTo(Integer value) {
            addCriterion("min_price =", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotEqualTo(Integer value) {
            addCriterion("min_price <>", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceGreaterThan(Integer value) {
            addCriterion("min_price >", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_price >=", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceLessThan(Integer value) {
            addCriterion("min_price <", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceLessThanOrEqualTo(Integer value) {
            addCriterion("min_price <=", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceIn(List<Integer> values) {
            addCriterion("min_price in", values, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotIn(List<Integer> values) {
            addCriterion("min_price not in", values, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceBetween(Integer value1, Integer value2) {
            addCriterion("min_price between", value1, value2, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("min_price not between", value1, value2, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIsNull() {
            addCriterion("max_price is null");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIsNotNull() {
            addCriterion("max_price is not null");
            return (Criteria) this;
        }

        public Criteria andMaxPriceEqualTo(Integer value) {
            addCriterion("max_price =", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotEqualTo(Integer value) {
            addCriterion("max_price <>", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceGreaterThan(Integer value) {
            addCriterion("max_price >", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_price >=", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceLessThan(Integer value) {
            addCriterion("max_price <", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceLessThanOrEqualTo(Integer value) {
            addCriterion("max_price <=", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIn(List<Integer> values) {
            addCriterion("max_price in", values, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotIn(List<Integer> values) {
            addCriterion("max_price not in", values, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceBetween(Integer value1, Integer value2) {
            addCriterion("max_price between", value1, value2, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("max_price not between", value1, value2, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeIsNull() {
            addCriterion("onSale_time is null");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeIsNotNull() {
            addCriterion("onSale_time is not null");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeEqualTo(Date value) {
            addCriterion("onSale_time =", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeNotEqualTo(Date value) {
            addCriterion("onSale_time <>", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeGreaterThan(Date value) {
            addCriterion("onSale_time >", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("onSale_time >=", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeLessThan(Date value) {
            addCriterion("onSale_time <", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeLessThanOrEqualTo(Date value) {
            addCriterion("onSale_time <=", value, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeIn(List<Date> values) {
            addCriterion("onSale_time in", values, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeNotIn(List<Date> values) {
            addCriterion("onSale_time not in", values, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeBetween(Date value1, Date value2) {
            addCriterion("onSale_time between", value1, value2, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOnsaleTimeNotBetween(Date value1, Date value2) {
            addCriterion("onSale_time not between", value1, value2, "onsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeIsNull() {
            addCriterion("offSale_time is null");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeIsNotNull() {
            addCriterion("offSale_time is not null");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeEqualTo(Date value) {
            addCriterion("offSale_time =", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeNotEqualTo(Date value) {
            addCriterion("offSale_time <>", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeGreaterThan(Date value) {
            addCriterion("offSale_time >", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("offSale_time >=", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeLessThan(Date value) {
            addCriterion("offSale_time <", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeLessThanOrEqualTo(Date value) {
            addCriterion("offSale_time <=", value, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeIn(List<Date> values) {
            addCriterion("offSale_time in", values, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeNotIn(List<Date> values) {
            addCriterion("offSale_time not in", values, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeBetween(Date value1, Date value2) {
            addCriterion("offSale_time between", value1, value2, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andOffsaleTimeNotBetween(Date value1, Date value2) {
            addCriterion("offSale_time not between", value1, value2, "offsaleTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSaleCountIsNull() {
            addCriterion("sale_count is null");
            return (Criteria) this;
        }

        public Criteria andSaleCountIsNotNull() {
            addCriterion("sale_count is not null");
            return (Criteria) this;
        }

        public Criteria andSaleCountEqualTo(Integer value) {
            addCriterion("sale_count =", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountNotEqualTo(Integer value) {
            addCriterion("sale_count <>", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountGreaterThan(Integer value) {
            addCriterion("sale_count >", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sale_count >=", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountLessThan(Integer value) {
            addCriterion("sale_count <", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountLessThanOrEqualTo(Integer value) {
            addCriterion("sale_count <=", value, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountIn(List<Integer> values) {
            addCriterion("sale_count in", values, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountNotIn(List<Integer> values) {
            addCriterion("sale_count not in", values, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountBetween(Integer value1, Integer value2) {
            addCriterion("sale_count between", value1, value2, "saleCount");
            return (Criteria) this;
        }

        public Criteria andSaleCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sale_count not between", value1, value2, "saleCount");
            return (Criteria) this;
        }

        public Criteria andViewCountIsNull() {
            addCriterion("view_count is null");
            return (Criteria) this;
        }

        public Criteria andViewCountIsNotNull() {
            addCriterion("view_count is not null");
            return (Criteria) this;
        }

        public Criteria andViewCountEqualTo(Integer value) {
            addCriterion("view_count =", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotEqualTo(Integer value) {
            addCriterion("view_count <>", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountGreaterThan(Integer value) {
            addCriterion("view_count >", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("view_count >=", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountLessThan(Integer value) {
            addCriterion("view_count <", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountLessThanOrEqualTo(Integer value) {
            addCriterion("view_count <=", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountIn(List<Integer> values) {
            addCriterion("view_count in", values, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotIn(List<Integer> values) {
            addCriterion("view_count not in", values, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountBetween(Integer value1, Integer value2) {
            addCriterion("view_count between", value1, value2, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotBetween(Integer value1, Integer value2) {
            addCriterion("view_count not between", value1, value2, "viewCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNull() {
            addCriterion("comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNotNull() {
            addCriterion("comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCountEqualTo(Integer value) {
            addCriterion("comment_count =", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotEqualTo(Integer value) {
            addCriterion("comment_count <>", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThan(Integer value) {
            addCriterion("comment_count >", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_count >=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThan(Integer value) {
            addCriterion("comment_count <", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_count <=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIn(List<Integer> values) {
            addCriterion("comment_count in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotIn(List<Integer> values) {
            addCriterion("comment_count not in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_count between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_count not between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentScoreIsNull() {
            addCriterion("comment_score is null");
            return (Criteria) this;
        }

        public Criteria andCommentScoreIsNotNull() {
            addCriterion("comment_score is not null");
            return (Criteria) this;
        }

        public Criteria andCommentScoreEqualTo(Integer value) {
            addCriterion("comment_score =", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreNotEqualTo(Integer value) {
            addCriterion("comment_score <>", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreGreaterThan(Integer value) {
            addCriterion("comment_score >", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_score >=", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreLessThan(Integer value) {
            addCriterion("comment_score <", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreLessThanOrEqualTo(Integer value) {
            addCriterion("comment_score <=", value, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreIn(List<Integer> values) {
            addCriterion("comment_score in", values, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreNotIn(List<Integer> values) {
            addCriterion("comment_score not in", values, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreBetween(Integer value1, Integer value2) {
            addCriterion("comment_score between", value1, value2, "commentScore");
            return (Criteria) this;
        }

        public Criteria andCommentScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_score not between", value1, value2, "commentScore");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountIsNull() {
            addCriterion("good_commentcount is null");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountIsNotNull() {
            addCriterion("good_commentcount is not null");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountEqualTo(Integer value) {
            addCriterion("good_commentcount =", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountNotEqualTo(Integer value) {
            addCriterion("good_commentcount <>", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountGreaterThan(Integer value) {
            addCriterion("good_commentcount >", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("good_commentcount >=", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountLessThan(Integer value) {
            addCriterion("good_commentcount <", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountLessThanOrEqualTo(Integer value) {
            addCriterion("good_commentcount <=", value, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountIn(List<Integer> values) {
            addCriterion("good_commentcount in", values, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountNotIn(List<Integer> values) {
            addCriterion("good_commentcount not in", values, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountBetween(Integer value1, Integer value2) {
            addCriterion("good_commentcount between", value1, value2, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andGoodCommentcountNotBetween(Integer value1, Integer value2) {
            addCriterion("good_commentcount not between", value1, value2, "goodCommentcount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountIsNull() {
            addCriterion("common_comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountIsNotNull() {
            addCriterion("common_comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountEqualTo(Integer value) {
            addCriterion("common_comment_count =", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountNotEqualTo(Integer value) {
            addCriterion("common_comment_count <>", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountGreaterThan(Integer value) {
            addCriterion("common_comment_count >", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("common_comment_count >=", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountLessThan(Integer value) {
            addCriterion("common_comment_count <", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("common_comment_count <=", value, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountIn(List<Integer> values) {
            addCriterion("common_comment_count in", values, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountNotIn(List<Integer> values) {
            addCriterion("common_comment_count not in", values, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("common_comment_count between", value1, value2, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andCommonCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("common_comment_count not between", value1, value2, "commonCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountIsNull() {
            addCriterion("bad_comment_count is null");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountIsNotNull() {
            addCriterion("bad_comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountEqualTo(Integer value) {
            addCriterion("bad_comment_count =", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountNotEqualTo(Integer value) {
            addCriterion("bad_comment_count <>", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountGreaterThan(Integer value) {
            addCriterion("bad_comment_count >", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bad_comment_count >=", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountLessThan(Integer value) {
            addCriterion("bad_comment_count <", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("bad_comment_count <=", value, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountIn(List<Integer> values) {
            addCriterion("bad_comment_count in", values, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountNotIn(List<Integer> values) {
            addCriterion("bad_comment_count not in", values, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("bad_comment_count between", value1, value2, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBadCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bad_comment_count not between", value1, value2, "badCommentCount");
            return (Criteria) this;
        }

        public Criteria andBrandidIsNull() {
            addCriterion("brandId is null");
            return (Criteria) this;
        }

        public Criteria andBrandidIsNotNull() {
            addCriterion("brandId is not null");
            return (Criteria) this;
        }

        public Criteria andBrandidEqualTo(Integer value) {
            addCriterion("brandId =", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidNotEqualTo(Integer value) {
            addCriterion("brandId <>", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidGreaterThan(Integer value) {
            addCriterion("brandId >", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidGreaterThanOrEqualTo(Integer value) {
            addCriterion("brandId >=", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidLessThan(Integer value) {
            addCriterion("brandId <", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidLessThanOrEqualTo(Integer value) {
            addCriterion("brandId <=", value, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidIn(List<Integer> values) {
            addCriterion("brandId in", values, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidNotIn(List<Integer> values) {
            addCriterion("brandId not in", values, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidBetween(Integer value1, Integer value2) {
            addCriterion("brandId between", value1, value2, "brandid");
            return (Criteria) this;
        }

        public Criteria andBrandidNotBetween(Integer value1, Integer value2) {
            addCriterion("brandId not between", value1, value2, "brandid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}