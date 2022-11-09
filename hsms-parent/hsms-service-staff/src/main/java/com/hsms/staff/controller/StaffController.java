package com.hsms.staff.controller;

import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Staffs;
import com.hsms.staff.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工信息接口层
 *
 * @author haotchen
 * @time 2022/11/9-14:08
 */
@RestController
@RequestMapping("/api/v1/staff")
@Api(value = "员工信息",tags = "员工信息")
public class StaffController {

    @Autowired
    StaffService staffService;

    /**
     * 获取所有员工信息列表
     * @return Result
     */
    @GetMapping("/getStaffsList")
    @ApiOperation(value = "获取员工信息",httpMethod = "GET")
    public Result<Object> getStaffs (
        // Func Parameters
    ){
        try {
            // Code values
            List<Staffs> staffs = staffService.queryStaffList();
            return Result.ok(staffs);
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据条件查询单个员工信息
     * @param staffName 员工姓名
     * @param staffs   员工信息条件
     * @return Result
     */
    @GetMapping("/queryStaffByStaffName/{staffName}")
    @ApiOperation(value = "根据条件获取员工信息",httpMethod = "GET")
    public Result<Object> queryStaffByStaffName(
        // Func Parameters
        @PathVariable("staffName") String staffName,
        @RequestBody Staffs staffs

    ){
        try {
            // Code values
            return Result.ok(staffService.queryStaffByCondition(staffName,staffs));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 根据条件分页查询
     * @param staffs 条件
     * @return Result
     */
    @GetMapping("/getStaffsPage/{index}/{size}")
    @ApiOperation(value = "根据条件分页查询",httpMethod = "GET")
    public Result<Object> getStaffsPage (
        // Func Parameters
        @RequestBody Staffs staffs,
        @PathVariable("index") Integer index,
        @PathVariable("size") Integer size
    ){
        try {
            // Code values
            return Result.ok(staffService.queryStaffPage(index, size, staffs));
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 新增员工
     * @param staffs 员工信息
     * @return Result
     */
    @PostMapping("/addStaff")
    @ApiOperation(value = "新增员工信息",httpMethod = "POST")
    public Result<Object> addStaff(
        // Func Parameters
        @RequestBody Staffs staffs
    ){
        try {
            // Code values
            staffService.addStaff(staffs);
            return Result.ok("新增员工成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除员工
     * @param id 根据id
     * @return Result
     */
    @DeleteMapping("/deleteStaff/{id}")
    @ApiOperation(value = "删除员工信息",httpMethod = "DELETE")
    public Result<Object> deleteStaff(
        // Func Parameters
        @PathVariable("id") Long id
    ){
        try {
            // Code values
            staffService.deleteStaffById(id);
            return Result.ok("注销员工成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改员工信息
     * @param staffs 员工信息
     * @return Result
     */
    @PutMapping("/updateStaff")
    @ApiOperation(value = "修改员工信息",httpMethod = "PUT")
    public Result<Object> updateStaff(
        // Func Parameters
        @RequestBody Staffs staffs
    ){
        try {
            // Code values
            staffService.updateStaff(staffs);
            return Result.ok();
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

}
