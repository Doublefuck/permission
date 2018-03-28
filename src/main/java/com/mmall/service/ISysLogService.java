package com.mmall.service;

import com.mmall.module.*;
import com.mmall.param.SearchLogParam;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
public interface ISysLogService {

    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);

    //void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after);

    //void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after);

    /**
     * 搜索日志
     * @param searchLogParam
     * @return
     */
    List<SysLogWithBLOBs> searchLog(SearchLogParam searchLogParam);

    /**
     * 操作还原
     * @param id
     */
    void recover(int id);
}
