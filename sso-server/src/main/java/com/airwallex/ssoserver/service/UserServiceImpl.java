package com.airwallex.ssoserver.service;

import com.airwallex.common.domain.User;
import com.airwallex.ssoserver.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User find(User user) {
        return userDao.find(user);
    }
}
