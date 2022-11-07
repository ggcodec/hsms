package com.hsms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.common.util.MD5Util;
import com.hsms.core.mapper.UsersMapper;
import com.hsms.core.pojo.Users;
import com.hsms.user.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * UserService实现类
 *
 * @author haotchen
 * @time 2022/11/7-18:12
 */

@Service
public class UserServiceImpl implements UserService {

    // Mapper 映射

    @Autowired
    UsersMapper usersMapper;


    /**
     * 校验登录账户
     *
     * @param username        用户账号
     * @param encoderPassword 加密后的密码
     */
    @Override
    public void checkUserAccount(String username, String encoderPassword) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(encoderPassword)) {
            throw new RuntimeException("账号和密码不能为空.");
        }
        // 设置查询条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, username)
                .eq(Users::getPassword, encoderPassword);
        Users users = usersMapper.selectOne(wrapper);
        // 检查是否为空
        if (Objects.isNull(users)) {
            throw new RuntimeException("账号密码校验失败.");
        }
    }

    /**
     * 查询用户信息,密码为空
     *
     * @param username 用户账号
     * @return Users
     */
    @Override
    public Users queryUserAccount(String username) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username)) {
            throw new RuntimeException("账号不能为空.");
        }
        // 设置查询条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, username);
        Users users = usersMapper.selectOne(wrapper);
        // 检查是否为空
        if (Objects.isNull(users)) {
            throw new RuntimeException("账号不存在请重试.");
        }
        users.setPassword("");
        return users;
    }

    /**
     * 修改用户信息,姓名/年龄/加密后的密码,密码如果为空查询后重新设置
     *
     * @param user 接收用户信息
     * @return Users
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateUsers(Users user) {
        // 参数校验
        if (user == null) {
            throw new RuntimeException("user 不能为空.");
        }
        String md5 = null;
        // 判断密码是否为空,如果为空需要设置一下密码
        if (StringUtils.isNullOrEmpty(user.getPassword())) {
            // 查询用户
            Users users = queryUserAccount(user.getUsername());
            // 设置查询出来的密码
            user.setPassword(users.getPassword());
        } else {
            try {
                // 将明文密码加密
                md5 = MD5Util.getMD5(user.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        // 设置修改条件
        LambdaUpdateWrapper<Users> wrapper = new LambdaUpdateWrapper<Users>()
                .eq(Users::getUsername, user.getUsername())
                .set(!Objects.isNull(user.getPassword()), Users::getPassword, md5)
                .set(!Objects.isNull(user.getName()), Users::getName, user.getName())
                .set(!Objects.isNull(user.getAge()), Users::getAge, user.getAge());
        // 修改用户信息
        usersMapper.update(user, wrapper);
    }

    /**
     * 删除用户信息
     *
     * @param username        用户账号
     * @param encoderPassword 加密后的密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deleteUsers(String username, String encoderPassword) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(encoderPassword)) {
            throw new RuntimeException("账号和密码不能为空.");
        }

        // 添加查询条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, username)
                .eq(Users::getPassword, encoderPassword);

        // 查询被删除的用户信息是否存在
        Users users = usersMapper.selectOne(wrapper);
        if (users.getPassword() != encoderPassword) {
            throw new RuntimeException("参数有误无法删除用户.");
        }
        if (Objects.isNull(users)) {
            throw new RuntimeException("用户不存在.");
        }

        // 删除用户
        usersMapper.deleteById(users.getId());
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void addUsers(Users user) {
        // 参数校验
        if (Objects.isNull(user)
                || StringUtils.isNullOrEmpty(user.getUsername())
                || StringUtils.isNullOrEmpty(user.getPassword())
                || StringUtils.isNullOrEmpty(user.getName())
                || Objects.isNull(user.getAge())
        ) {
            throw new RuntimeException("user 参数不能为空.");
        }

        // 添加查询条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<Users>()
                .eq(Users::getUsername, user.getUsername());
        Users users = usersMapper.selectOne(wrapper);
        if (!Objects.isNull(users)) {
            throw new RuntimeException("user 已存在.");
        }

        // 将密码进行md5加密存放数据库
        String md5 = null;
        try {
            // md5 加密密码返回16进制字符串
            md5 = MD5Util.getMD5(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 加密后的密码放入user 后新增 user
        user.setPassword(md5);
        usersMapper.insert(user);
    }

    /**
     * 查询用户信息列表
     *
     * @return List<Users>
     */
    @Override
    public List<Users> getUserList() {
        // 查询所有用户信息内容返回
        return usersMapper.selectList(null);
    }

    /**
     * 根据条件查询用户信息分页列表
     *
     * @param index 起始页面
     * @param size  每页大小
     * @return List<Users>
     */
    @Override
    public Page<Users> getUserPage(Integer index, Integer size,Users user) {
        // 参数校验
        if (index < 0 || size < 0) {
            throw new RuntimeException("非法参数,请重试");
        }

        // 添加分页查询的条件
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<Users>()
                .eq(!StringUtils.isNullOrEmpty(user.getName()), Users::getName, user.getName())
                .eq(!StringUtils.isNullOrEmpty(user.getUsername()), Users::getUsername, user.getUsername())
                .eq(!Objects.isNull(user.getId()), Users::getId, user.getId());


        // 查询所有用户信息内容返回
        return usersMapper.selectPage(new Page<Users>(index,size),wrapper);
    }
}
