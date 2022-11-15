package com.hsms.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsms.core.mapper.PriceStateMapper;
import com.hsms.core.pojo.PriceState;
import com.hsms.house.service.PriceStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * PriceStateServiceImpl
 *
 * @author haotchen
 * @time 2022/11/14-17:18
 */
@Service
public class PriceStateServiceImpl implements PriceStateService {
    // 映射
    @Autowired
    PriceStateMapper priceStateMapper;

    /**
     * 新增价格
     *
     * @param priceState
     */
    @Override
    public void addPriceAndState(PriceState priceState) {
        // 参数校验
        if (Objects.isNull(priceState.getPrice()) ||
                Objects.isNull(priceState.getState()) ||
                (!"1".equals(priceState.getState()) && !"0".equals(priceState.getState()))
        ) {
            throw new RuntimeException("参数不合法,请检查重试");
        }
        int insert = priceStateMapper.insert(priceState);
        if (insert <= 0) {
            throw new RuntimeException("新增价格失败,请检查重试");
        }

    }

    /**
     * 查询所有价格列表
     *
     * @return
     */
    @Override
    public List<PriceState> queryPriceStateList() {
        return priceStateMapper.selectList(null);
    }

    /**
     * 根据条件查询价格对应信息
     *
     * @param price
     * @param state
     * @return
     */
    @Override
    public PriceState queryPriceState(Double price, String state) {
        LambdaQueryWrapper<PriceState> wrapper = new LambdaQueryWrapper<PriceState>()
                .eq(PriceState::getPrice, price)
                .eq(PriceState::getState, state);
        // 查询并返回结果
        return priceStateMapper.selectOne(wrapper);
    }
}
