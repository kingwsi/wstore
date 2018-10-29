package com.wstore.search;

import com.wstore.pojo.admin.Product;
import com.wstore.search.service.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.junit4.SpringRunner;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreServiceSearchApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test02() {
        Product product = new Product();
        product.setId(Long.parseLong("2"));
        product.setName("魅族15");
        productRepository.index(product);
    }

    @Test
    public void search() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("item")
                .withTypes("product")
                .withQuery(matchQuery("name", "op"))
                .withQuery(matchQuery("subName", "op"))
                .withPageable(pageable)
                .build();
        Page<Product> search = productRepository.search(searchQuery);
        System.out.println(search.getTotalPages()
                + "\n" + search.getTotalElements());
    }

    @Test
    public void deleteTest() {
        int pages = 2;
        int num = 8;
        int navigateSize = 5;
        int minNum = navigateSize / 2;
        int maxNum = minNum;
        //navigateSize为偶数九将最大页码数-1 防止数组越界
        if (navigateSize % 2 == 0) {
            maxNum -= 1;
        }
        int[] navigateNums = new int[navigateSize];
        if (pages > navigateSize) {
            //判断当前页是否大于3
            if (num < minNum) {
                for (int i = 0; i < navigateSize; i++) {
                    navigateNums[i] = i + 1;
                }
            } else {
                int flag = 0;
                for (int i = num - minNum; i <= num + maxNum; i++) {
                    navigateNums[flag++] = i;
                }
            }
        } else {
            //设置数组长度
            navigateNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigateNums[i] = i + 1;
            }
        }

        System.out.println(navigateNums.toString());
    }

}
