package com.hsms.house.service;


import com.hsms.core.pojo.PriceState;

import java.util.List;

/**
 * 价格和状态变更
 */
public interface PriceStateService {

    /**
     * 新增价格
     */
    public void addPriceAndState(PriceState priceState);

    /**
     * 查询所有价格列表
     * @return
     */
    public List<PriceState> queryPriceStateList();

    /**
     * 根据条件查询价格对应信息
     * @param price
     * @param state
     * @return
     */
    public PriceState queryPriceState(Double price,String state);
}
