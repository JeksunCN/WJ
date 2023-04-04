package com.evan.wj.service;

import com.evan.wj.dao.UserDao;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对 UserDAO 进行了二次封装，一般来讲，我们在 DAO 中只定义基础的增删改查操作，而具体的操作，
 * 需要由 Service 来完成。当然，由于我们做的操作原本就比较简单，所以这里看起来只是简单地重命名了一下，
 * 比如把 “通过用户名及密码查询并获得对象” 这种方法命名为 get
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public boolean isExist(String username){
        User user = getByName(username);
        return null!=user;
    }

    public User getByName(String username){
        return userDao.findByUsername(username);
    }

    public User get(String username,String password){
        return userDao.getByUsernameAndPassword(username,password);
    }

    public void add(User user){
        userDao.save(user);
    }
}
