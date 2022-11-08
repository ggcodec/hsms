package com.hsms.auth.controller;

import com.hsms.common.util.JWTUtil;
import com.hsms.core.api.ManagerServerApi;
import com.hsms.core.api.UserServerApi;
import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Manager;
import com.hsms.core.pojo.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

/**
 * 认证接口
 *
 * @author haotchen
 * @time 2022/11/8-13:07
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "认证授权",tags = "认证授权")
public class AuthController {

    // User远程调用接口
    @Autowired
    UserServerApi userServerApi;
    // Manager远程调用接口
    @Autowired
    ManagerServerApi managerServerApi;

    @Value("${auth.secret}")
    String secret;

    /**
     * Client用户登录
     * @param user 封装账号密码
     * @return Result
     */
    @ApiOperation(value = "Client用户登录",httpMethod = "POST")
    @PostMapping("/loginUser")
    public Result<Object> loginUser(
        // Func Parameters
        @RequestBody Users user,
        HttpServletResponse res
    ){
        try {
            // Code values
            // 参数校验
            if (Objects.isNull(user.getUsername()) || Objects.isNull(user.getPassword())) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 调用user服务查询用户信息是否存在
            Result<Users> usersResult = userServerApi.queryUser(user.getUsername());

            // 校验查询结果是否为空
            Users users = usersResult.getData();
            if (Objects.isNull(users) ) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 校验账号密码是否正确
            Result<String> checkResult = userServerApi.loginUser(user);
            if (checkResult.getCode() != 200) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 创建token载荷,生成token
            HashMap<String,Object> tokenBody = new HashMap<>();
            tokenBody.put("name", users.getName());
            tokenBody.put("username", users.getUsername());
            tokenBody.put("age", users.getAge());
            String token = JWTUtil.jwtBuilder(tokenBody, secret, 6000 * 1000L);

            // 将token 存放到cookie中
            Cookie authToken = new Cookie("authToken", token);
            res.addCookie(authToken);
            return Result.ok("登录成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    /**
     * Manager登录
     * @param manager 封装账号密码
     * @return Result
     */
    @ApiOperation(value = "Manager登录",httpMethod = "POST")
    @PostMapping("/loginManager")
    public Result<Object> loginManager(
            // Func Parameters
            @RequestBody Manager manager,
            HttpServletResponse res
    ){
        try {
            // Code values
            // 参数校验
            if (Objects.isNull(manager.getUsername()) || Objects.isNull(manager.getPassword())) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 调用user服务查询用户信息是否存在
            Result<Manager> managerResult = managerServerApi.queryManager(manager.getUsername());

            // 校验查询结果是否为空
            Manager manager1 = managerResult.getData();
            if (Objects.isNull(manager1) ) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 校验账号密码是否正确
            Result<String> checkResult = managerServerApi.loginManager(manager1);
            if (checkResult.getCode() != 200) {
                throw new RuntimeException("账号密码不正确,请重试");
            }

            // 创建token载荷,生成token
            HashMap<String,Object> tokenBody = new HashMap<>();
            tokenBody.put("name", manager.getName());
            tokenBody.put("username", manager.getUsername());
            tokenBody.put("state", managerResult.getData().getState());
            String token = JWTUtil.jwtBuilder(tokenBody, secret, 6000 * 1000L);

            // 将token 存放到cookie中
            Cookie authToken = new Cookie("authToken", token);
            res.addCookie(authToken);
            return Result.ok("登录成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 注销登录
     * @return Result
     */
    @ApiOperation(value = "注销登录",httpMethod = "GET")
    @GetMapping("/logOut")
    public Result<Object> logOut(
        // Func Parameters
    ){
        try {
            // Code values
            return Result.ok("注销成功!");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
