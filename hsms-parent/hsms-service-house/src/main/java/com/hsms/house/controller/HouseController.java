package com.hsms.house.controller;

import com.hsms.core.bean.Result;
import com.hsms.core.pojo.RequestBodyHouse;
import com.hsms.house.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 房源信息接口层
 *
 * @author haotchen
 * @time 2022/11/14-15:33
 */
@RestController
@RequestMapping("/api/v1/house")
@Api(tags = "房源信息接口")
public class HouseController {

    @Autowired
    HouseService houseService;

    /**
     * 分页查询房源信息
     * @param index
     * @param size
     * @return
     */
    @GetMapping("/getHousePage/{index}/{size}")
    @ApiOperation(value = "分页查询房源信息",httpMethod = "GET")
    public Result<Object> getHousePage(
        // Func Parameters
        @PathVariable("index") Integer index,
        @PathVariable("size") Integer size
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHousePage(index,size));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getHouseById/{id}")
    @ApiOperation(value = "根据ID查询房源",httpMethod = "GET")
    public Result<Object> getHouseById(
        // Func Parameters
        @PathVariable("id") Long id
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHouseById(id));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getHouseByPubId/{id}")
    @ApiOperation(value = "根据发布者id查询房源",httpMethod = "GET")
    public Result<Object> getHouseByPubId(
            // Func Parameters
            @PathVariable("id") Long id
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHouseByPublisher(id));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    @GetMapping("/getHouseByPrice/{min}/{max}")
    @ApiOperation(value = "根据价格区间查询房源",httpMethod = "GET")
    public Result<Object> getHouseByPrice(
            // Func Parameters
            @PathVariable("min") Double min,
            @PathVariable("max") Double max
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHouseByPrice(min,max));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }



    @GetMapping("/getHouseByStaffId/{id}")
    @ApiOperation(value = "根据员工id查询房源",httpMethod = "GET")
    public Result<Object> getHouseByStaffId(
            // Func Parameters
            @PathVariable("id") Long id
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHouseByStaffId(id));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    @GetMapping("/getHouseByAddressReg/{reg}")
    @ApiOperation(value = "根据地址模糊查询",httpMethod = "GET")
    public Result<Object> getHouseByAddressReg(
            // Func Parameters
            @PathVariable("reg") String reg
    ){
        try {
            // Code values
            return Result.ok(houseService.queryHouseByAddressReg(reg));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }
    @DeleteMapping("/deleteHouseById/{id}")
    @ApiOperation(value = "根据id删除房源",httpMethod = "DELETE")
    public Result<Object> deleteHouseById(
            // Func Parameters
            @PathVariable("id") Long id
    ){
        try {
            // Code values
            return Result.ok(houseService.deleteHouseById(id));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }
    @PostMapping("/addHouse")
    @ApiOperation(value = "新增房源信息",httpMethod = "POST")
    public Result<Object> addHouse(
            // Func Parameters
            @RequestBody RequestBodyHouse requestBodyHouse
    ){
        try {
            // Code values
            houseService.addHouse(requestBodyHouse);
            return Result.ok("新增房源信息成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

}
