package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.ParamException;
import com.mmall.module.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.service.ISysAclModuleService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
@Service("iSysAclModuleService")
public class SysAclModuleService implements ISysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 删除权限模块
     * @param aclModuleId
     * @return
     */
    @Override
    public JsonData delete(int aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (sysAclModule == null) {
            throw new ParamException("待删除的权限模块不存在");
        }
        if (sysAclModuleMapper.countByParentId(aclModuleId) > 0) {
            throw new ParamException("当前权限模块下存在子模块，无法删除");
        }
        if (sysAclMapper.countByAclModuleId(aclModuleId) > 0) {
            throw new ParamException("当前权限模块下存在权限点，无法删除");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
        return null;
    }

    /**
     * 新增权限模块
     * @param aclModuleParam
     */
    @Override
    public JsonData save(AclModuleParam aclModuleParam) {
        BeanValidator.check(aclModuleParam);
        if (checkExists(aclModuleParam.getParentId(), aclModuleParam.getName(), aclModuleParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule sysAclModule = SysAclModule.builder().name(aclModuleParam.getName()).parentId(aclModuleParam.getParentId()).
                seq(aclModuleParam.getSeq()).status(aclModuleParam.getStatus()).remark(aclModuleParam.getRemark()).build();
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperatorTime(new Date());

        sysAclModuleMapper.insertSelective(sysAclModule);
        return JsonData.success(sysAclModule);
    }

    /**
     * 更新权限模块
     * @param aclModuleParam
     */
    @Override
    public JsonData update(AclModuleParam aclModuleParam) {
        BeanValidator.check(aclModuleParam);
        if (checkExists(aclModuleParam.getParentId(), aclModuleParam.getName(), aclModuleParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        // 更新前
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(aclModuleParam.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");
        // 构建新的权限模块
        SysAclModule after = SysAclModule.builder().id(aclModuleParam.getId()).name(aclModuleParam.getName()).parentId(aclModuleParam.getParentId()).
                seq(aclModuleParam.getSeq()).status(aclModuleParam.getStatus()).remark(aclModuleParam.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date());

        updateWithChild(before, after);
        return JsonData.success(after);
    }

    /**
     * 更新当前权限模块及其子权限模块
     * @param before
     * @param after
     */
    @Transactional
    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel(); // 新的权限模块的等级前缀
        String oldLevelPrefix = before.getLevel(); // 旧的权限模块的等级前缀
        // 更新前的权限模块等级与更新后的权限模块等级比较
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            // 处理子部门
            // 获取当前权限模块的所有子权限模块
            List<SysAclModule> sysAclModuleList = sysAclModuleMapper.getChildSysAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(sysAclModuleList)) {
                for (SysAclModule sysAclModule : sysAclModuleList) {
                    String level = sysAclModule.getLevel();
                    // 以旧的权限模块等级前缀开始,更新所有子权限模块层级
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysAclModule.setLevel(level);
                    }
                }
                // 批量更新权限模块层级
                sysAclModuleMapper.batchUpdateLevel(sysAclModuleList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 检查相同层级下是否存在相同名称的权限模块
     * @param parentId
     * @param sysAclModuleName
     * @param sysAclModuleId
     * @return
     */
    private boolean checkExists(Integer parentId, String sysAclModuleName, Integer sysAclModuleId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, sysAclModuleName, sysAclModuleId) > 0;
    }

    private String getLevel(Integer sysAclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(sysAclModuleId);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

}
