package com.hsms.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsms.core.pojo.Houses;
import com.hsms.core.pojo.RequestBodyHouse;

import java.util.List;

/**
 * 房源信息服务
 */
public interface HouseService {

    /**
     * 分页查询房源信息
     *
     * @param index
     * @param size
     * @return
     */
    public IPage<Houses> queryHousePage(Integer index, Integer size);

    /**
     * 根据ID查询房源信息
     *
     * @param id
     * @return
     */
    public Houses queryHouseById(Long id);

    /**
     * 根据发布者ID查询房源信息
     *
     * @param PubId
     * @return
     */
    public List<Houses> queryHouseByPublisher(Long PubId);

    /**
     * 根据价格范围查询房源信息
     *
     * @param min
     * @param max
     * @return
     */
    public List<Houses> queryHouseByPrice(Double min, Double max);

    /**
     * 根据员工id查询房源信息
     *
     * @param staffId
     * @return
     */
    public List<Houses> queryHouseByStaffId(Long staffId);

    /**
     * 根据地址模糊查询房源信息
     *
     * @param reg
     * @return
     */
    public List<Houses> queryHouseByAddressReg(String reg);

    /**
     * 根据房源信息id删除房源
     * @param id
     */
    public Houses deleteHouseById(Long id);

    /**
     * 新增房源信息
     * @param requestBodyHouse
     */
    public void addHouse(RequestBodyHouse requestBodyHouse);



}
