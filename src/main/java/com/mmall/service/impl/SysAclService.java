package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import com.mmall.module.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.service.ISysAclService;
import com.mmall.service.ISysLogService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */
@Service("iSysAclService")
public class SysAclService implements ISysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private ISysLogService iSysLogService;

    /**
     * 新增权限点
     * @param aclParam
     * @return
     */
    @Override
    public JsonData save(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if (checkExists(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getAclId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        int count = sysAclMapper.countAcl() + 10;
        SysAcl sysAcl = SysAcl.builder().aclId(count).name(aclParam.getName()).aclModuleId(aclParam.getAclModuleId()).url(aclParam.getUrl()).
                type(aclParam.getType()).status(aclParam.getStatus()).seq(aclParam.getSeq()).remark(aclParam.getRemark()).build();
        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperatorTime(new Date());
        sysAcl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysAclMapper.insertSelective(sysAcl);
        iSysLogService.saveAclLog(null, sysAcl);
        return JsonData.success(sysAcl);
    }

    /**
     * 更新权限点
     * @param aclParam
     * @return
     */
    @Override
    public JsonData update(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if (checkExists(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getAclId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(aclParam.getAclId());
//        if (before == null) {
//            throw new PermissionException("待更新的权限点不存在");
//        }
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder().aclId(aclParam.getAclId()).name(aclParam.getName()).aclModuleId(aclParam.getAclModuleId()).url(aclParam.getUrl()).
                type(aclParam.getType()).status(aclParam.getStatus()).seq(aclParam.getSeq()).remark(aclParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorTime(new Date());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysAclMapper.updateByPrimaryKeySelective(after);
        iSysLogService.saveAclLog(before, after);
        return JsonData.success(after);
    }

    /**
     * 根据权限模块id获取所有权限点
     * TODO 分页操作
     * @param aclModuleId
     * @return
     */
    @Override
    public JsonData list(Integer aclModuleId) {
        if (aclModuleId == null) {
            return JsonData.fail("参数为空");
        }
        // 根据权限模块id查询权限点的数量
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            // 获取权限点列表
            List<SysAcl> sysAclList = sysAclMapper.getByAclModuleId(aclModuleId);
            return JsonData.success(sysAclList);
        } else {
            return JsonData.fail("当前权限模块下不存在权限点");
        }
    }

    /**
     * 检测相同权限模块下是否存在相同名称的权限点
     * @param aclModuleId
     * @param aclName
     * @param aclId
     * @return
     */
    private boolean checkExists(Integer aclModuleId, String aclName, Integer aclId) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, aclName, aclId) > 0;
    }

    /**
     * 生成权限点码
     * 以当前时间戳加上100以内的随机数组成
     * @return
     */
    public String generateCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + "_" + (int) (Math.random() * 100);
    }

}
