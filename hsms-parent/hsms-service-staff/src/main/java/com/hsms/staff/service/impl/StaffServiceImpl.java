package com.hsms.staff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.mapper.StaffsMapper;
import com.hsms.core.pojo.Staffs;
import com.hsms.staff.service.StaffService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * StaffService实现类
 *
 * @author haotchen
 * @time 2022/11/9-13:36
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
public class StaffServiceImpl implements StaffService {

    // Staff Mapper映射
    @Autowired
    StaffsMapper staffsMapper;

    /**
     * 查询所有员工信息
     *
     * @return List<Staffs>
     */
    @Override
    public List<Staffs> queryStaffList() {
        return staffsMapper.selectList(null);
    }

    /**
     * 根据条件分页查询员工信息
     *
     * @param index  起始页码
     * @param size   页面大小
     * @param staffs 查询条件
     * @return Page<Staffs>
     */
    @Override
    public Page<Staffs> queryStaffPage(Integer index, Integer size, Staffs staffs) {
        // 参数校验
        if (Objects.isNull(index) || Objects.isNull(size)) {
            throw new RuntimeException("index or size is not null !");
        }

        if (Objects.isNull(staffs)) {
            return staffsMapper.selectPage(new Page<>(index,size),null);
        }

        // 添加查询条件
        LambdaQueryWrapper<Staffs> wrapper = new LambdaQueryWrapper<Staffs>()
                .eq(!StringUtils.isNullOrEmpty(staffs.getName()), Staffs::getName, staffs.getName())
                .eq(!StringUtils.isNullOrEmpty(staffs.getStaffRank()), Staffs::getStaffRank, staffs.getStaffRank());
        return staffsMapper.selectPage(new Page<>(index,size),wrapper);
    }

    /**
     * 根据条件查询单个员工信息
     *
     * @param staffName 员工姓名必填
     * @param staffs    其他条件
     * @return Staffs
     */
    @Override
    public Staffs queryStaffByCondition(String staffName, Staffs staffs) {
        // 参数校验
        if (Objects.isNull(staffName)) {
            throw new RuntimeException("staffName is not null !");
        }

        // 校验staffs 是否为空如果为空只通过员工姓名查询
        if (Objects.isNull(staffs)) {
            LambdaQueryWrapper<Staffs> wrapper = new LambdaQueryWrapper<Staffs>()
                    .eq(Staffs::getName, staffName);
            return staffsMapper.selectOne(wrapper);
        }

        // staffs 不为空,根据条件进行查询
        LambdaQueryWrapper<Staffs> wrapper = new LambdaQueryWrapper<Staffs>()
                .eq(Staffs::getName, staffName)
                .eq(!StringUtils.isNullOrEmpty(staffs.getStaffRank()),Staffs::getStaffRank,staffs.getStaffRank())
                .eq(!StringUtils.isNullOrEmpty(staffs.getState()),Staffs::getState,staffs.getState());
        return staffsMapper.selectOne(wrapper);
    }

    /**
     * 根据Id删除员工
     *
     * @param id 员工id
     */
    @Override
    public void deleteStaffById(Long id) {
        // 参数校验
        if (Objects.isNull(id)) {
            throw new RuntimeException("ID is not null !");
        }
        staffsMapper.deleteById(id);
    }

    /**
     * 添加单个员工信息
     *
     * @param staffs 员工信息
     */
    @Override
    public void addStaff(Staffs staffs) {
        // 参数校验
        if (Objects.isNull(staffs)) {
            throw new RuntimeException("staffs is not null !");
        }
        if (staffs.getName() == null || staffs.getStaffRank() == null) {
            throw new RuntimeException("参数不合法,请重新提交 !");
        }
        // 员工在职状态改为0 表示在职
        staffs.setState("0");
        staffsMapper.insert(staffs);
    }

    /**
     * 修改员工信息(id不可改)
     *
     * @param staffs 员工信息
     */
    @Override
    public void updateStaff(Staffs staffs) {
        // 参数校验
        if (Objects.isNull(staffs)) {
            return;
        }
        if (!"0".equals(staffs.getState()) && !"1".equals(staffs.getState())) {
            throw new RuntimeException("参数不合法,请重新提交 !");
        }
        // 修改员工信息
        LambdaUpdateWrapper<Staffs> wrapper = new LambdaUpdateWrapper<Staffs>()
                .eq(Staffs::getId, staffs.getId())
                .set(StringUtils.isNullOrEmpty(staffs.getName()), Staffs::getName, staffs.getName())
                .set(StringUtils.isNullOrEmpty(staffs.getStaffRank()), Staffs::getStaffRank, staffs.getStaffRank())
                .set(StringUtils.isNullOrEmpty(staffs.getState()), Staffs::getState, staffs.getState());
        staffsMapper.update(staffs,wrapper);
    }
}
