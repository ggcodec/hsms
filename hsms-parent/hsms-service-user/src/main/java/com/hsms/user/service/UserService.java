package com.hsms.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.pojo.Users;

import java.util.List;

/**
 * 用户信息相关处理服务层
 *
 * @author haotchen
 * @time 2022/11/7-17:52
 */

public interface UserService {

    /**
     * 校验登录账户
     * @param username  用户账号
     * @param encoderPassword 加密后的密码
     */
    public void checkUserAccount(String username,String encoderPassword);


    /**
     * 查询用户信息,密码为空
     * @param username 用户账号
     * @return Users
     */
    public Users queryUserAccount(String username);


    /**
     * 修改用户信息,姓名/年龄/加密后的密码,密码如果为空查询后重新设置
     * @param user  接收用户信息
     * @return  Users
     */
    public void updateUsers(Users user);

    /**
     * 删除用户信息
     * @param username  用户账号
     * @param encoderPassword 加密后的密码
     */
    public void deleteUsers(String username,String encoderPassword);

    /**
     * 新增用户信息
     * @param user 用户信息
     */
    public void addUsers(Users user);

    /**
     * 查询用户信息列表
     * @return List<Users>
     */
    public List<Users> getUserList();

    /**
     * 根据条件查询用户信息分页列表
     *
     * @return List<Users>
     */
    public Page<Users> getUserPage(Integer index, Integer size, Users user);



}
