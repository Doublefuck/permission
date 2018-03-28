package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mmall.bean.LogType;
import com.mmall.common.RequestHolder;
import com.mmall.dao.*;
import com.mmall.dto.SearchLogDto;
import com.mmall.exception.ParamException;
import com.mmall.module.*;
import com.mmall.param.SearchLogParam;
import com.mmall.service.ISysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 记录权限操作日志信息
 * Created by Administrator on 2018/3/26 0026.
 */
@Service("iSysLogService")
public class SysLogService implements ISysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Override
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_DEPT);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_USER);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ACL_MODULE);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ACL);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLogWithBLOBs.setStatus(1);
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperatorTime(new Date());

        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

//    @Override
//    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
//        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
//        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_ACL);
//        sysLogWithBLOBs.setTargetId(roleId);
//        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
//        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLogWithBLOBs.setStatus(1);
//        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        sysLogWithBLOBs.setOperatorTime(new Date());
//    }

//    @Override
//    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
//        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
//        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_USER);
//        sysLogWithBLOBs.setTargetId(roleId);
//        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
//        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLogWithBLOBs.setStatus(1);
//        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLogWithBLOBs.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//        sysLogWithBLOBs.setOperatorTime(new Date());
//    }

    /**
     * 动态搜索日志数据
     * TODO 分页操作
     * @param searchLogParam
     * @return
     */
    @Override
    public List<SysLogWithBLOBs> searchLog(SearchLogParam searchLogParam) {
        BeanValidator.check(searchLogParam);
        SearchLogDto searchLogDto = new SearchLogDto();
        searchLogDto.setType(searchLogParam.getType());
        // 组装搜索参数
        if (StringUtils.isNotBlank(searchLogParam.getBeforeSeq())) {
            searchLogDto.setBeforeSeq("%" + searchLogParam.getBeforeSeq() + "%");
        }
        if (StringUtils.isNotBlank(searchLogParam.getAfterSeq())) {
            searchLogDto.setAfterSeq("%" + searchLogParam.getAfterSeq() + "%");
        }
        if (StringUtils.isNotBlank(searchLogParam.getOperator())) {
            searchLogDto.setOperator("%" + searchLogParam.getOperator() + "%");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(searchLogParam.getFromTime())) {
                searchLogDto.setFromTime(sdf.parse(searchLogParam.getFromTime()));
            }
            if (StringUtils.isNotBlank(searchLogParam.getToTime())) {
                searchLogDto.setToTime(sdf.parse(searchLogParam.getToTime()));
            }
        } catch (Exception e) {
            throw new ParamException("传入的日期格式有问题，正确的格式为：yyyy-MM-dd HH:mm:ss");
        }
        // TODO 分页操作
        // 如果能查询出日志
        int count = sysLogMapper.countBySearchDto(searchLogDto);
        if (count > 0) {
            // 开始查询列表
            List<SysLogWithBLOBs> sysLogList = sysLogMapper.getLogListBySearchDto(searchLogDto);
            return sysLogList;
        }
        return Lists.newArrayList();
    }

    /**
     * 操作还原
     * @param id
     */
    @Override
    public void recover(int id) {
        SysLogWithBLOBs sysLogWithBLOBs = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLogWithBLOBs, "带还原的记录不存在");
        switch (sysLogWithBLOBs.getType()) {
            // 还原部门操作
            case LogType.TYPE_DEPT :
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(beforeDept, "待还原的部门已经不存在");
                if (StringUtils.isBlank(sysLogWithBLOBs.getNewValue()) || StringUtils.isBlank(sysLogWithBLOBs.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysDept afterDept = JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<SysDept>() {});
                afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterDept.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterDept.setOperatorTime(new Date());
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeDept, afterDept);
                break;
            // 还原用户操作
            case LogType.TYPE_USER :
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(beforeUser, "待还原的用户已经不存在");
                if (StringUtils.isBlank(sysLogWithBLOBs.getNewValue()) || StringUtils.isBlank(sysLogWithBLOBs.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysUser afterUser = JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<SysUser>() {});
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterUser.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperatorTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser, afterUser);
                break;
            // 还原权限模块操作
            case LogType.TYPE_ACL_MODULE :
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的用户已经不存在");
                if (StringUtils.isBlank(sysLogWithBLOBs.getNewValue()) || StringUtils.isBlank(sysLogWithBLOBs.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<SysAclModule>() {});
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperatorTime(new Date());
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            // 还原权限点操作
            case LogType.TYPE_ACL :
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的用户已经不存在");
                if (StringUtils.isBlank(sysLogWithBLOBs.getNewValue()) || StringUtils.isBlank(sysLogWithBLOBs.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<SysAcl>() {});
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAcl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperatorTime(new Date());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            // 角色还原操作
            case LogType.TYPE_ROLE :
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的用户已经不存在");
                if (StringUtils.isBlank(sysLogWithBLOBs.getNewValue()) || StringUtils.isBlank(sysLogWithBLOBs.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysRole afterRole = JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<SysRole>() {});
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterRole.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperatorTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            // 角色权限还原操作
            case LogType.TYPE_ROLE_ACL :
                SysRole sysAclRole = sysRoleMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(sysAclRole, "待还原的角色已经不存在");
                sysRoleAclService.changeRoleAcls(sysLogWithBLOBs.getTargetId(), JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                break;
            // 角色用户还原操作
            case LogType.TYPE_ROLE_USER :
                SysRole sysUserRole = sysRoleMapper.selectByPrimaryKey(sysLogWithBLOBs.getTargetId());
                Preconditions.checkNotNull(sysUserRole, "待还原的角色已经不存在");
                sysRoleUserService.changeRoleUsers(sysLogWithBLOBs.getTargetId(), JsonMapper.string2Obj(sysLogWithBLOBs.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                break;
            default :;

        }
    }
}
