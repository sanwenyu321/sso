package com.airwallex.ssoserver.dao;

import com.airwallex.common.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User find(@Param("user") User user);
}
