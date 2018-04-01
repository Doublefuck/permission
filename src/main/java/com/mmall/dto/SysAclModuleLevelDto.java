package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.module.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 权限模块dto对象，同时包含权限点集合数据
 * Created by Administrator on 2018/3/24 0024.
 */
@Setter
@Getter
public class SysAclModuleLevelDto extends SysAclModule {

    private List<SysAclModuleLevelDto> sysAclModuleLevelDtoList = Lists.newArrayList();

    private List<SysAclDto> sysAclDtoList = Lists.newArrayList();

    public static SysAclModuleLevelDto adapt(SysAclModule sysAclModule) {
        SysAclModuleLevelDto sysAclModuleLevelDto = new SysAclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, sysAclModuleLevelDto);
        return sysAclModuleLevelDto;
    }

}
