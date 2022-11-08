package com.hsms.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.pojo.Users;
import com.hsms.core.pojo.Manager;

import java.util.List;

/**
 * 管理员服务层
 */
public interface ManagerService {

    /**
     * 校验管理员登录账户
     * @param username  管理员账号
     * @param encoderPassword 加密后的密码
     */
    public void checkManagerAccount(String username,String encoderPassword);


    /**
     * 查询管理员信息,密码为空
     * @param username 管理员账号
     * @return Users
     */
    public Manager queryManagerAccount(String username);


    /**
     * 修改管理员信息,姓名/年龄/加密后的密码,密码如果为空查询后重新设置
     * @param manager  接收管理员信息
     * @return  Users
     */
    public void updateManager(Manager manager);

    /**
     * 删除管理员信息
     * @param username  管理员账号
     * @param encoderPassword 加密后的密码
     */
    public void deleteManager(String username,String encoderPassword);

    /**
     * 新增管理员信息
     * @param manager 管理员信息
     */
    public void addManager(Manager manager);

    /**
     * 查询管理员信息列表
     * @return List<Users>
     */
    public List<Manager> getManagerList();

    /**
     * 根据条件查询管理员信息分页列表
     *
     * @return List<Users>
     */
    public Page<Manager> getManagerPage(Integer index, Integer size, Manager manager);

}
