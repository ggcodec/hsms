package com.hsms.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsms.core.mapper.ImagesMapper;
import com.hsms.core.pojo.Images;
import com.hsms.house.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * ImageService实现类
 *
 * @author haotchen
 * @time 2022/11/14-15:01
 */
@Service
public class ImageServiceImpl implements ImageService {

    // 图片处理映射
    @Autowired
    ImagesMapper imagesMapper;

    /**
     * 根据房源id 新增图片
     *
     * @param HouseId
     * @param images
     */
    @Override
    public void addImage(Long HouseId, Images images) {
        // 参数校验
        if (HouseId < 0 || Objects.isNull(images)) {
            throw new RuntimeException("添加图片异常,请校验参数");
        }
        images.setHouseId(HouseId);
        imagesMapper.insert(images);
    }

    /**
     * 根据房源id 删除图片
     *
     * @param HouseId
     */
    @Override
    public void deleteImage(Long HouseId) {
        // 参数校验
        if (HouseId < 0 ) {
            throw new RuntimeException("删除图片异常,请校验参数");
        }
        LambdaQueryWrapper<Images> wrapper = new LambdaQueryWrapper<Images>()
                .eq(Images::getHouseId, HouseId);
        imagesMapper.delete(wrapper);
    }

    /**
     * 根据房源id查询关联图片
     *
     * @param houseId
     * @return
     */
    @Override
    public List<Images> queryImageList(Long houseId) {
        QueryWrapper<Images> wrapper = new QueryWrapper<Images>()
                .eq("house_id", houseId);
        return imagesMapper.selectList(wrapper);
    }
}
