package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.bean.LogType;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysLogMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.module.SysLogWithBLOBs;
import com.mmall.module.SysRoleAcl;
import com.mmall.service.ISysRoleAclService;
import com.mmall.util.IpUtil;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色权限关系管理
 * Created by Administrator on 2018/3/25 0025.
 */
@Service("iSysRoleAclService")
public class SysRoleAclService implements ISysRoleAclService {

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

//    @Resource
//    private ISysLogService iSysLogService;

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 根据角色id更新该角色的权限点
     * @param roleId
     * @param aclIdList
     * @return
     */
    @Override
    public JsonData changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        // 获取当前角色的权限id集合
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdByRoleIdList(Lists.newArrayList(roleId));
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (originAclIdSet.size() == 0) {
                return JsonData.fail("");
            }
        }
        updateRoleAcl(roleId, aclIdList);
        saveRoleAclLog(roleId, originAclIdList, aclIdList);
        return JsonData.success("更新成功");
    }

    /**
     * 批量更新角色权限
     * @param roleId
     * @param aclIdList
     */
    @Transactional
    public void updateRoleAcl(Integer roleId, @RequestParam(value = "aclIdList", required = false, defaultValue = "") List<Integer> aclIdList) {
        // 移除原来的角色权限点
        sysRoleAclMapper.deleteAclByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAcl> sysRoleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).
                    operator(RequestHolder.getCurrentUser().getUsername()).
                    operatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operatorTime(new Date()).build();
            sysRoleAclList.add(sysRoleAcl);
        }
        // 批量增加
        sysRoleAclMapper.batchInsert(sysRoleAclList);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_ACL);
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
