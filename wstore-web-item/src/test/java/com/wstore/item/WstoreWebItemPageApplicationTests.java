package com.wstore.item;

import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.pojo.sso.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.soap.SOAPBinding;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreWebItemPageApplicationTests {

    @Test
    public void jsonTest() {
        User user = new User();
        user.setUserName("wang");
        user.setUserPassword("123456");
        String userJson = WstoreJsonUtil.toJson(user);

        User o = WstoreJsonUtil.fromJson(userJson, User.class);
        System.out.println(o);
    }

}
