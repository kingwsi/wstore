package com.wstore.search.service.repository;

import com.wstore.pojo.admin.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product,Long> {

    Page<Product> findByNameLikeAndBrandIdAndCategory(String keyword, Integer brandId, Integer categoryId, Pageable pageable);

    long countAllBy();

}
