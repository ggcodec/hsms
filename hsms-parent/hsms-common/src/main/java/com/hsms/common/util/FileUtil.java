package com.hsms.common.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作相关的工具类
 */
public class FileUtil {

    /**
     * 在静态模块中初始化fastdfs的配置:只会加载一次
     */
    static {
        try {
            //读取配置文件的信息
            ClassPathResource resource = new ClassPathResource("tracker.conf");
            //进行fastDFS的初始化
            ClientGlobal.init(resource.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public static String fileUpload(MultipartFile file){
        try {
            //初始化tracker的连接
            TrackerClient trackerClient = new TrackerClient();
            //通过客户端获取服务端的实例
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过tacker获取storage的信息
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //获取文件的名字: abc.jpg
            String originalFilename = file.getOriginalFilename();
            //通过storage进行文件上传
            /**
             * 1.文件的字节码: 从文件取
             * 2.文件的拓展名: 通过文件名取
             * 3.文件的附加参数:
             */
            String[] strings = storageClient.upload_file(file.getBytes(),
                    StringUtils.getFilenameExtension(originalFilename),
                    null);
            //返回
            return strings[0] + "/" + strings[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
