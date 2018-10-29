package com.wstore.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.common.utils.WstoreUtils;
import com.wstore.mapper.ProductMapper;
import com.wstore.pojo.admin.Product;
import com.wstore.search.service.repository.ProductRepository;
import com.wstore.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import com.wstore.pojo.search.Page;

import java.util.*;

/**
 * @ClassName SearchServiceImpl
 * @Author Koi
 * @Date 2018/8/15 15:32
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    protected static Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Value("${product.section.size}")
    private Integer PRODUCT_SECTION_SIZE;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 批量上架商品
     *
     * 返回所有已上架商品
     *
     * @return
     */
    @Override
    public long batchOnSale() {
        productMapper.batchOnSale();
        long count = productMapper.countByExample(null);
        long k = count % PRODUCT_SECTION_SIZE == 0 ? count / PRODUCT_SECTION_SIZE : count / PRODUCT_SECTION_SIZE + 1;
        for (int i = 0; i < k; i++) {
            List<Product> products = productMapper.selectWithCategory(i * PRODUCT_SECTION_SIZE, PRODUCT_SECTION_SIZE);
            bulkIndex(products);
//            productRepository.deleteAll(products);
        }
        return count;
    }

    /**
     * 添加商品信息到es
     *
     * @param product
     */
    @Override
    public void addProductToES(Product product) {
        try {
            Product toElasticsearch = productMapper.selectByPrimaryKey(product.getId());
            productRepository.index(toElasticsearch);
            logger.info("添加到ES索引成功:ProductID====" + product.getId());
        } catch (ElasticsearchException elasticsearch) {
            logger.error("添加到Elasticsearch失败:ProductID====" + product.getId());
            throw elasticsearch;
        }
    }

    /**
     * 批量插入
     *
     * @param ProductList
     */
    @Override
    public void bulkIndex(List<Product> ProductList) {
        int counter = 0;
        try {
            if (!elasticsearchTemplate.indexExists("item")) {
                elasticsearchTemplate.createIndex("product");
            }
            List<IndexQuery> queries = new ArrayList<>();
            for (Product Product : ProductList) {
                //插入之前检验商品是否满足上架条件
                if (productMapper.findSkuCount(Product.getCode()) > 0) {
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setId(Product.getId() + "");
                    indexQuery.setObject(Product);
                    indexQuery.setIndexName("item");
                    indexQuery.setType("product");
                    queries.add(indexQuery);
                    if (counter % 500 == 0) {
                        elasticsearchTemplate.bulkIndex(queries);
                        queries.clear();
                        System.out.println("bulkIndex counter : " + counter);
                    }
                    counter++;
                }
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            System.out.println("bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }

    /**
     * 从elasticsearch移除怕product
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product) {
        try {
            productRepository.delete(product);
            logger.info("添加到ES索引成功:ProductID====" + product.getId());
        } catch (ElasticsearchException elasticsearch) {
            logger.error("添加到Elasticsearch失败:ProductID====" + product.getId());
            throw elasticsearch;
        }
    }

    @Override
    public Page<Product> searchProduct(String keyword, Integer category, Integer brand, Pageable pageable) {
        //return productRepository.findByNameLikeAndBrandIdAndCategory(keyword, brand, category, pageable);
        org.springframework.data.domain.Page<Product> page
                = productRepository.findByNameLikeAndBrandIdAndCategory(keyword, brand, category, pageable);
        com.wstore.pojo.search.Page<Product> productPage = new com.wstore.pojo.search.Page<>();

        productPage.setList(page.getContent());
        productPage.setNum(page.getNumber());
        productPage.setFirstPage(page.isFirst());
        productPage.setLastPage(page.isLast());
        productPage.setPages(page.getTotalPages());
        productPage.setNavigatepageNums(WstoreUtils.paging(page.getTotalPages(), page.getNumber() + 1, 5));

        return productPage;
    }

    @Override
    public List<Product> getProduct(Integer startRow, Integer endRow) {
        return productMapper.selectWithCategory(startRow, endRow);
    }
}
