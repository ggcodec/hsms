package com.hsms.house.controller;

import com.hsms.core.bean.Result;
import com.hsms.core.pojo.PriceState;
import com.hsms.house.service.PriceStateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 价格和状态接口
 *
 * @author haotchen
 * @time 2022/11/14-17:24
 */
@RestController
@RequestMapping("/api/v1/house")
@Api(tags = "价格和状态接口查询")
public class PriceStateController {

    @Autowired
    PriceStateService priceStateService;

    /**
     * 获取价格和状态列表
     * @return
     */
    @GetMapping("/getPriceStateList")
    public Result<Object> getPriceStateList(
        // Func Parameters
    ){
        try {
            // Code values
            return Result.ok(priceStateService.queryPriceStateList());
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    @GetMapping("/getPriceStateOnCondition")
    public Result<Object> getPriceStateOnCondition (
            // Func Parameters
            @RequestBody PriceState priceState
            ){
        try {
            // Code values
            // 参数校验
            if (Objects.isNull(priceState.getPrice()) ||
                    (!"0".equals(priceState.getState()) && !"1".equals(priceState.getState()))) {
                throw new RuntimeException("参数提交有误,请检查重试");
            }

            // 查询并返回结果
            return Result.ok(priceStateService.queryPriceState(priceState.getPrice(),priceState.getState()));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 新增价格和状态
     * @param priceState
     * @return
     */
    @PostMapping("/addPriceAndState")
    public Result<Object> addPriceAndState(
            // Func Parameters
            @RequestBody PriceState priceState
    ){
        try {
            // Code values
            priceStateService.addPriceAndState(priceState);
            return Result.ok("新增成功.");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
