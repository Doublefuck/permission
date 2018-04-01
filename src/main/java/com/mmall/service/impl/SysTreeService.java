package com.mmall.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.dto.SysAclDto;
import com.mmall.dto.SysAclModuleLevelDto;
import com.mmall.module.SysAcl;
import com.mmall.module.SysAclModule;
import com.mmall.module.SysDept;
import com.mmall.service.ISysCoreService;
import com.mmall.service.ISysTreeService;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@Service("iSysTreeService")
public class SysTreeService implements ISysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private ISysCoreService iSysCoreService;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 获取某一用户的权限信息
     * @param userId
     * @return
     */
    public List<SysAclModuleLevelDto> userAclTree(int userId) {
        // 获取当前用户的被分配权限点
        List<SysAcl> userSysAclList = iSysCoreService.getUserAclList(userId);
        List<SysAclDto> sysAclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : userSysAclList) {
            SysAclDto sysAclDto = SysAclDto.adapt(sysAcl);
            sysAclDto.setChecked(true);
            sysAclDto.setHasAcl(true);
            sysAclDtoList.add(sysAclDto);
        }
        return aclListToTree(sysAclDtoList);
    }

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
        //return deptLevelDtoList;
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

    /**
     * 获取角色权限树数据
     * @param roleId
     * @return
     */
    @Override
    public List<SysAclModuleLevelDto> roleTree(Integer roleId) {
        // 获取当前用户的被分配权限点
        List<SysAcl> userSysAclList = iSysCoreService.getCurrentUserAclList();
        // 当前角色被分配的权限点
        List<SysAcl> roleSysAclList = iSysCoreService.getRoleAclList(roleId);

        // 当前用户的权限点id集合列表
        Set<Integer> userSysAclIdSet = Sets.newHashSet();
        Iterator iterator = userSysAclList.iterator();
        while (iterator.hasNext()) {
            SysAcl sysAcl = (SysAcl) iterator.next();
            Integer sysAclId = sysAcl.getAclId();
            userSysAclIdSet.add(sysAclId);
        }
        // 某一角色已分配的权限点id集合列表
        Set<Integer> roleSysAclIdSet = Sets.newHashSet();
        iterator = roleSysAclList.iterator();
        while (iterator.hasNext()) {
            SysAcl sysAcl = (SysAcl) iterator.next();
            Integer sysAclId = sysAcl.getAclId();
            roleSysAclIdSet.add(sysAclId);
        }
        // 当前系统的所有权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        // 当前用户和角色的权限点的并集
        // Set<SysAcl> aclSet = new HashSet<>(allAclList);
        // aclSet.addAll(userSysAclList);

        List<SysAclDto> sysAclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : allAclList) {
            SysAclDto sysAclDto = SysAclDto.adapt(sysAcl);
            // 如果当前用户的权限点id包括并集中的权限点id
            if (userSysAclIdSet.contains(sysAcl.getAclId())) {
                sysAclDto.setHasAcl(true); // 权限点可操作
            }
            // 如果并集中的权限点id在角色的权限点中存在
            if (roleSysAclIdSet.contains(sysAcl.getAclId())) {
                sysAclDto.setChecked(true); // 权限点默认被选中
            }
            sysAclDtoList.add(sysAclDto);
        }
        return aclListToTree(sysAclDtoList);
    }

    /**
     * 分别获取系统所有的权限模块和权限点
     * 递归组装权限点+权限模块
     * @param sysAclDtoList
     * @return
     */
    public List<SysAclModuleLevelDto> aclListToTree(List<SysAclDto> sysAclDtoList) {
        if (CollectionUtils.isEmpty(sysAclDtoList)) {
            return Lists.newArrayList();
        }
        // 获取系统权限模块树
        List<SysAclModuleLevelDto> aclModuleLevelDtoList = aclModuleTree();
        // 每一个权限模块下的权限点列表
        Multimap<Integer, SysAclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (SysAcl sysAcl : sysAclDtoList) {
            // 权限点可用
            if (sysAcl.getStatus() == 1) {
                moduleIdAclMap.put(sysAcl.getAclModuleId(), SysAclDto.adapt(sysAcl));
            }
        }
        bindAclsWithOrder(aclModuleLevelDtoList, moduleIdAclMap);
        return aclModuleLevelDtoList;
    }

    /**
     * 将权限点绑定在权限模块下
     * @param aclModuleLevelDtoList
     * @param moduleIdAclMap
     */
    public void bindAclsWithOrder(List<SysAclModuleLevelDto> aclModuleLevelDtoList, Multimap<Integer, SysAclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            return;
        }
        for (SysAclModuleLevelDto sysAclModuleLevelDto : aclModuleLevelDtoList) {
            // 根据模块id获取权限点列表
            List<SysAclDto> sysAclDtoList = (List<SysAclDto>) moduleIdAclMap.get(sysAclModuleLevelDto.getAclModuleId());
            if (CollectionUtils.isNotEmpty(sysAclDtoList)) {
                // 排序
                Collections.sort(sysAclDtoList, new Comparator<SysAclDto>() {
                    @Override
                    public int compare(SysAclDto o1, SysAclDto o2) {
                        return o1.getSeq() - o2.getSeq();
                    }
                });
                sysAclModuleLevelDto.setSysAclDtoList(sysAclDtoList);
            }
            // 递归
            bindAclsWithOrder(sysAclModuleLevelDto.getSysAclModuleLevelDtoList(), moduleIdAclMap);
        }
    }

    /**
     * 组装第一层级权限模块
     * @param sysAclModuleLevelDtoList
     * @return
     */
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

    /**
     * 以第一层级权限模块数据起始，进行逐层递归获取权限模块
     * @param sysAclModuleLevelDtoList
     * @param level
     * @param levelDtoMultimap
     */
    public void transformAclModuleTree(List<SysAclModuleLevelDto> sysAclModuleLevelDtoList, String level, Multimap levelDtoMultimap) {
        for (int i = 0 ;i < sysAclModuleLevelDtoList.size() ; i++) {
            // 遍历该层的每个元素
            SysAclModuleLevelDto sysAclModuleLevelDto = sysAclModuleLevelDtoList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, sysAclModuleLevelDto.getAclModuleId());
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
     * 处理第一层级部门，即根部门
     * @param deptLevelDtoList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Lists.newArrayList(); // 返回空集合
        }
        // 以level（部门层级）为key，以相同level的部门作为value
        // 当前部门层级下可能存在多个部门
        // level-->[dept1,dept2,dept3,...]，类似于Map<level, List<Dept>>
        Multimap<String, DeptLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        // 一级部门
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            // 将部门信息和对应的部门层级放入到multimap中，可能存在一个层级对应多个部门信息
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
        // 开始递归
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
            // 处理当前层级的数据，组装成下一层级的level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getDeptId());
            // 根据nextlevel处理下一层
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
