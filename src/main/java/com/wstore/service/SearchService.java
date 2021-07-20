package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.mapper.ProductMapper;
import com.wstore.pojo.admin.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.wstore.pojo.search.Page;

import java.util.*;

/**
 * @ClassName SearchServiceImpl
 * @Author Koi
 * @Date 2018/8/15 15:32
 * @Version 1.0
 */
@Service
public class SearchService {

    protected static Logger logger = LoggerFactory.getLogger(SearchService.class);

    private final static Integer PRODUCT_SECTION_SIZE = 500;

    @Autowired
    ProductMapper productMapper;


    /**
     * 批量上架商品
     *
     * 返回所有已上架商品
     *
     * @return
     */

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

    public void addProductToES(Product product) {
        logger.warn("addProductToES->{}", product);
//        try {
//            Product toElasticsearch = productMapper.selectByPrimaryKey(product.getId());
//            productRepository.index(toElasticsearch);
//            logger.info("添加到ES索引成功:ProductID====" + product.getId());
//        } catch (ElasticsearchException elasticsearch) {
//            logger.error("添加到Elasticsearch失败:ProductID====" + product.getId());
//            throw elasticsearch;
//        }
    }

    /**
     * 批量插入
     *
     * @param
     */

    public void bulkIndex(List<Product> ProductList) {
        logger.warn("批量插入-ProductList");
//        int counter = 0;
//        try {
//            if (!elasticsearchTemplate.indexExists("item")) {
//                elasticsearchTemplate.createIndex("product");
//            }
//            List<IndexQuery> queries = new ArrayList<>();
//            for (Product Product : ProductList) {
//                //插入之前检验商品是否满足上架条件
//                if (productMapper.findSkuCount(Product.getCode()) > 0) {
//                    IndexQuery indexQuery = new IndexQuery();
//                    indexQuery.setId(Product.getId() + "");
//                    indexQuery.setObject(Product);
//                    indexQuery.setIndexName("item");
//                    indexQuery.setType("product");
//                    queries.add(indexQuery);
//                    if (counter % 500 == 0) {
//                        elasticsearchTemplate.bulkIndex(queries);
//                        queries.clear();
//                        System.out.println("bulkIndex counter : " + counter);
//                    }
//                    counter++;
//                }
//            }
//            if (queries.size() > 0) {
//                elasticsearchTemplate.bulkIndex(queries);
//            }
//            System.out.println("bulkIndex completed.");
//        } catch (Exception e) {
//            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
//            throw e;
//        }
    }

    /**
     * 从elasticsearch移除怕product
     *
     * @param product
     */

    public void removeProduct(Product product) {
        logger.info("removeProduct..");
//        try {
//            productRepository.delete(product);
//            logger.info("添加到ES索引成功:ProductID====" + product.getId());
//        } catch (ElasticsearchException elasticsearch) {
//            logger.error("添加到Elasticsearch失败:ProductID====" + product.getId());
//            throw elasticsearch;
//        }
    }


    public Page<Product> searchProduct(String keyword, Integer category, Integer brand, Pageable pageable) {
        //return productRepository.findByNameLikeAndBrandIdAndCategory(keyword, brand, category, pageable);
//        org.springframework.data.domain.Page<Product> page
//                = productRepository.findByNameLikeAndBrandIdAndCategory(keyword, brand, category, pageable);
        com.wstore.pojo.search.Page<Product> productPage = new com.wstore.pojo.search.Page<>();

//        productPage.setList(page.getContent());
//        productPage.setNum(page.getNumber());
//        productPage.setFirstPage(page.isFirst());
//        productPage.setLastPage(page.isLast());
//        productPage.setPages(page.getTotalPages());
//        productPage.setNavigatepageNums(WstoreUtils.paging(page.getTotalPages(), page.getNumber() + 1, 5));

        return productPage;
    }


    public List<Product> getProduct(Integer startRow, Integer endRow) {
        return productMapper.selectWithCategory(startRow, endRow);
    }
}
