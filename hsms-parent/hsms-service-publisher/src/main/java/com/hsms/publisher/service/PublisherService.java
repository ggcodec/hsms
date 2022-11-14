package com.hsms.publisher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.pojo.Publishers;

/**
 * 发布者信息接口
 */
public interface PublisherService {

    /**
     * 根据电话号码查找发布者信息
     * @param phone
     * @return
     */
    public Publishers queryPublisherByPhone(String phone);

    /**
     * 根据id 查找发布者信息
     * @param id
     * @return
     */
    public Publishers queryPublisherById(Long id);

    /**
     * 新增发布者信息
     * @param publishers
     */
    public void addPublisher(Publishers publishers);


    /**
     * 根据条件修改发布者信息
     * @param publishers
     */
    public void updatePublisher(Publishers publishers);

    /**
     * 分页查询发布者列表
     */
    public Page<Publishers> queryPublishersPage(Integer index, Integer size);

    /**
     * 删除发布者信息
     */
    public Publishers deletePublishersById(Long id);
}
