package com.shun.service;

public class ServiceImpl implements Service {
    public void select() {
        System.out.println("查询用户");
    }

    public void insert() {
        System.out.println("增加用户");
    }

    public void update() {
        System.out.println("更新用户");
    }

    public void delete() {
        System.out.println("删除用户");
    }
}
