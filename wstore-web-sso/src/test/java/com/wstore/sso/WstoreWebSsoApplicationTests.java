package com.wstore.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreWebSsoApplicationTests {

    @Value("${verify_code.timeout}")
    private Integer verify_code;
    @Test
    public void contextLoads() {
        System.out.println(verify_code);
    }
}
