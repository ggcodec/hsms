package com.hsms.house.controller;


import com.hsms.common.util.FileUtil;
import com.hsms.core.bean.Result;
import io.swagger.annotations.Api;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传的控制层
 */
@RestController
@RequestMapping(value = "/api/v1/house")
@Api(tags = "文件上传")
public class FileController {

    @Value("${fileServer.url}")
    private String url;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/fileUpload")
    public Result fileUpload(@RequestParam MultipartFile file) throws Exception{
        //文件上传
        String path = FileUtil.fileUpload(file);
        //判断成功还是失败
        if(StringUtils.isEmpty(path)){
            return Result.fail();
        }
        //返回结果 [0]= 组名 [1] = 全量路径和文件名
        return Result.ok(url + path);
    }
}
