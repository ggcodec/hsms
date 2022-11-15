package com.hsms.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.mapper.HousesMapper;
import com.hsms.core.mapper.ImagesMapper;
import com.hsms.core.mapper.PriceStateMapper;
import com.hsms.core.pojo.Houses;
import com.hsms.core.pojo.Images;
import com.hsms.core.pojo.PriceState;
import com.hsms.core.pojo.RequestBodyHouse;
import com.hsms.house.service.HouseService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * HouseService 实现类
 *
 * @author haotchen
 * @time 2022/11/14-14:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HouseServiceImpl implements HouseService {

    // 房源信息映射
    @Autowired
    HousesMapper housesMapper;

    @Autowired
    PriceStateMapper priceStateMapper;
    @Autowired
    ImagesMapper imagesMapper;

    /**
     * 分页查询房源信息
     *
     * @param index
     * @param size
     * @return
     */
    @Override
    public IPage<Houses> queryHousePage(Integer index, Integer size) {
        // 参数校验
        if (index < 0 || size < 0) {
            throw new RuntimeException("参数有误,请检查重试");
        }

        // 查询返回结果
        return housesMapper.selectPage(new Page<>(index,size),null);
    }

    /**
     * 根据ID查询房源信息
     *
     * @param id
     * @return
     */
    @Override
    public Houses queryHouseById(Long id) {
        // 参数校验
        if (id < 0) {
            throw new RuntimeException("参数有误,请检查重试");
        }

        // 查询返回结果
        return housesMapper.selectById(id);
    }

    /**
     * 根据发布者ID查询房源信息
     *
     * @param PubId
     * @return
     */
    @Override
    public List<Houses> queryHouseByPublisher(Long PubId) {
        // 参数校验
        if (PubId < 0) {
            throw new RuntimeException("参数有误,请检查重试");
        }

        LambdaQueryWrapper<Houses> wrapper = new LambdaQueryWrapper<Houses>()
                .eq(Houses::getPublisherId, PubId);

        // 查询返回结果
        return housesMapper.selectList(wrapper);
    }

    /**
     * 根据价格范围查询房源信息
     *
     * @param min
     * @param max
     * @return
     */
    @Override
    public List<Houses> queryHouseByPrice(Double min, Double max) {
        // 参数校验
        if (min < 0 || max < min) {
            throw new RuntimeException("参数有误,请检查重试");
        }
        // 通过范围先查询出所有符合条件的价格id
        LambdaQueryWrapper<PriceState> wrapper = new LambdaQueryWrapper<PriceState>()
                .eq(PriceState::getState,"0")
                .ge(PriceState::getPrice,min)
                        .le(PriceState::getPrice,max);

        List<PriceState> priceStates = priceStateMapper.selectList(wrapper);
        if (Objects.isNull(priceStates) || priceStates.size() <= 0) {
            return null;
        }

        // 创建一个列表集合存放price id
        ArrayList<Long> longs = new ArrayList<>();
        for (PriceState priceState : priceStates) {
            longs.add(priceState.getId());
        }

        // 根据价格id 进行查询
        LambdaQueryWrapper<Houses> in = new LambdaQueryWrapper<Houses>()
                .in(Houses::getPriceStateId, longs);

        // 将所有符合条件的House全部返回
        return housesMapper.selectList(in);
    }

    /**
     * 根据员工id查询房源信息
     *
     * @param staffId
     * @return
     */
    @Override
    public List<Houses> queryHouseByStaffId(Long staffId) {
        // 参数校验
        if (staffId < 0) {
            return null;
        }

        // 通过条件查询
        LambdaQueryWrapper<Houses> wrapper = new LambdaQueryWrapper<Houses>()
                .eq(Houses::getStaffId, staffId);

        return housesMapper.selectList(wrapper);
    }

    /**
     * 根据地址模糊查询房源信息
     *
     * @param reg
     * @return
     */
    @Override
    public List<Houses> queryHouseByAddressReg(String reg) {
        // 参数校验
        if (StringUtils.isNullOrEmpty(reg)) {
            return null;
        }
        LambdaQueryWrapper<Houses> like = new LambdaQueryWrapper<Houses>()
                .like(Houses::getAddress, reg);
        // 根据地址模糊查询并匹配内容
        return housesMapper.selectList(like);
    }

    /**
     * 新增房源信息
     *
     * @param requestBodyHouse
     */
    @Override
    public void addHouse(RequestBodyHouse requestBodyHouse) {
        // 先插入House获取id
        int houseInsert = housesMapper.insert(requestBodyHouse);
        if (houseInsert <= 0) {
            throw new RuntimeException(requestBodyHouse.getId() + " 房源信息插入失败,请稍后重试");
        }

        // 插入图片链接
        if (requestBodyHouse.getUrls() != null) {
            for (String url : requestBodyHouse.getUrls()) {
                Images images = new Images();
                images.setHouseId(requestBodyHouse.getId());
                images.setUrl(url);
                int insert = imagesMapper.insert(images);
                if (insert <= 0) {
                    throw new RuntimeException(url + " 图片插入失败,请稍后重试");
                }
            }
        }
    }

    /**
     * 根据房源信息id删除房源
     *
     * @param id
     */
    @Override
    public Houses deleteHouseById(Long id) {
        // 参数校验
        if (id < 0) {
            return null;
        }

        Houses houses = queryHouseById(id);

        if (Objects.isNull(houses)) {
            return null;
        }
        housesMapper.deleteById(id);
        return houses;
    }
}
