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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//    @Override
//    public JsonData findByParam(String username, String password) {
//        if (StringUtils.isBlank(username)) {
//            return JsonData.fail("用户名不能为空");
//        } else if (StringUtils.isBlank(password)) {
//            return JsonData.fail("密码不能为空");
//        }
//        SysUser sysUser = sysUserMapper.findByUsernameAndPassword(username, password);
//
//        return JsonData.success(sysUser);
//        return null;
//    }

    /**
     * 用户注册
     * @param sysUser
     * @return
     */
    @Override
    public JsonData register(SysUser sysUser) {
        return null;
    }

    /**
     * 新增用户
     * @param userParam
     */
    @Override
    @Transactional
    public void save(UserParam userParam) {
        BeanValidator.check(userParam);
        if (checkExist(userParam.getUsername())) {
            throw new ParamException("用户名已存在");
        }
        boolean flag = checkExist(userParam.getTelephone());
        if (flag) {
            throw new ParamException("电话已被占用");
        }
        if (checkExist(userParam.getEmail())) {
            throw new ParamException("邮箱已被占用");
        }
        if (!checkDeptExist(userParam.getDeptId())) {
            throw new ParamException("部门不存在");
        }
        // 自定义密码
        String password = PasswordUtil.randomPassword();
        password = "12345678"; // TODO
        // MD5密码加密
        String encryptPassword = MD5Util.encrypt(password);
        int count = sysUserMapper.countUser();
        if (count > 0) {
            count += 100000;
        }
        SysUser sysUser = SysUser.builder().userId(count).username(userParam.getUsername()).telephone(userParam.getTelephone()).
            email(userParam.getEmail()).password(encryptPassword).deptId(userParam.getDeptId()).status(userParam.getStatus()).
            remark(userParam.getRemark()).build();
//        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysUser.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperatorTime(new Date());

        // TODO 发送email，通知用户密码信息

        sysUserMapper.insertSelective(sysUser);
//        int a = 9/0;
        iSysLogService.saveUserLog(null, sysUser);
    }

    /**
     * 更新用户信息
     * @param userParam
     */
    @Override
    @Transactional
    public void update(UserParam userParam) {
        BeanValidator.check(userParam);
        if (checkExist(userParam.getUsername())) {
            throw new ParamException("当前用户名已存在，请更换用户名");
        }
        if (!checkReg(userParam.getTelephone(), 1)) {
            throw new ParamException("电话格式错误");
        }
        if (checkExist(userParam.getTelephone())) {
            throw new ParamException("电话已被占用");
        }
        if (!checkDeptExist(userParam.getDeptId())) {
            throw new ParamException("待更新用户所在的部门不存在");
        }
//        if (checkReg(userParam.getEmail(), 2)) {
//            throw new ParamException("邮箱格式错误");
//        }
        if (checkExist(userParam.getEmail())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getUserId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().userId(userParam.getUserId()).username(userParam.getUsername()).telephone(userParam.getTelephone()).
                email(userParam.getEmail()).deptId(userParam.getDeptId()).status(userParam.getStatus()).
                remark(userParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperatorTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
        //int a = 9/0;
        iSysLogService.saveUserLog(before, after);
    }

    /**
     * 查询登录用户信息
     * @param keyword
     * @return
     */
    @Override
    public JsonData<SysUser> loginByKeyword(String keyword) {
        SysUser sysUser = sysUserMapper.loginByKeyword(keyword);
        return JsonData.success(sysUser);
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
     * 根据关键字检测用户名、邮箱或者手机是否已存在
     * @param keyword
     * @return
     */
    public boolean checkExist(String keyword) {
        return sysUserMapper.findByKeyword(keyword) > 0;
    }

    /**
     * 查询用户所属的部门是否存在
     * @param deptId
     * @return
     */
    public boolean checkDeptExist(Integer deptId) {
        return sysUserMapper.checkDeptExist(deptId) > 0;
    }

    /**
     * 邮箱、手机号码正则验证
     * @param param
     */
    public boolean checkReg(String param, int type) {
        String regexPhone = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        String regexEmail = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern pattern = null;
        Matcher matcher = null;
        if (type == 1) {
            pattern = Pattern.compile(regexPhone);
        } else if (type == 2) {
            pattern = Pattern.compile(regexEmail);
        }
        matcher = pattern.matcher(param);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

}
