package com.evan.wj.dao;

import com.evan.wj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 此处接口继承了JPA
 * 使用 JPA，无需手动构建 SQL 语句，而只需要按照规范提供方法的名字即可实现对数据库的增删改查。
 */
public interface UserDao extends JpaRepository<User,Integer> {



    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);
}
