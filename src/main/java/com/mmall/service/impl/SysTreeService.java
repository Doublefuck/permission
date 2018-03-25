package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.dto.SysAclModuleLevelDto;
import com.mmall.module.SysAclModule;
import com.mmall.module.SysDept;
import com.mmall.service.ISysTreeService;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@Service("iSysTreeService")
public class SysTreeService implements ISysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    /**
     * 组装部门树
     * @return
     */
    @Override
    public List<DeptLevelDto> deptTree() {
        // 获取所有部门
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> deptLevelDtoList = new ArrayList<>();

        for (SysDept sysDept : deptList) {
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            deptLevelDtoList.add(deptLevelDto);
        }
        return deptListToTree(deptLevelDtoList);
    }

    /**
     * 组装权限模块树
     * @return
     */
    @Override
    public List<SysAclModuleLevelDto> aclModuleTree() {
        // 获取所有的权限模块
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<SysAclModuleLevelDto> sysAclModuleLevelDtoList = Lists.newArrayList();
        for (SysAclModule sysAclModule : aclModuleList) {
            SysAclModuleLevelDto sysAclModuleLevelDto = SysAclModuleLevelDto.adapt(sysAclModule);
            sysAclModuleLevelDtoList.add(sysAclModuleLevelDto);
        }

        return aclModuleListToTree(sysAclModuleLevelDtoList);
    }

    public List<SysAclModuleLevelDto> aclModuleListToTree(List<SysAclModuleLevelDto> sysAclModuleLevelDtoList) {
        if (CollectionUtils.isEmpty(sysAclModuleLevelDtoList)) {
            return Lists.newArrayList(); // 返回空集合
        }
        // 以level为key，以相同level的权限模块作为value
        // level-->[aclmodule1,aclmodule2,aclmodule3,...]，类似于Map<level, List<Dept>>
        Multimap<String, SysAclModuleLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        // 一级权限模块
        List<SysAclModuleLevelDto> rootList = Lists.newArrayList();
        for (SysAclModuleLevelDto sysAclModuleLevelDto : sysAclModuleLevelDtoList) {
            levelDtoMultimap.put(sysAclModuleLevelDto.getLevel(), sysAclModuleLevelDto);
            // 判断当前权限模块是否是最顶级的权限模块
            if (LevelUtil.ROOT.equals(sysAclModuleLevelDto.getLevel())) {
                rootList.add(sysAclModuleLevelDto);
            }
        }
        // 对第一层权限模块排序
        Collections.sort(rootList, new Comparator<SysAclModuleLevelDto>() {
            @Override
            public int compare(SysAclModuleLevelDto o1, SysAclModuleLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelDtoMultimap);
        return rootList;
    }

    public void transformAclModuleTree(List<SysAclModuleLevelDto> sysAclModuleLevelDtoList, String level, Multimap levelDtoMultimap) {
        for (int i = 0 ;i < sysAclModuleLevelDtoList.size() ; i++) {
            // 遍历该层的每个元素
            SysAclModuleLevelDto sysAclModuleLevelDto = sysAclModuleLevelDtoList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, sysAclModuleLevelDto.getId());
            // 处理下一层
            List<SysAclModuleLevelDto> tempAclModuleList =(List<SysAclModuleLevelDto>) levelDtoMultimap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempAclModuleList)) {
                // 排序
                Collections.sort(tempAclModuleList, new Comparator<com.mmall.dto.SysAclModuleLevelDto>() {
                    @Override
                    public int compare(SysAclModuleLevelDto o1, SysAclModuleLevelDto o2) {
                        return o1.getSeq() - o2.getSeq();
                    }
                });
                // 设置下一层部门
                sysAclModuleLevelDto.setSysAclModuleLevelDtoList(tempAclModuleList);
                // 进入到下一层部门处理（递归）
                transformAclModuleTree(tempAclModuleList, nextLevel, levelDtoMultimap);
            }
        }
    }

    /**
     * 递归
     * 获取deptLevelDtoList中的deptLevelDtoList
     * 处理第一层级部门
     * @param deptLevelDtoList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Lists.newArrayList(); // 返回空集合
        }
        // 以level为key，以相同level的部门作为value
        // level-->[dept1,dept2,dept3,...]，类似于Map<level, List<Dept>>
        Multimap<String, DeptLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        // 一级部门
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            levelDtoMultimap.put(deptLevelDto.getLevel(), deptLevelDto);
            // 判断当前部门是否是最顶级部门
            if (LevelUtil.ROOT.equals(deptLevelDto.getLevel())) {
                rootList.add(deptLevelDto);
            }
        }

        // rootList排序，按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        transformDeptTree(rootList, LevelUtil.ROOT, levelDtoMultimap);
        return rootList;
    }

    /**
     * 根据第一层级部门递归处理下一层及部门
     * 0-->0.1,0.2...
     * 0.1-->0.1.1,0.1.2...
     * 0.2-->0.2.1,0.2.2...
     * @param deptLevelDtoList
     * @param level
     * @param levelDtoMultimap
     */
    public void transformDeptTree(List<DeptLevelDto> deptLevelDtoList, String level, Multimap levelDtoMultimap) {
        for (int i = 0 ;i < deptLevelDtoList.size() ; i++) {
            // 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<DeptLevelDto> tempDeptList =(List<DeptLevelDto>) levelDtoMultimap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptLevelDtoList(tempDeptList);
                // 进入到下一层部门处理
                transformDeptTree(tempDeptList, nextLevel, levelDtoMultimap);
            }
        }
    }

    /**
     * 根据seq比较部门排序
     */
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
