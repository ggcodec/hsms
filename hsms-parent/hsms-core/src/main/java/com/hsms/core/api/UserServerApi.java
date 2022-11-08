package com.hsms.core.api;

import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("service-user") // 调用service-user服务的api
public interface UserServerApi {

    /**
     * 远程调用查询user
     * @param username 账号
     * @return Result
     */
    @GetMapping("/api/v1/user/queryUser")
    Result<Users> queryUser(@RequestParam("username") String username);

    /**
     * 校验账号密码
     * @param username 账号
     * @param password 密码
     * @return Result
     */
    @PostMapping("/api/v1/user/loginUser")
    Result<String> loginUser(@RequestBody Users users);

}
