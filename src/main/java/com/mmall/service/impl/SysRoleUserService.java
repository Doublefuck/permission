package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.bean.LogType;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysLogMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.module.SysLogWithBLOBs;
import com.mmall.module.SysRoleUser;
import com.mmall.module.SysUser;
import com.mmall.service.ISysRoleUserService;
import com.mmall.util.IpUtil;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户角色管理
 * Created by Administrator on 2018/3/25 0025.
 */
@Service("iSysRoleUserService")
public class SysRoleUserService implements ISysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

//    @Resource
//    private ISysLogService iSysLogService;

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 根据roleId获取所有对应的用户
     * @param roleId
     * @return
     */
    @Override
    public JsonData getUserListByRoleId(int roleId) {
        // 根据roleId获取对应所有用户id
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return JsonData.fail("当前角色没有指定给任何用户");
        }
        // 获取对应的用户列表
        List<SysUser> sysUserList = sysUserMapper.getByUserIdList(userIdList);
        return JsonData.success(sysUserList);
    }

    /**
     * 修改当前角色下的用户信息
     * @param roleId
     * @param userIds
     * @return
     */
    @Override
    public JsonData changeRoleUsers(int roleId, List<Integer> userIds) {
        // 获取当前角色的用户id集合
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIds.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIds);
            originUserIdSet.removeAll(userIdSet);
            if (originUserIdSet.size() == 0) {
                return JsonData.fail("");
            }
        }
        updateRoleUsers(roleId, userIds);
        saveRoleUserLog(roleId, originUserIdList, userIds);
        return JsonData.success("更新当前角色下用户信息成功");
    }

    /**
     * 批量更新当前角色的用户信息
     * @param roleId
     * @param userIds
     */
    @Transactional
    private void updateRoleUsers(Integer roleId, List<Integer> userIds ) {
        // 首先移除roleId对应的用户数据
        sysRoleUserMapper.deleteUserByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }

        List<SysRoleUser> sysRoleUserList = Lists.newArrayList();
        for (Integer userId : userIds) {
            SysRoleUser sysRoleUser = SysRoleUser.builder().roleId(roleId).userId(userId).
                    operator(RequestHolder.getCurrentUser().getUsername()).operatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).
                    operatorTime(new Date()).build();
            sysRoleUserList.add(sysRoleUser);
        }
        // 批量插入新的用户角色数据
        sysRoleUserMapper.batchInsert(sysRoleUserList);
    }

    private void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_USER);
        sysLogWithBLOBs.setTargetId(roleId);
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }
}
