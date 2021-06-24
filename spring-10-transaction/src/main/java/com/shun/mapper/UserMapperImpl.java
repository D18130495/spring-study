package com.shun.mapper;

import com.shun.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{
    public List<User> selectUser() {

        User user = new User(6, "xiaowang", "qwerasdfzxc");

        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);

        userMapper.addUser(user);
        userMapper.deleteUser(5);
        return userMapper.selectUser();
    }

    public int addUser(User user) {
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        return userMapper.addUser(user);
    }

    public int deleteUser(int id) {
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        return userMapper.deleteUser(id);
    }
}
