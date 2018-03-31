package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.module.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 部门层级树
 * 构建部门数据传输对象，继承自SysDept，同时集成自身的List数据包装
 * Created by Administrator on 2018/3/22 0022.
 */
@Setter
@Getter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptLevelDtoList = Lists.newArrayList();

    /**
     * 根据SysDept组装DeptLevelDto
     * springframework中的BeanUtils能够实现根据一个对象复制新的对象
     * @param sysDept
     * @return
     */
    public static DeptLevelDto adapt(SysDept sysDept) {
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        // 将sysDept中成员变量组装成deptLevelDto对象
        BeanUtils.copyProperties(sysDept, deptLevelDto);
        return deptLevelDto;
    }
}
