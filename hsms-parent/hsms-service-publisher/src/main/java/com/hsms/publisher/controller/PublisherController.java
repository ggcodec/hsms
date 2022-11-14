package com.hsms.publisher.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.bean.Result;
import com.hsms.core.pojo.Publishers;
import com.hsms.publisher.service.PublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 发布者信息接口层
 *
 * @author haotchen
 * @time 2022/11/13-16:19
 */
@RestController
@RequestMapping("/api/v1/publisher")
@Api(tags = "发布者信息接口")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    /**
     * 分页查询发布者信息
     * @param index
     * @param size
     * @return
     */
    @GetMapping("/getPublisherPage/{index}/{size}")
    @ApiOperation(value = "分页查询发布者信息",httpMethod = "GET")
    public Result<Object> getPublisherPage(
            // Func Parameters
            @PathVariable("index") Integer index,
            @PathVariable("size") Integer size
    ) {
        try {
            // Code values
            Page<Publishers> publishersPage = publisherService.queryPublishersPage(index, size);
            return Result.ok(publishersPage);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据id 查询发布者信息
     * @param id
     * @return
     */
    @GetMapping("/getPublisherById/{id}")
    @ApiOperation(value = "根据Id获取发布者信息",httpMethod = "GET")
    public Result<Object> getPublisherById(
            // Func Parameters
            @PathVariable("id") Long id
    ) {
        try {
            // Code values
            publisherService.queryPublisherById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据电话号码查询发布者信息
     * @param phone
     * @return
     */
    @GetMapping("/getPublisherByPhone/{phone}")
    @ApiOperation(value = "根据电话获取发布者信息",httpMethod = "GET")
    public Result<Object> getPublisherByPhone(
            // Func Parameters
            @PathVariable("phone") String phone
    ) {
        try {
            // Code values
            Publishers publishers = publisherService.queryPublisherByPhone(phone);
            return Result.ok(publishers);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 修改发布者信息
     * @param publishers
     * @return
     */
    @PutMapping("/updatePublisher")
    @ApiOperation(value = "修改发布者信息",httpMethod = "PUT")
    public Result<Object> updatePublisher(
        // Func Parameters
        @RequestBody Publishers publishers
    ){
        try {
            // Code values
            publisherService.updatePublisher(publishers);
            return Result.ok("修改发布者信息成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除发布者信息
     * @param id
     * @return
     */
    @DeleteMapping("/deletePublisher/{id}")
    @ApiOperation(value = "删除发布者信息",httpMethod = "DELETE")
    public Result<Object> deletePublisher(
        // Func Parameters
        @PathVariable("id") Long id
    ){
        try {
            // Code values
            Publishers publishers = publisherService.deletePublishersById(id);
            return Result.ok(publishers);
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 新增发布者信息
     * @param publishers
     * @return
     */
    @PostMapping("/addPublisher")
    @ApiOperation(value = "新增发布者信息",httpMethod = "POST")
    public Result<Object> addPublisher(
        // Func Parameters
        @RequestBody Publishers publishers
    ){
        try {
            // Code values
            publisherService.addPublisher(publishers);
            return Result.ok("添加发布者信息成功");
        }catch(Exception e){
            return Result.fail(e.getMessage());
        }
    }

}
