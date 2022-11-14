package com.hsms.house.service;

import com.hsms.core.pojo.Images;

/**
 * 房源信息图片
 */
public interface ImageService {

    /**
     * 根据房源id 新增图片
     * @param HouseId
     * @param images
     */
    public void addImage(Long HouseId, Images images);

    /**
     * 根据房源id 删除图片
     * @param HouseId
     */
    public void deleteImage(Long HouseId);



}
