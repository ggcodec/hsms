package com.hsms.core.api;

import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Manager;
import com.hsms.core.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("service-manager") // 调用service-user服务的api
public interface ManagerServerApi {

    /**
     * 远程调用查询manager
     * @param username 账号
     * @return Result
     */
    @GetMapping("/api/v1/manager/queryManager")
    Result<Manager> queryManager(@RequestParam("username") String username);

    /**
     * 校验账号密码
     * @param manager 账号密码载体
     * @return Result
     */
    @PostMapping("/api/v1/manager/loginManager")
    Result<String> loginManager(@RequestBody Manager manager);

}
