package com.longge.service;

import com.longge.domain.User;

/**
 * @author longge
 * @create 2019-12-09 下午8:02
 */
public interface UserService {

    boolean regist(User user);

    boolean active(String code);

    User login(String username,String password);
}
