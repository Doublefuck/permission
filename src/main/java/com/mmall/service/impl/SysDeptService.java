package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.module.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.ISysDeptService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;


/**
 * Created by Administrator on 2018/3/17 0017.
 */
@Service("iSysDeptService")
public class SysDeptService implements ISysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 新增部门
     * @param deptParam
     */
    @Override
    public void save(DeptParam deptParam) {
        BeanValidator.check(deptParam); // 参数校验
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        // 组装部门类
        SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId()).
                seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
        // 组装部门层级
        String calculateLevel = LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId());
        sysDept.setLevel(calculateLevel);
        sysDept.setRemark(deptParam.getRemark());
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysDept.setOperatorTime(new Date());

        // 更新到数据库
        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * 更新部门
     * @param deptParam
     */
    @Override
    public void update(DeptParam deptParam) {
        BeanValidator.check(deptParam); // 参数校验
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        // 更新前的部门
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        SysDept after = SysDept.builder().id(deptParam.getId()).name(deptParam.getName()).parentId(deptParam.getParentId()).
                seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        after.setRemark(deptParam.getRemark());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date()); // TODO

        updateWihChild(before, after);
    }

    /**
     * 更新当前部门及其子部门
     * 原子性
     * @param before
     * @param after
     */
    @Transactional
    public void updateWihChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel(); // 新的部门的等级前缀
        String oldLevelPrefix = before.getLevel(); // 旧的部门的等级前缀
        // 更新前的部门等级与更新后的部门等级比较
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            // 处理子部门
            // 获取当前部门的所有子部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept sysDept : deptList) {
                    String level = sysDept.getLevel();
                    // 以旧的部门等级前缀开始,更新所有子部门层级
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysDept.setLevel(level);
                    }
                }
                // 批量更新部门层级
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }

        sysDeptMapper.updateByPrimaryKey(after);

    }

    // 校验相同层级下是否存在相同名称的部门
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId,deptName, deptId) > 0;
    }

    // 获取当前部门的层级
    private String getLevel(Integer deptId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }
}
