package com.hsms.staff.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsms.core.pojo.Staffs;

import java.util.List;

/**
 * 员工信息相关服务接口
 */
public interface StaffService {

    /**
     * 查询所有员工信息
     * @return List<Staffs>
     */
    public List<Staffs> queryStaffList();

    /**
     * 根据条件分页查询员工信息
     *
     * @param index  起始页码
     * @param size   页面大小
     * @param staffs 查询条件
     * @return
     */
    public Page<Staffs> queryStaffPage(Integer index, Integer size, Staffs staffs);

    /**
     * 根据条件查询单个员工信息
     * @param staffName 员工姓名必填
     * @param staffs    其他条件
     * @return Staffs
     */
    public Staffs queryStaffByCondition(String staffName,Staffs staffs);

    /**
     * 根据Id删除员工
     * @param id 员工id
     */
    public void deleteStaffById(Long id);

    /**
     * 添加单个员工信息
     * @param staffs 员工信息
     */
    public void addStaff(Staffs staffs);

    /**
     * 修改员工信息(id不可改)
     * @param staffs 员工信息
     */
    public void updateStaff(Staffs staffs);

}
