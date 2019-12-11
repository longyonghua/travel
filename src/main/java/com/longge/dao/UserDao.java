package com.longge.dao;

import com.longge.domain.User;

/**
 * @author longge
 * @create 2019-12-09 下午8:03
 */
public interface UserDao {
    //根据用户名查询用户信息
    User findByUsername(String username);
    //用户保存
    void save(User user);
    //根据激活码查询用户对象
    User findByCode(String code);
    //修改用户的激活状态
    void updateStatus(User user);
    //根据用户名和密码查询用户信息
    User findByUsernameAndPassword(String username,String password);
}
