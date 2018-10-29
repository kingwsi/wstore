package com.wstore.admin.controller;

import com.wstore.admin.util.WstoreFastDFS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreWebAdminApplicationTests {

    @Autowired
    WstoreFastDFS fastDFS;

    @Test
    public void contextLoads() {

    }

}
