package com.mmall.dto;

import com.mmall.module.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 权限点组装对象，对SysAcl扩展
 * Created by Administrator on 2018/3/25 0025.
 */
@Setter
@Getter
@ToString
public class SysAclDto extends SysAcl {

    // 权限点是否被默认选中
    private boolean checked = false;

    // 权限点是否可被用户操作
    private boolean hasAcl = false;

    public static SysAclDto adapt(SysAcl sysAcl) {
        SysAclDto sysAclDto = new SysAclDto();
        BeanUtils.copyProperties(sysAcl, sysAclDto);
        return sysAclDto;
    }

}
