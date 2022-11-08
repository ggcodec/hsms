package com.hsms.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.common.util.MD5Util;
import com.hsms.core.mapper.ManagesMapper;
import com.hsms.core.pojo.Manager;
import com.hsms.core.pojo.Users;
import com.hsms.manager.service.ManagerService;
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
 * 管理员服务层实现类
 *
 * @author haotchen
 * @time 2022/11/8-17:08
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
public class ManagerServiceImpl implements ManagerService {

    // Mapper映射
    @Autowired
    ManagesMapper managerMapper;


    /**
     * 校验管理员登录账户
     *
     * @param username        管理员账号
     * @param encoderPassword 加密后的密码
     */
    @Override
    public void checkManagerAccount(String username, String encoderPassword) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(encoderPassword)) {
            throw new RuntimeException("参数不能为空!");
        }

        // 添加查询条件
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUsername, username)
                .eq(Manager::getPassword, encoderPassword);

        // 查询管理员信息
        Manager manager = managerMapper.selectOne(wrapper);

        // 结果为空表示账号密码失败
        if (Objects.isNull(manager)) {
            throw new RuntimeException("账号密码错误,请重试!");
        }
    }

    /**
     * 查询管理员信息,密码为空
     *
     * @param username 管理员账号
     * @return Users
     */
    @Override
    public Manager queryManagerAccount(String username) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username)) {
            throw new RuntimeException("参数不能为空!");
        }

        // 添加查询条件
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUsername, username);

        // 查询管理员信息
        Manager manager = managerMapper.selectOne(wrapper);
        manager.setPassword("");
        return manager;
    }

    /**
     * 修改管理员信息,姓名/年龄/加密后的密码,密码如果为空查询后重新设置
     *
     * @param manager 接收管理员信息
     *
     */
    @Override
    public void updateManager(Manager manager) {
        // 参数校验
        if (manager == null) {
            throw new RuntimeException("user 不能为空.");
        }
        if (StringUtils.isNullOrEmpty(manager.getUsername())) {
            throw new RuntimeException("username 不能为空.");
        }
        String md5 = null;
        // 判断密码是否为空,如果为空需要设置一下密码
        if (StringUtils.isNullOrEmpty(manager.getPassword())) {
            // 查询用户
            Manager managers = queryManagerAccount(manager.getUsername());
            // 设置查询出来的密码
            manager.setPassword(managers.getPassword());
        } else {
            try {
                // 将明文密码加密
                md5 = MD5Util.getMD5(manager.getPassword());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        // 设置修改条件
        LambdaUpdateWrapper<Manager> wrapper = new LambdaUpdateWrapper<Manager>()
                .eq(Manager::getUsername, manager.getUsername())
                .set(!Objects.isNull(manager.getPassword()), Manager::getPassword, md5)
                .set(!Objects.isNull(manager.getName()), Manager::getName, manager.getName());

        // 修改用户信息
        managerMapper.update(manager, wrapper);
    }

    /**
     * 删除管理员信息
     *
     * @param username        管理员账号
     * @param encoderPassword 加密后的密码
     */
    @Override
    public void deleteManager(String username, String encoderPassword) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(encoderPassword)) {
            throw new RuntimeException("参数不能为空!");
        }

        // 添加查询条件
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUsername, username)
                .eq(Manager::getPassword, encoderPassword);

        // 查询被删除的用户信息是否存在
        Manager manager = managerMapper.selectOne(wrapper);
        if (Objects.isNull(manager)) {
            throw new RuntimeException("管理员不存在.");
        }

        // 校验密码是否正确
        if (!manager.getPassword().equals(encoderPassword)) {
            throw new RuntimeException("参数有误无法删除管理员.");
        }

        // 删除用户
        managerMapper.deleteById(manager.getId());
    }

    /**
     * 新增管理员信息
     *
     * @param manager 管理员信息
     */
    @Override
    public void addManager(Manager manager) {
        // 参数校验
        if (Objects.isNull(manager)
                || StringUtils.isNullOrEmpty(manager.getUsername())
                || StringUtils.isNullOrEmpty(manager.getPassword())
                || StringUtils.isNullOrEmpty(manager.getName())
        ) {
            throw new RuntimeException("user 参数不能为空.");
        }

        // 添加查询条件
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(Manager::getUsername, manager.getUsername());
        Manager managers = managerMapper.selectOne(wrapper);
        if (!Objects.isNull(managers)) {
            throw new RuntimeException("managers 已存在.");
        }

        // 将密码进行md5加密存放数据库
        String md5 = null;
        try {
            // md5 加密密码返回16进制字符串
            md5 = MD5Util.getMD5(manager.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 加密后的密码放入manager 后新增 manager
        manager.setPassword(md5);
        managerMapper.insert(manager);
    }

    /**
     * 查询管理员信息列表
     *
     * @return List<Users>
     */
    @Override
    public List<Manager> getManagerList() {
        // 查询所有用户信息内容返回
        return managerMapper.selectList(null);
    }

    /**
     * 根据条件查询管理员信息分页列表
     *
     * @param index
     * @param size
     * @param manager
     * @return List<Users>
     */
    @Override
    public Page<Manager> getManagerPage(Integer index, Integer size, Manager manager) {
        // 参数校验
        if (index < 0 || size < 0) {
            throw new RuntimeException("非法参数,请重试");
        }

        // 添加分页查询的条件
        LambdaQueryWrapper<Manager> wrapper = new LambdaQueryWrapper<Manager>()
                .eq(!StringUtils.isNullOrEmpty(manager.getName()), Manager::getName, manager.getName())
                .eq(!StringUtils.isNullOrEmpty(manager.getUsername()), Manager::getUsername, manager.getUsername())
                .eq(!Objects.isNull(manager.getId()), Manager::getId, manager.getId());


        // 查询所有用户信息内容返回
        return managerMapper.selectPage(new Page<Manager>(index, size), wrapper);
    }
}
