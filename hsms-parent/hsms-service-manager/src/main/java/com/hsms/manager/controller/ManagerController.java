package com.hsms.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.common.util.MD5Util;
import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Manager;
import com.hsms.core.pojo.Users;
import com.hsms.manager.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员接口
 *
 * @author haotchen
 * @time 2022/11/8-17:56
 */
@RestController
@RequestMapping("/api/v1/manager")
@Api
public class ManagerController {

    @Autowired
    ManagerService managerService;

    /**
     * 获取管理员信息
     *
     * @param username 用户账号
     * @return Result
     */
    @GetMapping("/queryManager")
    @ApiOperation(value = "获取管理员信息",httpMethod = "GET")
    public Result<Object> queryManager(
            // Func Parameters
            @RequestParam("username") String username
    ) {
        try {
            // Code values
            Manager manager = managerService.queryManagerAccount(username);
            return Result.ok(manager);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 注册管理员信息
     *
     * @param manger 管理员信息
     * @return Result
     */
    @PostMapping("/addManager")
    @ApiOperation(value = "注册管理员信息",httpMethod = "POST")
    public Result<Object> addManager(
            // Func Parameters
            @RequestBody Manager manger
    ) {
        try {
            // Code values
            managerService.addManager(manger);
            return Result.ok("注册成功!");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 管理员信息登录校验,主要账号密码校验
     *
     * @param manger 存放账号密码的载体
     * @return Result
     */
    @PostMapping("/loginManager")
    @ApiOperation(value = "账号密码登录校验",httpMethod = "POST")
    public Result<Object> loginManager(
            // Func Parameters
            @RequestBody Manager manger
    ) {
        try {
            // Code values
            managerService.checkManagerAccount(manger.getUsername(), MD5Util.getMD5(manger.getPassword()));
            return Result.ok("校验成功,可以登录");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 修改管理员信息
     *
     * @param manager 管理员信息
     * @return Result
     */
    @PutMapping("/modifyManager")
    @ApiOperation(value = "修改用户信息",httpMethod = "PUT")
    public Result<Object> modifyManager(
            // Func Parameters
            @RequestBody Manager manager
    ) {
        try {
            // Code values
            managerService.updateManager(manager);
            return Result.ok("修改信息成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 注销管理员信息
     *
     * @param manager 封装账号密码
     * @return Result
     */
    @DeleteMapping("/deleteManager")
    @ApiOperation(value = "注销管理员信息",httpMethod = "DELETE")
    public Result<Object> deleteManager(
            // Func Parameters
            @RequestBody Manager manager
    ) {
        try {
            // Code values
            String encoderPassword = MD5Util.getMD5(manager.getPassword());
            managerService.deleteManager(manager.getUsername(), encoderPassword);
            return Result.ok("注销管理员信息成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询管理员信息列表
     *
     * @return Result
     */
    @GetMapping("/getManagerList")
    @ApiOperation(value = "查询管理员信息列表",httpMethod = "GET")
    public Result<Object> getManagerList(
            // Func Parameters
    ) {
        try {
            // Code values
            // 查询所有管理员信息返回
            List<Manager> managerList = managerService.getManagerList();
            return Result.ok(managerList);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 分页查询用户信息
     * @param index 起始页面
     * @param size  页面大小
     * @param manager  查询条件
     * @return
     */
    @GetMapping("/getManagerPage/{index}/{size}")
    @ApiOperation(value = "分页查询用户信息",httpMethod = "GET")
    public Result<Object> getManagerPage(
            // Func Parameters
            @PathVariable("index") Integer index,
            @PathVariable("size") Integer size,
            @RequestBody Manager manager
    ) {
        try {
            // Code values
            // 根据条件分页查询用户信息
            Page<Manager> managerPage = managerService.getManagerPage(index, size, manager);
            return Result.ok(managerPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
}
