package com.hsms.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.common.util.MD5Util;
import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Users;
import com.hsms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息接口层
 *
 * @author haotchen
 * @time 2022/11/7-18:43
 */

@RestController
@Api(value = "用户信息",tags = "用户信息")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取用户信息
     *
     * @param username 用户账号
     * @return Result
     */
    @GetMapping("/queryUser")
    @ApiOperation(value = "获取用户信息",httpMethod = "GET")
    public Result<Object> queryUser(
            // Func Parameters
            @RequestParam("username") String username
    ) {
        try {
            // Code values
            Users users = userService.queryUserAccount(username);
            return Result.ok(users);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return Result
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "注册用户信息",httpMethod = "POST")
    public Result<Object> addUser(
            // Func Parameters
            @RequestBody Users user
    ) {
        try {
            // Code values
            userService.addUsers(user);
            return Result.ok("注册成功!");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 用户信息登录校验,主要账号密码校验
     *
     * @param user 存放账号密码的载体
     * @return Result
     */
    @PostMapping("/loginUser")
    @ApiOperation(value = "账号密码登录校验",httpMethod = "POST")
    public Result<Object> loginUser(
            // Func Parameters
            @RequestBody Users user
    ) {
        try {
            // Code values
            userService.checkUserAccount(user.getUsername(), MD5Util.getMD5(user.getPassword()));
            return Result.ok("校验成功,可以登录");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return Result
     */
    @PutMapping("/modifyUser")
    @ApiOperation(value = "修改用户信息",httpMethod = "PUT")
    public Result<Object> modifyUser(
            // Func Parameters
            @RequestBody Users user
    ) {
        try {
            // Code values
            userService.updateUsers(user);
            return Result.ok("修改信息成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 注销用户信息
     *
     * @param user 封装账号密码
     * @return Result
     */
    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "注销用户信息",httpMethod = "DELETE")
    public Result<Object> deleteUser(
            // Func Parameters
            @RequestBody Users user
    ) {
        try {
            // Code values
            String encoderPassword = MD5Util.getMD5(user.getPassword());
            userService.deleteUsers(user.getUsername(), encoderPassword);
            return Result.ok("注销用户信息成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询用户信息列表
     *
     * @return Result
     */
    @GetMapping("/getUserList")
    @ApiOperation(value = "查询用户信息列表",httpMethod = "GET")
    public Result<Object> getUserList(
            // Func Parameters
    ) {
        try {
            // Code values
            // 查询所有用户信息返回
            List<Users> userList = userService.getUserList();
            return Result.ok(userList);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 分页查询用户信息
     * @param index 起始页面
     * @param size  页面大小
     * @param user  查询条件
     * @return
     */
    @GetMapping("/getUserPage/{index}/{size}")
    @ApiOperation(value = "分页查询用户信息",httpMethod = "GET")
    public Result<Object> getUserPage(
            // Func Parameters
            @PathVariable("index") Integer index,
            @PathVariable("size") Integer size,
            @RequestBody Users user
    ) {
        try {
            // Code values
            // 根据条件分页查询用户信息
            Page<Users> userPage = userService.getUserPage(index, size, user);
            return Result.ok(userPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
