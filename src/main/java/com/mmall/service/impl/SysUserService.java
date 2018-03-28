package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.module.SysUser;
import com.mmall.param.UserParam;
import com.mmall.service.ISysLogService;
import com.mmall.service.ISysUserService;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 前台+后台用户管理
 * Created by Administrator on 2018/3/22 0022.
 */
@Service("iSysUserService")
public class SysUserService implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ISysLogService iSysLogService;

    /**
     * 登录校验
     * @param username
     * @param password
     * @return
     */
    @Override
    public JsonData findByParam(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return JsonData.fail("用户名不能为空");
        } else if (StringUtils.isBlank(password)) {
            return JsonData.fail("密码不能为空");
        }
        SysUser sysUser = sysUserMapper.findByUsernameAndPassword(username, password);

        return JsonData.success(sysUser);
    }

    /**
     * 新增用户
     * @param userParam
     */
    @Override
    public void save(UserParam userParam) {
        BeanValidator.check(userParam);
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(userParam.getEmail(), userParam.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        // 自定义密码
        String password = PasswordUtil.randomPassword();
        password = "12345678"; // TODO
        // MD5密码加密
        String encryptPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(userParam.getUsername()).telephone(userParam.getTelephone()).
            email(userParam.getEmail()).password(encryptPassword).deptId(userParam.getDeptId()).status(userParam.getStatus()).
            remark(userParam.getRemark()).build();
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); // TODO
        sysUser.setOperatorTime(new Date()); // TODO

        // TODO 发送email，通知用户密码信息

        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        iSysLogService.saveUserLog(null, sysUser);
    }

    /**
     * 更新用户信息
     * @param userParam
     */
    @Override
    public void update(UserParam userParam) {
        BeanValidator.check(userParam);
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(userParam.getEmail(), userParam.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(userParam.getId()).username(userParam.getUsername()).telephone(userParam.getTelephone()).
                email(userParam.getEmail()).deptId(userParam.getDeptId()).status(userParam.getStatus()).
                remark(userParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date()); // TODO

        sysUserMapper.updateByPrimaryKeySelective(after);
        iSysLogService.saveUserLog(before, after);
    }

    /**
     * 根据关键字查询信息，可以是用户名、手机号、邮箱等
     * @param keyword
     * @return
     */
    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    /**
     * 获取所有用户信息列表
     * @return
     */
    @Override
    public List<SysUser> getAll() {
        List<SysUser> sysUserList = sysUserMapper.getAll();
        return sysUserList;
    }


    /**
     * 校验用户邮箱是否存在
     * @param email
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String email, Integer userId) {
        return sysUserMapper.countByEmail(email, userId) > 0;
    }

    /**
     * 校验用户手机是否存在
     * @param phone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String phone, Integer userId) {
        return sysUserMapper.countByTelephone(phone, userId) > 0;
    }
}
