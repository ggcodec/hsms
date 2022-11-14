package com.hsms.publisher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.mapper.PublishersMapper;
import com.hsms.core.pojo.Publishers;
import com.hsms.publisher.service.PublisherService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * PublisherService实现类
 *
 * @author haotchen
 * @time 2022/11/13-15:58
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    // Mapper映射
    @Autowired
    PublishersMapper publishersMapper;

    /**
     * 根据电话号码查找发布者信息
     *
     * @param phone
     * @return
     */
    @Override
    public Publishers queryPublisherByPhone(String phone) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new RuntimeException("phone is not null or empty");
        }
        // 查询并返回结果
        return publishersMapper.selectOne(new LambdaQueryWrapper<Publishers>().eq(Publishers::getPhone, phone));
    }

    /**
     * 根据id 查找发布者信息
     *
     * @param id
     * @return
     */
    @Override
    public Publishers queryPublisherById(Long id) {
        // 参数校验
        if (Objects.isNull(id)) {
            throw new RuntimeException("id is not null");
        }
        // 通过id 查找并返回结果
        return publishersMapper.selectById(id);
    }

    /**
     * 新增发布者信息
     *
     * @param publishers
     */
    @Override
    public void addPublisher(Publishers publishers) {
        // 参数校验
        if (Objects.isNull(publishers)) {
            throw new RuntimeException("publishers Is not null");
        }

        // 执行查询
        publishersMapper.insert(publishers);
    }

    /**
     * 根据条件修改发布者信息
     *
     * @param publishers
     */
    @Override
    public void updatePublisher(Publishers publishers) {
        // 参数校验
        if (Objects.isNull(publishers)) {
            throw new RuntimeException("publishers Is not null");
        }
        if (Objects.isNull(publishers.getId())) {
            throw new RuntimeException("publishers.ID Is not null");
        }

        // 添加修改条件
        LambdaUpdateWrapper<Publishers> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Publishers::getId,publishers.getId());
        wrapper.set(!Objects.isNull(publishers.getName()),Publishers::getName,publishers.getName());
        wrapper.set(!Objects.isNull(publishers.getHouseCount()),Publishers::getHouseCount,publishers.getHouseCount());
        wrapper.set(!Objects.isNull(publishers.getAge()),Publishers::getAge,publishers.getAge());
        wrapper.set(!Objects.isNull(publishers.getPhone()),Publishers::getPhone,publishers.getPhone());

        // 执行修改
        publishersMapper.update(publishers,wrapper);
    }

    /**
     * 分页查询发布者列表
     *
     * @param index
     * @param size
     */
    @Override
    public Page<Publishers> queryPublishersPage(Integer index, Integer size) {
        if (index < 0 || size < 0) {
            throw new RuntimeException("分页参数不合法,请重试");
        }
        return publishersMapper.selectPage(new Page<>(index, size), null);
    }

    /**
     * 删除发布者信息
     *
     * @param id
     */
    @Override
    public Publishers deletePublishersById(Long id) {
        // 查询删除的发布者信息
        Publishers publishers = queryPublisherById(id);

        // 执行删除
        publishersMapper.deleteById(id);

        // 返回删除的信息
        return publishers;
    }
}
