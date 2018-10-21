package com.airwallex.ssoserver.dao;

import com.airwallex.common.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Test
    public void find() {
        User t = new User();
        t.setId("1");
        t.setUsername("zhangsan");
        t.setPassword("123");
        User user = userDao.find(t);
        System.out.println("=======");
        System.out.println(user.getUsername());
        assertNotNull(user);
    }
}