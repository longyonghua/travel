package com.longge.service.impl;

import com.longge.dao.UserDao;
import com.longge.dao.impl.UserDaoImpl;
import com.longge.domain.User;
import com.longge.service.UserService;
import com.longge.util.MailUtils;
import com.longge.util.UuidUtil;

/**
 * @author longge
 * @create 2019-12-09 下午8:03
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if(u!=null){
            return false;
        }
        //设置激活码和激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        //保存用户信息
        userDao.save(user);
        //发送邮件
        String content = "<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user!=null){
            //修改激活状态
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User login(String username,String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }
}